package edu.shtoiko.accountservice.controller;

import edu.shtoiko.accountservice.model.Dto.TransactionDto;
import edu.shtoiko.accountservice.model.Dto.TransactionReqest;
import edu.shtoiko.accountservice.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transaction/")
public class TransactionController {
    final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/account/{id}")
    public List<TransactionDto> getAllDtoByAccountId(@PathVariable("id") long accountId) {
        return transactionService.getAllDtoByAccountId(accountId);
    }

    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionReqest transactionReqest){
        return new ResponseEntity<>(transactionService.create(transactionReqest), HttpStatus.CREATED);
    }
}
