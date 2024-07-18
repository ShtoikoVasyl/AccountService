package edu.shtoiko.accountservice.service;

import edu.shtoiko.accountservice.model.Dto.AccountRequest;
import edu.shtoiko.accountservice.model.Dto.AccountResponse;
import edu.shtoiko.accountservice.model.Dto.CurrentAccountDto;
import edu.shtoiko.accountservice.model.account.CurrentAccount;

import java.util.List;

public interface CurrentAccountService {

    AccountResponse create(AccountRequest accountRequest);

    CurrentAccount readById(long id);
    AccountResponse updateName(String newName, long accountId);
    void delete(long id);

    public CurrentAccountDto getAccountDtoById(long id);

    List<CurrentAccount> getAll();
    List<CurrentAccount> getByUserId(long userId);

    List<AccountResponse> getAccountsDtoByUserId(long userId);

//    User getByUserName();
}
