package edu.shtoiko.accountservice.model.Dto;

import edu.shtoiko.accountservice.model.enums.TransactionStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDto {
    private String id;
    private Instant date;
    private long receiverAccountId;
    private long senderAccountId;
    private BigDecimal amount;
    private String currencyCode;
    private String description;
    private TransactionStatus transactionStatus;
}
