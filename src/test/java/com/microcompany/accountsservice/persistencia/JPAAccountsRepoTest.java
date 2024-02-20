package com.microcompany.accountsservice.persistencia;

import com.microcompany.accountsservice.persistence.AccountRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.logging.Logger;

@ExtendWith(SpringExtension.class)
@DataJpaTest()
@ComponentScan(basePackages = {"com.microcompany.productsservice.persistence"})
@AutoConfigureTestEntityManager
//@ActiveProfiles({"test"})
public class JPAAccountsRepoTest {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(JPAAccountsRepoTest.class);
    @Autowired
    private TestEntityManager em;

    @Autowired
    private AccountRepository accountJpaRepo;



}
