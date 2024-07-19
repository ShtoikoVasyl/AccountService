package edu.shtoiko.accountservice.service.implementation;

import edu.shtoiko.accountservice.model.Dto.AccountRequest;
import edu.shtoiko.accountservice.model.Dto.AccountResponse;
import edu.shtoiko.accountservice.model.Dto.CurrentAccountDto;
import edu.shtoiko.accountservice.model.enums.AccountStatus;
import edu.shtoiko.accountservice.model.account.CurrentAccount;
import edu.shtoiko.accountservice.repository.CurrentAccountRepository;
import edu.shtoiko.accountservice.service.CurrencyService;
import edu.shtoiko.accountservice.service.CurrentAccountService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
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


    private CurrentAccount createCurrentAccount(AccountRequest accountRequest){
        CurrentAccount newAccount = CurrentAccount.builder()
                .amount(new BigDecimal(0))
                .accountName(accountRequest.getAccountName())
                .ownerId(accountRequest.getOwnerId())
                .currency(currencyService.getCurrencyByCode(accountRequest.getCurrencyCode()))
                .accountStatus(AccountStatus.OK)
                .accountNumber(generateUniqueNumber())
                .pinCode(defaultPin)
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
    public AccountResponse create(AccountRequest accountRequest) {
        return modelMapper.map(createCurrentAccount(accountRequest), AccountResponse.class);
    }

    public CurrentAccountDto getAccountDtoById(long id){
        return modelMapper.map(readById(id), CurrentAccountDto.class);
    }

    @Override
    public CurrentAccount readById(long id) {
        return currentAccountRepository.findById(id).orElseThrow(() -> {
            log.error("Account with id={} not found", id);
            return new EntityNotFoundException("Account with id=" + id + " not found");
        });
    }

    @Transactional
    @Override
    public AccountResponse updateName(String newName, long accountId) {
        log.info("Updating account name for id={} to newName={}", accountId, newName);
        CurrentAccount currentAccount = readById(accountId);
        currentAccount.setAccountName(newName);
        AccountResponse response = modelMapper.map(currentAccountRepository.save(currentAccount), AccountResponse.class);
        log.info("Account name updated for id={}", accountId);
        return response;
    }

    @Override
    public void delete(long id) {
        log.info("Deleting account with id={}", id);
        currentAccountRepository.deleteById(id);
    }

    @Override
    public List<CurrentAccount> getAll() {
        return currentAccountRepository.findAll();
    }

    @Override
    public List<CurrentAccount> getByUserId(long userId) {
        return currentAccountRepository.findAllByOwnerId(userId);
    }

    @Override
    public List<AccountResponse> getAccountsDtoByUserId(long userId) {
        return getByUserId(userId).stream()
                .map( ac -> modelMapper.map(ac, AccountResponse.class))
                .toList();
    }
}
