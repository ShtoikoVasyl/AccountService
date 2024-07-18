package edu.shtoiko.accountservice.controller;

import edu.shtoiko.accountservice.model.Dto.AccountRequest;
import edu.shtoiko.accountservice.model.Dto.AccountResponse;
import edu.shtoiko.accountservice.model.Dto.CurrentAccountDto;
import edu.shtoiko.accountservice.service.CurrentAccountService;
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
public class AccountController {
    final CurrentAccountService currentAccountService;

    public AccountController(CurrentAccountService currentAccountService) {
        this.currentAccountService = currentAccountService;
    }

    @GetMapping("/{account_id}/")
    public CurrentAccountDto readAccount(@PathVariable("account_id") long accountId){
            return currentAccountService.getAccountDtoById(accountId);
    }

    @GetMapping("/user/{user_id}/")
    public ResponseEntity<List<AccountResponse>> readUsersAccounts(@PathVariable("user_id") long userId){
        return new ResponseEntity<>(currentAccountService.getAccountsDtoByUserId(userId), HttpStatus.OK);
    }

    @PostMapping("/{user_id}/")
    public ResponseEntity<AccountResponse> createAccount(@PathVariable("user_id") long accountId, @RequestBody AccountRequest accountRequest){
        return new ResponseEntity<>(currentAccountService.create(accountRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{account_id}/")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable("account_id") long accountId, @RequestBody String newName){
        return new ResponseEntity<AccountResponse>(currentAccountService.updateName(newName, accountId), HttpStatus.OK);
    }

    @DeleteMapping("/{account_id}/")
    public ResponseEntity<Long> deleteAccount(@PathVariable("account_id") long accountId){
        try {
            currentAccountService.delete(accountId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
