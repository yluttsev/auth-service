package ru.luttsev.authservice.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.luttsev.authservice.config.PostgresContainer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(PostgresContainer.class)
@Sql("/db/users/insert_test_users.sql")
@Transactional
class UserRoleControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Получение пользователем своих ролей")
    @WithMockUser(username = "test_user", roles = "USER")
    void testGetUserOwnRoles() throws Exception {
        mvc.perform(get("/auth/user-role"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Получение ролей любого пользователя с необходимыми правами")
    @WithMockUser(username = "test_user", roles = "ADMIN")
    void testGetCustomUserRolesWithPermissions() throws Exception {
        mvc.perform(get("/auth/user-role/user1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Получение ролей любого пользователя без необходимых прав")
    @WithMockUser(username = "test_user", roles = "USER")
    void testGetCustomUserRolesWithoutPermissions() throws Exception {
        mvc.perform(get("/auth/user-role/user1"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}
