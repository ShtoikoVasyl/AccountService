package edu.shtoiko.accountservice.model.Dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionRequest {

    private Long receiverAccountNumber;

    private Long senderAccountNumber;

    private BigDecimal amount;

    private String currencyCode;

    private String description;

}
