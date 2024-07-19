package edu.shtoiko.accountservice.model.Dto;

import edu.shtoiko.accountservice.model.account.CurrentAccount;
import edu.shtoiko.accountservice.service.CurrentAccountService;

public class AccountTransformer {

    CurrentAccountService currentAccountService;

    public AccountTransformer(CurrentAccountService currentAccountService) {
        this.currentAccountService = currentAccountService;
    }

    public CurrentAccount getUpdatedAccount(CurrentAccountDto currentAccountDto){
        CurrentAccount updatedCurrentAccount = currentAccountService.readById(currentAccountDto.getAccountId());
        updatedCurrentAccount.setAccountName(currentAccountDto.getAccountName());
        return updatedCurrentAccount;
    }

    public static AccountResponse getAccountResponse(CurrentAccount currentAccount){
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setAccountNumber(currentAccount.getAccountNumber());
        accountResponse.setAccountName(currentAccount.getAccountName());
        accountResponse.setAccountStatus(currentAccount.getAccountStatus());
        accountResponse.setAmount(currentAccount.getAmount());
        accountResponse.setCurrencyCode(currentAccount.getCurrency().getCode());
        accountResponse.setOwnerId(currentAccount.getOwnerId());
        accountResponse.setAccountNumber(currentAccount.getAccountNumber());
        return accountResponse;
    }
}
