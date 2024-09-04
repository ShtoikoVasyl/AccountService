package edu.shtoiko.accountservice.controller;

import edu.shtoiko.accountservice.model.Dto.TransactionDto;
import edu.shtoiko.accountservice.model.Dto.TransactionRequest;
import edu.shtoiko.accountservice.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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
@Tag(name = "Transaction Controller", description = "Controller for managing transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @Operation(summary = "Get transactions by account ID", description = "Retrieves all transactions associated with a specific account ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDto.class))),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/account/{id}")
    public List<TransactionDto> getAllDtoByAccountId(
            @Parameter(description = "ID of the account to retrieve transactions for", required = true)
            @PathVariable("id") long accountId) {
        return transactionService.getAllTransactionDtosByAccountId(accountId);
    }

    @Operation(summary = "Create a new transaction", description = "Creates a new transaction with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(
            @Parameter(description = "Transaction details", required = true)
            @Valid @RequestBody TransactionRequest transactionRequest) {
        return new ResponseEntity<>(transactionService.create(transactionRequest), HttpStatus.CREATED);
    }
}