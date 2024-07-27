package edu.shtoiko.accountservice.repository;

import edu.shtoiko.accountservice.model.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {

    public List<Transaction> findAllByReceiverAccountNumberOrSenderAccountNumber(Long receiverAccountId,
        Long senderAccountId);
}
