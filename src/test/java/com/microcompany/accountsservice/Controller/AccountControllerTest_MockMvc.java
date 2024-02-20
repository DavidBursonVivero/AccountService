package com.microcompany.accountsservice.Controller;

import com.microcompany.accountsservice.persistence.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
// @TestPropertySource( locations = "classpath:application-integrationtest.properties")
@ActiveProfiles("test")
@Sql("classpath:datos_prueba.sql")
public class AccountControllerTest_MockMvc {

    @Autowired
    private AccountRepository repo;

    @Autowired
    MockMvc mvc;

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
        int amount = -100;
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
                .andExpect(status().isNotFound());
    }
}
