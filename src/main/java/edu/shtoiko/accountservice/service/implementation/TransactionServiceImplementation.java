package edu.shtoiko.accountservice.service.implementation;

import edu.shtoiko.accountservice.model.Dto.AccountRequestCredentials;
import edu.shtoiko.accountservice.model.Dto.AccountResponse;
import edu.shtoiko.accountservice.model.Dto.TransactionDto;
import edu.shtoiko.accountservice.model.Dto.TransactionRequest;
import edu.shtoiko.accountservice.model.entity.Role;
import edu.shtoiko.accountservice.model.entity.Transaction;
import edu.shtoiko.accountservice.model.enums.TransactionStatus;
import edu.shtoiko.accountservice.repository.TransactionRepository;
import edu.shtoiko.accountservice.service.CurrentAccountService;
import edu.shtoiko.accountservice.service.MessageProducerService;
import edu.shtoiko.accountservice.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImplementation implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final ModelMapper modelMapper;

    private final MessageProducerService messageProducerService;

    private final CurrentAccountService accountService;

    @Override
    public TransactionDto create(TransactionRequest transactionRequest) {
        AccountResponse senderAccount =
            accountService.getAccountResponseByNumber(Long.parseLong(transactionRequest.getSenderAccountNumber()));
        if (checkPrincipalAccessToAccount(List.of(new Role("ACCOUNTS_WRITE")), senderAccount.getOwnerId())) {
            return modelMapper.map(saveTransaction(transactionRequest), TransactionDto.class);
        } else {
            throw new AccessDeniedException(
                "Principal don't have access to account " + transactionRequest.getSenderAccountNumber());
        }
    }

    private Transaction saveTransaction(TransactionRequest transactionRequest) {
        Transaction newTransaction = modelMapper.map(transactionRequest, Transaction.class);
        newTransaction.setSystemComment("Transfer between accounts");
        newTransaction.setTransactionStatus(TransactionStatus.NEW);
        newTransaction.setDate(Instant.now());
        newTransaction = transactionRepository.save(newTransaction);
        messageProducerService.sendMessage(newTransaction);
        log.info("Created new transaction with id={}", newTransaction.getId());
        return newTransaction;
    }

    private boolean checkPrincipalAccessToAccount(List<Role> roleRequirements, Long accountOwnerId) {
        Long userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getDetails());
        Collection<? extends GrantedAuthority> principalRoles =
            SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        System.out.println("roleRequirements: " + roleRequirements);
        System.out.println("principalRoles: " + principalRoles);
        return roleRequirements.stream()
            .anyMatch(principalRoles::contains) || accountOwnerId.equals(userId);
    }

    @Override
    public Transaction readById(String id) {
        return transactionRepository.findById(id).orElseThrow(() -> {
            log.error("Transaction with id={} not found", id);
            return new EntityNotFoundException("Transaction with id=" + id + " not found");
        });
    }

    @Override
    public List<TransactionDto> getAllTransactionDtosByAccountId(long id) {
        log.info("Looking for all transaction for accountId={}", id);
        Long accountNumber = accountService.getAccountResponseById(id).getAccountNumber();
        List<Transaction> transactions =
            transactionRepository.findAllByReceiverAccountNumberOrSenderAccountNumber(accountNumber, accountNumber);
        return transactions.stream()
            .map((tr) -> modelMapper.map(tr, TransactionDto.class)).toList();
    }

    @Override
    public List<TransactionDto> getAllTransactionDtosByAccountCredentials(AccountRequestCredentials credentials) {
        AccountResponse account = accountService.getAccountResponseByCredentials(credentials);
        return getAllTransactionDtosByAccountId(account.getAccountId());
    }
}
