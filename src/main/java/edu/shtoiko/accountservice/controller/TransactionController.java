package edu.shtoiko.accountservice.controller;

import edu.shtoiko.accountservice.model.Dto.AccountRequestCredentials;
import edu.shtoiko.accountservice.model.Dto.TransactionDto;
import edu.shtoiko.accountservice.model.Dto.TransactionRequest;
import edu.shtoiko.accountservice.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction/")
@Tag(name = "Transaction Controller", description = "Controller for managing transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Get transactions by account ID",
        description = "Retrieves all transactions associated with a specific account ID. Accessible to the account owner or users with ACCOUNTS_READ authority.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = TransactionDto.class))),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/account")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("#account.ownerId == authentication.details or hasAuthority('ACCOUNTS_READ')")
    public ResponseEntity<?> getAllDtoByAccountId(
        @Parameter(description = "ID of the account to retrieve transactions for",
            required = true) @RequestBody AccountRequestCredentials account) {
        return ResponseEntity.ok(transactionService.getAllTransactionDtosByAccountCredentials(account));
    }

    @Operation(summary = "Create a new transaction",
        description = "Creates a new transaction with the provided details. Accessible to the user who creates the transaction.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Transaction created successfully",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = TransactionDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("authentication.details == #transactionRequest.createdBy")
    public ResponseEntity<?> createTransaction(
        @Parameter(description = "Transaction details",
            required = true) @Valid @RequestBody TransactionRequest transactionRequest) {
        return new ResponseEntity<>(transactionService.create(transactionRequest), HttpStatus.CREATED);
    }
}