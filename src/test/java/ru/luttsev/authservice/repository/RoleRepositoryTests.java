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

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PostgresContainer.class)
@Sql({"/db/users/insert_test_users.sql", "/db/roles/insert_test_roles.sql"})
@Transactional
class RoleRepositoryTests {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @DisplayName("Поиск ролей пользователя по его логину")
    void testFindRolesByLogin() {
        assertEquals(2, roleRepository.findRolesByLogin("user1").size());
    }

}
