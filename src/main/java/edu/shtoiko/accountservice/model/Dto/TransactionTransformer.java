package edu.shtoiko.accountservice.model.Dto;

import edu.shtoiko.accountservice.model.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class TransactionTransformer {

    public static TransactionDto convertToDto(Transaction transaction){
        return TransactionDto.builder()
                .id(transaction.getId())
                .senderAccountId(transaction.getSenderAccountNumber())
                .receiverAccountId(transaction.getReceiverAccountNumber())
                .amount(transaction.getAmount())
                .date(transaction.getDate())
                .currencyCode(transaction.getCurrencyCode())
                .description(transaction.getDescription())
                .transactionStatus(transaction.getTransactionStatus())
                .build();
    }

    public static Transaction convertToEntity(TransactionDto transactionDto) {
        return Transaction.builder()
                .senderAccountNumber(transactionDto.getSenderAccountId())
                .receiverAccountNumber(transactionDto.getReceiverAccountId())
                .amount(transactionDto.getAmount())
                .date(transactionDto.getDate())
                .description(transactionDto.getDescription())
                .build();
    }

    public static Transaction convertToEntity(TransactionReqest transactionRequest) {
        return Transaction.builder()
                .senderAccountNumber(transactionRequest.getSenderAccountNumber())
                .receiverAccountNumber(transactionRequest.getReceiverAccountNumber())
                .amount(transactionRequest.getAmount())
                .currencyCode(transactionRequest.getCurrencyCode())
                .date(Instant.now())
                .description(transactionRequest.getDescription())
                .build();
    }
}
