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
    Account newAccount = new Account(1L, "Cuenta de ahorro", null, 80, 25L);

    Mockito.when(service.create(Mockito.any(Account.class)))
            .thenReturn(newAccount);
    Mockito.when(service.addBalance(newAccount.getId(), 100, newAccount.getOwnerId()))
            .thenReturn(newAccount);
    Mockito.doNothing().when(service).delete(newAccount.getId());

}

     @Test
    void givenAdccount_WhenGCreateAccount_thenStatus200() throws Exception {

        Account newAccount = new Account(1L, "Cuenta de ahorro", null, 80, 25L);

        mvc.perform(post("/accounts")
                        .content(JsonUtil.asJsonString(newAccount))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id", is(greaterThan(0))));
    }

     @Test
    public void givenAdd_Cash_WhenGetAccount_thenStatus200() throws Exception {
        Long id = 1L;
        int amount = 100;
        Long ownerId = 25L;

        mvc.perform(put("/accounts/" + id + "/añadir?amount=" + amount + "&ownerId=" + ownerId).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id", is(greaterThan(0))));
    }

    @Test
    public void givenAdd_Cash_WhenGetAccount_thenStatus412() throws Exception {
        Long id = 1L;
        int amount = -200;
        Long ownerId = 25L;

        mvc.perform(put("/accounts/" + id + "/añadir?amount=" + amount + "&ownerId=" + ownerId).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isPreconditionFailed())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE));

    }

    @Test
    public void givenDelete_Account_WhenGetAccount_thenStatus204() throws Exception {
        Long id = 1L;
        mvc.perform(delete("/accounts/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenDelete_NonExistingAccount_ThenReturns412() throws Exception {
        Long id = 0L;
        mvc.perform(delete("/accounts/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed());
    }

}
