package edu.shtoiko.accountservice.model.Dto;

import edu.shtoiko.accountservice.model.enums.AccountStatus;
import edu.shtoiko.accountservice.model.enums.AccountType;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class AccountResponse {
    private long accountId;
    private long ownerId;
    private String accountName;
    private long accountNumber;
    private String currencyCode;
    private BigDecimal amount;
    private AccountType accountType;
    private AccountStatus accountStatus;
}
