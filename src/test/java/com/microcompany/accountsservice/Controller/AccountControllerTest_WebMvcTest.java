package com.microcompany.accountsservice.Controller;

import com.microcompany.accountsservice.controller.AccountController;
import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.persistence.AccountRepository;
import com.microcompany.accountsservice.services.AccountService;
import com.microcompany.accountsservice.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

        Mockito.when(service.addBalance(1L, 50, 25L))
                .thenReturn(accounts.get(0));

        Mockito.when(repository.findByOwnerId(1L))
                .thenReturn(accounts);

        Mockito.when(repository.findAll())
                .thenReturn(accounts);

        Mockito.when(repository.save(Mockito.any(Account.class)))
                .thenAnswer(elem -> {
                    Account account = (Account) elem.getArguments()[0];
                    account.setId(1L);
                    return account;
                });
    }

    /*@Test
    public void givenAdd_Cash_WhenGetAccount_thenStatus200() throws Exception {
        Long id = 1L;
        int amount = 100;
        Long ownerId = 25L;

        mvc.perform(put("/accounts/" + id + "/añadir?amount=" + amount + "&ownerId=" + ownerId)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id", is(greaterThan(0))));
    }*/

     @Test
    void givenAdd_Cash_WhenGetAccount_thenStatus200() throws Exception {
        // given
       Long id = 1L;
        int amount = 100;
        Long ownerId = 25L;

        // when - then
        mvc.perform(post("/products")
                        .content(JsonUtil.asJsonString(accounts))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id", is(greaterThan(0))))
        ;

    }
}
