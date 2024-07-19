package edu.shtoiko.accountservice.model.Dto;

import edu.shtoiko.accountservice.model.enums.AccountType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequest {
    private Long ownerId;

    private String accountName;

    private String currencyCode;

    private AccountType type;
}
