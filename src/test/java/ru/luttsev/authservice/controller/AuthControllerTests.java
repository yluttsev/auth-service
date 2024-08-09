package ru.luttsev.authservice.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.luttsev.authservice.config.PostgresContainer;
import ru.luttsev.authservice.model.entity.AppUser;
import ru.luttsev.authservice.model.entity.RefreshToken;
import ru.luttsev.authservice.model.payload.request.SignInRequest;
import ru.luttsev.authservice.model.payload.request.SignUpRequest;
import ru.luttsev.authservice.model.payload.request.TokenRefreshRequest;
import ru.luttsev.authservice.service.AppUserService;
import ru.luttsev.authservice.service.RefreshTokenService;
import ru.luttsev.authservice.service.impl.JwtService;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(PostgresContainer.class)
@Sql("/db/users/insert_test_users.sql")
@Transactional
class AuthControllerTests {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Регистрация нового пользователя")
    void testSignUpNonExistentUser() throws Exception {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .login("user3")
                .password("{noop}user3password")
                .email("user3@inbox.ru")
                .build();
        mvc.perform(put("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Регистрация существующего пользователя")
    void testSignUpExistentUser() throws Exception {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .login("user1")
                .email("user1@inbox.ru")
                .build();
        mvc.perform(put("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Успешный вход пользователя в систему")
    void testSuccessfullySignInUser() throws Exception {
        SignInRequest signInRequest = SignInRequest.builder()
                .login("user1")
                .password("user1password")
                .build();
        mvc.perform(post("/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Неуспешный вход пользователя в систему")
    void testFailureSignInUser() throws Exception {
        SignInRequest signInRequest = SignInRequest.builder()
                .login("user1")
                .password("INCORRECT_PASSWORD")
                .build();
        mvc.perform(post("/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInRequest)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testSuccessfullyRefreshToken() throws Exception {
        AppUser user1 = appUserService.getUserByLogin("user1");
        String refreshToken = jwtService.generateRefreshToken(user1.getLogin());
        LocalDateTime refreshTokenExpiration = LocalDateTime
                .ofInstant(jwtService.getTokenExpiration(refreshToken).toInstant(), ZoneId.systemDefault());
        RefreshToken user1RefreshToken = RefreshToken.builder()
                .token(refreshToken)
                .user(user1)
                .expirationDate(refreshTokenExpiration)
                .build();
        refreshTokenService.save(user1RefreshToken);

        TokenRefreshRequest tokenRefreshRequest = new TokenRefreshRequest(refreshToken);
        mvc.perform(post("/auth/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tokenRefreshRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
