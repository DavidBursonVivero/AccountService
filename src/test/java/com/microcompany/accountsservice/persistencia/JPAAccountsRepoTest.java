package com.microcompany.accountsservice.persistencia;

import com.microcompany.accountsservice.exception.AccountNotfoundException;
import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.persistence.AccountRepository;
import com.microcompany.accountsservice.services.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.logging.Logger;

@ExtendWith(SpringExtension.class)
@DataJpaTest()
@ComponentScan(basePackages = {"com.microcompany.accountsservice.persistencia"})
@AutoConfigureTestEntityManager
@Sql("classpath:datos_prueba.sql")
@ActiveProfiles({"test"})
public class JPAAccountsRepoTest {

    //private static final Logger logger = (Logger) LoggerFactory.getLogger(JPAAccountsRepoTest.class);
    @Autowired
    private TestEntityManager em;

    @Autowired
    private AccountRepository accountJpaRepo;


    @Test
    void deleteById() {
        Account account = accountJpaRepo.findById(1L).orElseThrow(() -> new AccountNotfoundException(1L));
        this.accountJpaRepo.delete(account);
    }

    @Test
    void add() {
        Account newAccount = accountJpaRepo.findById(1L).orElseThrow(() -> new AccountNotfoundException(1L));
        Account account = null;
        int newBalance = newAccount.getBalance() + 58;
        newAccount.setBalance(newBalance);
    }


}
