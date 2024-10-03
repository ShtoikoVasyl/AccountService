package edu.shtoiko.accountservice.model.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionRequest {

    @NotNull
    @Positive
    private String createdBy;

    @Size(min = 16, max = 16, message = "The account number must be exactly 16 digits")
    @Pattern(regexp = "\\d{16}", message = "The account number must contain only digits")
    private String receiverAccountNumber;

    @Size(min = 16, max = 16, message = "The account number must be exactly 16 digits")
    @Pattern(regexp = "\\d{16}", message = "The account number must contain only digits")
    private String senderAccountNumber;

    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency code must be a valid ISO 4217 code (3 uppercase letters)")
    private String currencyCode;

    private String description;

}
