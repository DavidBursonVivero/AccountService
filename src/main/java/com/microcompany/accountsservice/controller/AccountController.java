package com.microcompany.accountsservice.controller;

import com.microcompany.accountsservice.exception.AccountNotfoundException;
import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.persistence.AccountRepository;
import com.microcompany.accountsservice.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountRepository repo;

    @Autowired
    private AccountService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Account> getOne(@PathVariable("id") @Min(1) Long account_id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                service.getAccount(account_id));

    }

    @PostMapping(value = "")
    public ResponseEntity<Account> crear (@RequestBody Account newAcc){
        return new ResponseEntity<>(service.create(newAcc), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Account> update (@PathVariable("id") Long account_id, @RequestBody Account account){
        return ResponseEntity.status(HttpStatus.OK).body(
                service.updateAccount(account_id, account)
        );
    }

    @PutMapping(value = "/{id}/retirar")
    public ResponseEntity<Account> withDraw (@PathVariable("id") Long id, @RequestBody int amount, @RequestBody Long ownerId){
        return ResponseEntity.status(HttpStatus.OK).body(
                service.withdrawBalance(id, amount, ownerId)
        );
    }

    @PutMapping(value = "/{id}/añadir")
    public ResponseEntity<Account> add (@PathVariable("id") Long id, @RequestBody int amount, @RequestBody Long ownerId){
        return ResponseEntity.status(HttpStatus.OK).body(
                service.addBalance(id, amount, ownerId)
        );
    }

    @DeleteMapping(value ="/{id}")
    public ResponseEntity delete (@PathVariable ("id") Long id){
        service.deleteAccountsUsingOwnerId(id);
        return ResponseEntity.noContent().build();
    }

}
