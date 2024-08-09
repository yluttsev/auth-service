package ru.luttsev.authservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.luttsev.authservice.config.PostgresContainer;

@SpringBootTest
@Import(PostgresContainer.class)
class AuthServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
