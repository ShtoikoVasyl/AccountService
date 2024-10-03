package edu.shtoiko.accountservice.controller;

import edu.shtoiko.accountservice.model.Dto.AccountCreateRequest;
import edu.shtoiko.accountservice.model.Dto.AccountRequestCredentials;
import edu.shtoiko.accountservice.model.Dto.AccountResponse;
import edu.shtoiko.accountservice.model.Dto.AccountUpdateNameRequest;
import edu.shtoiko.accountservice.model.Dto.CurrentAccountDto;
import edu.shtoiko.accountservice.service.CurrentAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account/")
@Tag(name = "Account Controller", description = "Controller for managing user accounts")
public class AccountController {

    final CurrentAccountService currentAccountService;

    public AccountController(CurrentAccountService currentAccountService) {
        this.currentAccountService = currentAccountService;
    }

    @Operation(summary = "Get account details",
        description = "Retrieves details of a specific account by its ID. Accessible to the account owner or users with ACCOUNTS_READ authority.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Account details retrieved successfully",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = CurrentAccountDto.class))),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("account")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("#account.ownerId == authentication.details or hasAuthority('ACCOUNTS_READ')")
    public ResponseEntity<?> readAccount(
        @Parameter(description = "ID of the account to retrieve",
            required = true) @Valid @RequestBody AccountRequestCredentials account) {
        return new ResponseEntity<>(currentAccountService.getAccountResponseByCredentials(account), HttpStatus.OK);
    }

    @Operation(summary = "Get user's accounts",
        description = "Retrieves a list of accounts associated with a specific user. Accessible to the user or users with ACCOUNTS_READ or USERMANAGER_READ authorities.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User accounts retrieved successfully",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AccountResponse.class))),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user/{user_id}/")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("#userId == authentication.details or hasAuthority('ACCOUNTS_READ') or hasAuthority('USERMANAGER_READ')")
    public ResponseEntity<?> readUsersAccounts(
        @Parameter(description = "ID of the user whose accounts are to be retrieved",
            required = true) @PathVariable("user_id") String userId) {
        return new ResponseEntity<>(currentAccountService.getAccountsResponseByUserId(Long.parseLong(userId)),
            HttpStatus.OK);
    }

    @Operation(summary = "Create a new account",
        description = "Creates a new account for a specified user. Accessible to the user or users with ACCOUNTS_WRITE authority.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Account created successfully",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AccountResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping()
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("#accountRequest.ownerId == authentication.details or hasAuthority('ACCOUNTS_WRITE')")
    public ResponseEntity<?> createAccount(
        @Parameter(description = "Request to create new account",
            required = true) @Valid @RequestBody AccountCreateRequest accountRequest) {
        return new ResponseEntity<>(currentAccountService.create(accountRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Update account name",
        description = "Updates the name of a specified account. Accessible to the account owner or users with ACCOUNTS_WRITE authority.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Account name updated successfully",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AccountResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/update/")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("#updateNameRequest.ownerId == authentication.details or hasAuthority('ACCOUNTS_WRITE')")
    public ResponseEntity<?> updateAccount(
        @Parameter(description = "Request to update the account name",
            required = true) @Valid @RequestBody AccountUpdateNameRequest updateNameRequest) {
        try {
            return new ResponseEntity<>(currentAccountService.updateAccountNameByUpdateRequest(updateNameRequest),
                HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete an account",
        description = "Deletes a specified account by its ID. Accessible to the account owner or users with ACCOUNTS_WRITE authority.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Account deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping()
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("#account.ownerId == authentication.details or hasAuthority('ACCOUNTS_WRITE')")
    public ResponseEntity<?> deleteAccount(
        @Parameter(description = "ID of the account to delete",
            required = true) @Valid @RequestBody AccountRequestCredentials account) {
        currentAccountService.deleteByAccountCredentials(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}