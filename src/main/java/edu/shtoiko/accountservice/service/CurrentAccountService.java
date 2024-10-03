package edu.shtoiko.accountservice.service;

import edu.shtoiko.accountservice.model.Dto.*;
import edu.shtoiko.accountservice.model.account.CurrentAccount;

import java.util.List;

public interface CurrentAccountService {

    AccountResponse create(AccountCreateRequest accountRequest);

    CurrentAccount readById(Long id);

    AccountResponse updateAccountName(CurrentAccount account, String newName);

    void delete(Long id);

    public CurrentAccountDto getAccountDtoById(Long id);

    List<CurrentAccount> getAll();

    List<CurrentAccount> getByUserId(Long userId);

    List<AccountResponse> getAccountsResponseByUserId(Long userId);

    AccountResponse getAccountResponseById(Long accountId);

    AccountResponse getAccountResponseByNumber(Long accountNumber);

    AccountResponse getAccountResponseByCredentials(AccountRequestCredentials account);

    AccountResponse deleteByAccountCredentials(AccountRequestCredentials account);

    AccountResponse updateAccountNameByUpdateRequest(AccountUpdateNameRequest updateNameRequest);
}
