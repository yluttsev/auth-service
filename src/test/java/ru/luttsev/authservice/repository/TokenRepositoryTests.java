package ru.luttsev.authservice.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.luttsev.authservice.config.PostgresContainer;
import ru.luttsev.authservice.model.entity.RefreshToken;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PostgresContainer.class)
@Sql({"/db/users/insert_test_users.sql",
        "/db/refresh-tokens/insert_test_refresh_tokens.sql"})
class TokenRepositoryTests {

    @Autowired
    private TokenRepository tokenRepository;

    @Test
    @DisplayName("Поиск сущности токена по refresh токену")
    void testFindByToken() {
        assertTrue(tokenRepository.findByToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIiLCJpYXQiOjE3MjIyNzcyODIsImV4cCI6MTc1MzgxMzI4MywiYXVkIjoiIiwic3ViIjoidXNlcjEifQ.4lsSROzX1otFLy5CHzNdRPA6oDsWNUE8pVU9KBaBa4Y").isPresent());
    }

    @Test
    @DisplayName("Удаление токена у пользователя")
    void testDeleteTokenByUser() {
        RefreshToken refreshToken = tokenRepository.findByToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIiLCJpYXQiOjE3MjIyNzcyODIsImV4cCI6MTc1MzgxMzI4MywiYXVkIjoiIiwic3ViIjoidXNlcjEifQ.4lsSROzX1otFLy5CHzNdRPA6oDsWNUE8pVU9KBaBa4Y").get();
        UUID id = refreshToken.getUser().getId();
        tokenRepository.deleteByUserId(id);

        assertTrue(tokenRepository.findByToken(refreshToken.getToken()).isEmpty());
    }

}
