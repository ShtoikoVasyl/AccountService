package edu.shtoiko.accountservice.service;

import edu.shtoiko.accountservice.model.Dto.TransactionDto;
import edu.shtoiko.accountservice.model.Dto.TransactionReqest;
import edu.shtoiko.accountservice.model.account.CurrentAccount;
import edu.shtoiko.accountservice.model.entity.Transaction;

import java.util.List;

public interface TransactionService {

    TransactionDto create(TransactionReqest transactionReqest);
    Transaction readById(String id);
    Transaction update(CurrentAccount currentAccount);

    List<TransactionDto> getAllDtoByAccountId(long id);
}
