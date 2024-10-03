package edu.shtoiko.accountservice.model.mapping;

import edu.shtoiko.accountservice.model.Dto.TransactionRequest;
import edu.shtoiko.accountservice.model.entity.Transaction;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.time.Instant;

public class TransactionRequestToTransactionConverter implements Converter<TransactionRequest, Transaction> {

    @Override
    public Transaction convert(MappingContext<TransactionRequest, Transaction> context) {
        TransactionRequest source = context.getSource();
        Transaction destination = new Transaction();

        if (source.getReceiverAccountNumber() != null) {
            destination.setReceiverAccountNumber(Long.parseLong(source.getReceiverAccountNumber()));
        }

        if (source.getSenderAccountNumber() != null) {
            destination.setSenderAccountNumber(Long.parseLong(source.getSenderAccountNumber()));
        }

        destination.setCreatedBy(Long.parseLong(source.getCreatedBy()));
        destination.setAmount(source.getAmount());
        destination.setCurrencyCode(source.getCurrencyCode());
        destination.setDescription(source.getDescription());

        destination.setDate(Instant.now());
        return destination;
    }
}
