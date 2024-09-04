package edu.shtoiko.accountservice.controller;

import edu.shtoiko.accountservice.model.Dto.AccountRequest;
import edu.shtoiko.accountservice.model.Dto.AccountResponse;
import edu.shtoiko.accountservice.model.Dto.CurrentAccountDto;
import edu.shtoiko.accountservice.service.CurrentAccountService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account/")
@Tag(name = "Account Controller", description = "Controller for managing user accounts")
public class AccountController {
    final CurrentAccountService currentAccountService;

    public AccountController(CurrentAccountService currentAccountService) {
        this.currentAccountService = currentAccountService;
    }
    @Operation(summary = "Get account details", description = "Retrieves details of a specific account by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CurrentAccountDto.class))),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{account_id}/")
    public CurrentAccountDto readAccount(
            @Parameter(description = "ID of the account to retrieve", required = true)
            @PathVariable("account_id") long accountId) {
        return currentAccountService.getAccountDtoById(accountId);
    }

    @Operation(summary = "Get user accounts", description = "Retrieves a list of accounts associated with a specific user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User accounts retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user/{user_id}/")
    public ResponseEntity<List<AccountResponse>> readUsersAccounts(
            @Parameter(description = "ID of the user whose accounts are to be retrieved", required = true)
            @PathVariable("user_id") long userId) {
        return new ResponseEntity<>(currentAccountService.getAccountsDtoByUserId(userId), HttpStatus.OK);
    }

    @Operation(summary = "Create a new account", description = "Creates a new account for a specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{user_id}/")
    public ResponseEntity<AccountResponse> createAccount(
            @Parameter(description = "ID of the user for whom the account is being created", required = true)
            @PathVariable("user_id") long accountId,
            @Valid @RequestBody AccountRequest accountRequest) {
        return new ResponseEntity<>(currentAccountService.create(accountRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Update account name", description = "Updates the name of a specified account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account name updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{account_id}/")
    public ResponseEntity<AccountResponse> updateAccount(
            @Parameter(description = "ID of the account to update", required = true)
            @PathVariable("account_id") long accountId,
            @RequestBody String newName) {
        return new ResponseEntity<>(currentAccountService.updateName(newName, accountId), HttpStatus.OK);
    }

    @Operation(summary = "Delete an account", description = "Deletes a specified account by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{account_id}/")
    public ResponseEntity<Long> deleteAccount(
            @Parameter(description = "ID of the account to delete", required = true)
            @PathVariable("account_id") long accountId) {
        try {
            currentAccountService.delete(accountId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
