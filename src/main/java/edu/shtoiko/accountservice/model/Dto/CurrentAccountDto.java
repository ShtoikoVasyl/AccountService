package edu.shtoiko.accountservice.model.Dto;

import edu.shtoiko.accountservice.model.account.CurrentAccount;
import edu.shtoiko.accountservice.model.enums.AccountStatus;
import edu.shtoiko.accountservice.model.entity.Currency;
import edu.shtoiko.accountservice.model.account.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CurrentAccountDto {
    private Long accountId;
    private Long ownerId;
    private String accountName;
    private Long accountNumber;
    private Currency currency;
    private BigDecimal amount;
    private AccountStatus accountStatus;
}
