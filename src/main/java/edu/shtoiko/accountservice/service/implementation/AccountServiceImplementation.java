package edu.shtoiko.accountservice.service.implementation;

import edu.shtoiko.accountservice.model.Dto.*;
import edu.shtoiko.accountservice.model.enums.AccountStatus;
import edu.shtoiko.accountservice.model.account.CurrentAccount;
import edu.shtoiko.accountservice.repository.CurrentAccountRepository;
import edu.shtoiko.accountservice.service.CurrencyService;
import edu.shtoiko.accountservice.service.CurrentAccountService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountServiceImplementation implements CurrentAccountService {
    @Value("${account-service.default-pin}")
    private short defaultPin;
    private final CurrencyService currencyService;
    private final CurrentAccountRepository currentAccountRepository;


    private AccountResponse createCurrentAccount(AccountRequest accountRequest){
        CurrentAccount newAccount = CurrentAccount.builder()
                .amount(new BigDecimal(0))
                .accountName(accountRequest.getAccountName())
                .ownerId(accountRequest.getOwnerId())
                .currency(currencyService.getCurrencyByCode(accountRequest.getCurrencyCode()))
                .accountStatus(AccountStatus.OK)
                .accountNumber(generateUniqueNumber())
                .pinCode(defaultPin)
                .build();
        return AccountTransformer.getAccountResponse(currentAccountRepository.save(newAccount));
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
        return createCurrentAccount(accountRequest);
    }

    public CurrentAccountDto getAccountDtoById(long id){
        return new CurrentAccountDto(currentAccountRepository.findById(id).orElseThrow(
                () -> new RuntimeException("CurrentAccount with id=" + id + " not found")));
    }

    @Override
    public CurrentAccount readById(long id) {
        return currentAccountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    @Override
    public AccountResponse updateName(String newName, long accountId) {
        CurrentAccount currentAccount = currentAccountRepository.findById(accountId).orElseThrow(() ->
                new EntityNotFoundException("Account with id=" + accountId + " not found"));
        currentAccount.setAccountName(newName);
        return AccountTransformer.getAccountResponse(currentAccountRepository.save(currentAccount));
    }

    @Override
    public void delete(long id) {
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
                .map(AccountTransformer::getAccountResponse)
                .toList();
    }
}
