package edu.shtoiko.accountservice.model.Dto;

import edu.shtoiko.accountservice.model.enums.AccountStatus;
import edu.shtoiko.accountservice.model.enums.AccountType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountResponse {
    private Long accountId;
    private Long ownerId;
    private String accountName;
    private Long accountNumber;
    private String currencyCode;
    private BigDecimal amount;
    private AccountType accountType;
    private AccountStatus accountStatus;
}
