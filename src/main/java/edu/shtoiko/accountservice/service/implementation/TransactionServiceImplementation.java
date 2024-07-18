package edu.shtoiko.accountservice.service.implementation;

import edu.shtoiko.accountservice.model.Dto.TransactionDto;
import edu.shtoiko.accountservice.model.Dto.TransactionReqest;
import edu.shtoiko.accountservice.model.Dto.TransactionTransformer;
import edu.shtoiko.accountservice.model.account.CurrentAccount;
import edu.shtoiko.accountservice.model.entity.Transaction;
import edu.shtoiko.accountservice.model.enums.TransactionStatus;
import edu.shtoiko.accountservice.repository.TransactionRepository;
import edu.shtoiko.accountservice.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImplementation implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public TransactionDto create(TransactionReqest transactionReqest) {
        Transaction newTransaction = TransactionTransformer.convertToEntity(transactionReqest);
        newTransaction.setSystemComment("Transfer between accounts");
        newTransaction.setTransactionStatus(TransactionStatus.NEW);
        return TransactionTransformer.convertToDto(transactionRepository.save(newTransaction));
    }

    @Override
    public Transaction readById(String id) {
        return transactionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity with id:" + id + " not found"));
    }

    @Override
    public Transaction update(CurrentAccount currentAccount) {
        return null;
    }

    @Override
    public List<TransactionDto> getAllDtoByAccountId(long id) {
        List<Transaction> transactions = transactionRepository.findAllByReceiverAccountNumberOrSenderAccountNumber(id, id);
        return transactions.stream()
                .map((TransactionTransformer::convertToDto)).toList();
    }
}
