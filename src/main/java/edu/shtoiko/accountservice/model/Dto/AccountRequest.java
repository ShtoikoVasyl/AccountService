package edu.shtoiko.accountservice.model.Dto;

import edu.shtoiko.accountservice.model.enums.AccountType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequest {
    @NotNull
    @Positive
    private Long ownerId;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name must contain only letters")
    private String accountName;

    @NotBlank(message = "CurrencyCode cannot be blank")
    private String currencyCode;

    @NotNull(message = "AccountType cannot be null")
    private AccountType type;
}
