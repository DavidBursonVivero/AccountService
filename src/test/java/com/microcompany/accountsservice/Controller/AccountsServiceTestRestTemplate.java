package com.microcompany.accountsservice.Controller;

import com.microcompany.accountsservice.model.Account;
import io.swagger.v3.oas.models.PathItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql("classpath:datos_prueba.sql")
public class AccountsServiceTestRestTemplate {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void givenurl_Balance_whenadd_thenSuccess() throws Exception {

        Account addBalance = new Account(1L, "Cuenta de ahorro", null, 80, 25L);

        HttpHeaders headers = new HttpHeaders();
        headers.add("ACCEPT", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Account> entity = new HttpEntity<>(addBalance, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/accounts/" + addBalance.getId() + "/añadir?amount=" + addBalance.getBalance() + "&ownerId=" + addBalance.getOwnerId(),
                HttpMethod.PUT, entity, String.class);


        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);

    }

    @Test
    public void givenurl_Account_whenDelete_thenSuccess() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add("ACCEPT", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/accounts/" + 1L,
                HttpMethod.DELETE, entity, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }


}
