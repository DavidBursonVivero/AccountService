package com.microcompany.accountsservice.controller;

import com.microcompany.accountsservice.exception.AccountNotfoundException;
import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.persistence.AccountRepository;
import com.microcompany.accountsservice.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@Validated
@CrossOrigin(origins = {"*"}, allowedHeaders = "*")
public class AccountController {

    @Autowired
    private AccountRepository repo;

    @Autowired
    private AccountService service;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cuando hay cuentas a devolver."),
        @ApiResponse(responseCode = "404", description = "Cuando no hay cuentas a devolver.")
    })

    @GetMapping(value = "/{id}")
public ResponseEntity<Account> getOne(
    @Parameter(name = "Buscar", description = "identificador unico de la cuenta", example = "1")
    @RequestParam(required = false, defaultValue = "") @Size(min = 0, max = 10) String texto,
    @PathVariable("id") @Min(1) Long account_id) {
    return ResponseEntity.status(HttpStatus.OK).body(
            service.getAccount(account_id));
}


    @PostMapping(value = "")
    public ResponseEntity<Account> crear ( @Parameter(name = "Crear", description = "Crea una nueva cuenta bancaria", example = "Nueva cuenta ahorro")
    @RequestParam(required = false, defaultValue = "") String texto, @RequestBody Account newAcc){
        return new ResponseEntity<>(service.create(newAcc), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Account> update (@Parameter(name = "Editar", description = "Editar una nueva cuenta bancaria", example = "editar cuenta ahorro")
    @RequestParam(required = false, defaultValue = "") String texto, @PathVariable("id") Long account_id, @RequestBody Account account){
        return ResponseEntity.status(HttpStatus.OK).body(
                service.updateAccount(account_id, account)
        );
    }

    @PutMapping(value = "/{id}/retirar")
    public ResponseEntity<Account> withDraw (@Parameter(name = "Retirar", description = "Retirar dinero a una cuenta bancaria", example = "2")
    @RequestParam(required = false, defaultValue = "") String texto, @PathVariable("id") Long id, @RequestParam int amount, @RequestParam Long ownerId){
        return ResponseEntity.status(HttpStatus.OK).body(
                service.withdrawBalance(id, amount, ownerId)
        );
    }

    @PutMapping(value = "/{id}/añadir")
    public ResponseEntity<Account> add (@Parameter(name = "Añadir", description = "Añadir dinero a una cuenta bancaria", example = "20")
    @RequestParam(required = false, defaultValue = "") String texto, @PathVariable("id") Long id, @RequestParam int amount, @RequestParam Long ownerId){
        return ResponseEntity.status(HttpStatus.OK).body(
                service.addBalance(id, amount, ownerId)
        );
    }

    @DeleteMapping(value ="/{id}")
    public ResponseEntity <Account> delete (@PathVariable ("id") Long id){
        service.deleteAccountsUsingOwnerId(id);
        return ResponseEntity.noContent().build();
    }

}
