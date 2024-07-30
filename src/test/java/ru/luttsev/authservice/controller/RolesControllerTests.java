package ru.luttsev.authservice.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.luttsev.authservice.config.PostgresContainer;
import ru.luttsev.authservice.model.payload.request.AddRoleRequest;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(PostgresContainer.class)
@Sql("/db/users/insert_test_users.sql")
@Transactional
class RolesControllerTests {

    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Добавление роли пользователю без необходимых прав")
    @WithMockUser(username = "test_user")
    void testAddRoleToUserWithoutPermissions() throws Exception {
        AddRoleRequest addRoleRequest = new AddRoleRequest(
                "user2", List.of("ROLE_ADMIN")
        );
        mvc.perform(put("/auth/roles/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRoleRequest)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Добавление роли пользователю с необходимыми правами")
    @WithMockUser(username = "test_user", roles = {"ADMIN"})
    void testAddRoleToUserWithPermissions() throws Exception {
        AddRoleRequest addRoleRequest = new AddRoleRequest(
                "user2", List.of("ROLE_ADMIN")
        );
        mvc.perform(put("/auth/roles/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRoleRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
