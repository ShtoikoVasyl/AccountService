package edu.shtoiko.accountservice.service.implementation;

import edu.shtoiko.accountservice.model.Dto.TransactionDto;
import edu.shtoiko.accountservice.model.Dto.TransactionRequest;
import edu.shtoiko.accountservice.model.account.CurrentAccount;
import edu.shtoiko.accountservice.model.entity.Transaction;
import edu.shtoiko.accountservice.model.enums.TransactionStatus;
import edu.shtoiko.accountservice.repository.TransactionRepository;
import edu.shtoiko.accountservice.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImplementation implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final ModelMapper modelMapper;

    @Override
    public TransactionDto create(TransactionRequest transactionRequest) {
        Transaction newTransaction = modelMapper.map(transactionRequest, Transaction.class);
        newTransaction.setSystemComment("Transfer between accounts");
        newTransaction.setTransactionStatus(TransactionStatus.NEW);
        return modelMapper.map(transactionRepository.save(newTransaction), TransactionDto.class);
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
                .map((tr) -> modelMapper.map(tr, TransactionDto.class)).toList();
    }
}
