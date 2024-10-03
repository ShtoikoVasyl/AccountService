package edu.shtoiko.accountservice.service.implementation;

import edu.shtoiko.accountservice.exception.ResponseException;
import edu.shtoiko.accountservice.model.Dto.*;
import edu.shtoiko.accountservice.model.account.Account;
import edu.shtoiko.accountservice.model.enums.AccountStatus;
import edu.shtoiko.accountservice.model.account.CurrentAccount;
import edu.shtoiko.accountservice.model.enums.ProcessingStatus;
import edu.shtoiko.accountservice.repository.CurrentAccountRepository;
import edu.shtoiko.accountservice.service.CurrencyService;
import edu.shtoiko.accountservice.service.CurrentAccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImplementation implements CurrentAccountService {
    @Value("${account-service.default-pin}")
    private short defaultPin;
    private final CurrencyService currencyService;
    private final CurrentAccountRepository currentAccountRepository;
    private final ModelMapper modelMapper;

    private CurrentAccount createCurrentAccount(AccountCreateRequest accountRequest) {
        CurrentAccount newAccount = CurrentAccount.builder()
            .amount(new BigDecimal(0))
            .accountName(accountRequest.getAccountName())
            .ownerId(Long.parseLong(accountRequest.getOwnerId()))
            .currency(currencyService.getCurrencyByCode(accountRequest.getCurrencyCode()))
            .accountStatus(AccountStatus.OK)
            .accountNumber(generateUniqueNumber())
            .pinCode(defaultPin)
            .processingStatus(ProcessingStatus.READY)
            .build();
        newAccount = currentAccountRepository.save(newAccount);
        log.info("New account created, id={}, ownerId={}", newAccount.getId(), newAccount.getOwnerId());
        return newAccount;
    }

    private Long generateUniqueNumber() {
        long accountNumber;
        Random random = new Random();
        do {
            String nm = "414551" + String.format("%010d", random.nextInt(999999999) + 1);
            accountNumber = Long.parseLong(nm);
        } while (currentAccountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }

    @Override
    public AccountResponse create(AccountCreateRequest accountRequest) {
        return modelMapper.map(createCurrentAccount(accountRequest), AccountResponse.class);
    }

    public CurrentAccountDto getAccountDtoById(Long id) {
        return modelMapper.map(readById(id), CurrentAccountDto.class);
    }

    @Override
    public CurrentAccount readById(Long id) {
        return currentAccountRepository.findById(id).orElseThrow(() -> {
            log.error("Account with id={} not found", id);
            return new ResponseException(HttpStatus.NOT_FOUND, "Account with id=" + id + " not found");
        });
    }

    @Override
    public List<CurrentAccount> getAll() {
        return currentAccountRepository.findAll();
    }

    @Override
    public List<CurrentAccount> getByUserId(Long userId) {
        return currentAccountRepository.findAllByOwnerId(userId);
    }

    @Override
    public List<AccountResponse> getAccountsResponseByUserId(Long userId) {
        return getByUserId(userId).stream()
            .map(ac -> modelMapper.map(ac, AccountResponse.class))
            .toList();
    }

    @Override
    public AccountResponse getAccountResponseById(Long accountId) {
        return modelMapper.map(readById(accountId), AccountResponse.class);
    }

    @Override
    public AccountResponse getAccountResponseByNumber(Long accountNumber) {
        return modelMapper.map(readByNumber(accountNumber), AccountResponse.class);
    }

    private CurrentAccount readByNumber(Long accountNumber) {
        CurrentAccount account = currentAccountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Account " + accountNumber + " not found");
        }
        return account;
    }

    // todo: should be rewritten using single search method in the repo
    @Override
    public AccountResponse getAccountResponseByCredentials(AccountRequestCredentials credentials) {
        return getAccountResponseByAccountIdAndOwnerId(Long.parseLong(credentials.getAccountId()),
            Long.parseLong(credentials.getOwnerId()));
    }

    private AccountResponse getAccountResponseByAccountIdAndOwnerId(Long accountId, Long ownerId) {
        AccountResponse account = getAccountResponseById(accountId);
        if (!account.getOwnerId().equals(ownerId)) {
            throw new ResponseException(HttpStatus.NOT_FOUND, "Account with account id:" + accountId
                + " and ownerId:" + accountId + " not found");
        }
        return account;
    }

    @Override
    public AccountResponse deleteByAccountCredentials(AccountRequestCredentials credentials) {
        return deleteByAccountIdAndOwnerId(Long.parseLong(credentials.getAccountId()),
            Long.parseLong(credentials.getOwnerId()));
    }

    @Override
    @Transactional
    public AccountResponse updateAccountNameByUpdateRequest(AccountUpdateNameRequest updateRequest) {
        log.info("Updating account name for id={} to newName={}", updateRequest.getAccountId(),
            updateRequest.getNewName());
        CurrentAccount account = readById(Long.parseLong(updateRequest.getAccountId()));
        if (account.getOwnerId().equals(Long.parseLong(updateRequest.getOwnerId()))) {
            return updateAccountName(account, updateRequest.getNewName());
        } else {
            throw new ResponseException(HttpStatus.FORBIDDEN, "Requested and real ownerIds mismatch");
        }
    }

    @Override
    public AccountResponse updateAccountName(CurrentAccount account, String newName) {
        account.setAccountName(newName);
        AccountResponse response =
            modelMapper.map(currentAccountRepository.save(account), AccountResponse.class);
        log.info("Account name updated for id={}", account.getId());
        return response;
    }

    // todo: should be rewritten using single search method in the repo
    public AccountResponse deleteByAccountIdAndOwnerId(Long accountId, Long ownerId) {
        CurrentAccount account = readById(accountId);
        if (!account.getOwnerId().equals(ownerId)) {
            throw new ResponseException(HttpStatus.NOT_FOUND, "Account with account id:" + accountId
                + " and ownerId:" + accountId + " not found");
        }
        currentAccountRepository.delete(account);
        return modelMapper.map(account, AccountResponse.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        readById(id);
        currentAccountRepository.deleteById(id);
        log.info("Account id={} was deleted", id);
    }
}
