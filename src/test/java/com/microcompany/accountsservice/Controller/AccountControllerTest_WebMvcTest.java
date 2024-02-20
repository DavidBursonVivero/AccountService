package com.microcompany.accountsservice.Controller;

import com.microcompany.accountsservice.controller.AccountController;
import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.persistence.AccountRepository;
import com.microcompany.accountsservice.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest_WebMvcTest {

     @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService service;

    @MockBean
    private AccountRepository repository;


    @BeforeEach
    public void setUp() {

        List<Account> accounts = Arrays.asList(
                new Account("Nueva Cuenta", (Date) null, 5, 25L));

        Mockito.when(service.getAccounts())
                .thenReturn(accounts);

        Mockito.when(repository.findByOwnerId(1L))
                .thenReturn(accounts);

        Mockito.when(repository.findAll())
                .thenReturn(accounts);

        Mockito.when(repository.save(Mockito.any(Account.class)))
                .thenAnswer(elem -> {
                    Account account = (Account) elem.getArguments()[0];
                    account.setId(100L);
                    return account;
                });
    }

    /* @Test
    public void givenAccount_whenGetAccount_thenStatus200() throws Exception {

        mvc.perform(get("/products?texto=" + texto).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[*].name", hasItem("Candy")))
        ;

    }*/



}