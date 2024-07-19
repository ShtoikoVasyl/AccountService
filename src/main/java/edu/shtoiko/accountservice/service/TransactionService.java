package edu.shtoiko.accountservice.service;

import edu.shtoiko.accountservice.model.Dto.TransactionDto;
import edu.shtoiko.accountservice.model.Dto.TransactionRequest;
import edu.shtoiko.accountservice.model.account.CurrentAccount;
import edu.shtoiko.accountservice.model.entity.Transaction;

import java.util.List;

public interface TransactionService {

    TransactionDto create(TransactionRequest transactionRequest);
    Transaction readById(String id);
    List<TransactionDto> getAllTransactionDtosByAccountId(long id);
}
