package edu.shtoiko.accountservice.model.Dto;

import edu.shtoiko.accountservice.model.enums.AccountType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCreateRequest {

    @NotBlank(message = "Owner ID cannot be blank")
    @Pattern(regexp = "^[0-9]+$", message = "Owner ID must be a positive number")
    private String ownerId;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
    @Pattern(regexp = "^[A-Z][a-zA-Z ]*$",
        message = "Name must start with an uppercase letter and contain only letters and spaces")
    private String accountName;

    @NotBlank(message = "CurrencyCode cannot be blank")
    @Pattern(regexp = "^[A-Z]{3}$", message = "CurrencyCode must consist of exactly three uppercase letters")
    private String currencyCode;

    @NotNull(message = "AccountType cannot be null")
    private AccountType type;
}
