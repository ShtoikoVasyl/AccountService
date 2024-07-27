package edu.shtoiko.accountservice.service.implementation;

import edu.shtoiko.accountservice.model.Dto.TransactionDto;
import edu.shtoiko.accountservice.model.Dto.TransactionRequest;
import edu.shtoiko.accountservice.model.entity.Transaction;
import edu.shtoiko.accountservice.model.enums.TransactionStatus;
import edu.shtoiko.accountservice.repository.TransactionRepository;
import edu.shtoiko.accountservice.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImplementation implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final ModelMapper modelMapper;

    @Override
    public TransactionDto create(TransactionRequest transactionRequest) {
        Transaction newTransaction = modelMapper.map(transactionRequest, Transaction.class);
        newTransaction.setSystemComment("Transfer between accounts");
        newTransaction.setTransactionStatus(TransactionStatus.NEW);
        newTransaction.setDate(Instant.now());
        newTransaction = transactionRepository.save(newTransaction);
        log.info("Created new transaction with id={}", newTransaction.getId());
        return modelMapper.map(newTransaction, TransactionDto.class);
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
        List<Transaction> transactions =
            transactionRepository.findAllByReceiverAccountNumberOrSenderAccountNumber(id, id);
        return transactions.stream()
            .map((tr) -> modelMapper.map(tr, TransactionDto.class)).toList();
    }
}
