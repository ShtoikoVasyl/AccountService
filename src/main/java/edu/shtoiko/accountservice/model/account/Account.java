package edu.shtoiko.accountservice.model.account;

import edu.shtoiko.accountservice.model.enums.AccountStatus;
import edu.shtoiko.accountservice.model.entity.Currency;
import edu.shtoiko.accountservice.model.entity.Transaction;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public abstract class Account {
    private long id;

    private Long ownerId;

    private String accountName;

    private Currency currency;

    private BigDecimal amount;

    private List<Transaction> sendTransaction;

    private List<Transaction> receivedTransaction;

    private AccountStatus accountStatus;
}
