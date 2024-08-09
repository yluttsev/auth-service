package ru.luttsev.authservice.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.luttsev.authservice.config.PostgresContainer;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PostgresContainer.class)
@Sql("/db/users/insert_test_users.sql")
@Transactional
class AppUserRepositoryTests {

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    @DisplayName("Поиск пользователя по логину")
    void testFindAppUserByLogin() {
        assertTrue(appUserRepository.findByLogin("user1").isPresent());
    }

    @Test
    @DisplayName("Проверка существования пользователя по логину")
    void testCheckLogin() {
        assertTrue(appUserRepository.checkLogin("user1"));
    }

    @Test
    @DisplayName("Проверка существования пользователя по email")
    void testCheckEmail() {
        assertTrue(appUserRepository.checkEmail("user1@inbox.ru"));
    }

}
