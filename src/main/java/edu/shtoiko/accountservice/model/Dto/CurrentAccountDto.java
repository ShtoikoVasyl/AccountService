package edu.shtoiko.accountservice.model.Dto;

import edu.shtoiko.accountservice.model.account.CurrentAccount;
import edu.shtoiko.accountservice.model.enums.AccountStatus;
import edu.shtoiko.accountservice.model.entity.Currency;
import edu.shtoiko.accountservice.model.account.Account;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrentAccountDto {
    private long accountId;
    private long ownerId;
    private String accountName;
    private long accountNumber;
    private Currency currency;
    private BigDecimal amount;
    private AccountStatus accountStatus;

    public CurrentAccountDto(Account account, Long ownerId, Currency currency) {
        this.accountId = account.getId();
        this.ownerId = ownerId;
        this.accountName = account.getAccountName();
        this.currency = currency;
        this.amount = account.getAmount();
        this.accountStatus = account.getAccountStatus();
    }

    public CurrentAccountDto(CurrentAccount account) {
        this.accountId = account.getId();
        this.ownerId = account.getOwnerId();
        this.accountName = account.getAccountName();
        this.currency = account.getCurrency();
        this.amount = account.getAmount();
        this.accountStatus = account.getAccountStatus();
    }

    public boolean isNew(){
        return accountId == 0;
    }
}
