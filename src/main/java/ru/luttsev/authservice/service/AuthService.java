package ru.luttsev.authservice.service;

import ru.luttsev.authservice.model.payload.request.SignInRequest;
import ru.luttsev.authservice.model.payload.request.SignUpRequest;
import ru.luttsev.authservice.model.payload.request.TokenRefreshRequest;
import ru.luttsev.authservice.model.payload.response.SignUpResponse;
import ru.luttsev.authservice.model.payload.response.TokenResponse;

import javax.security.auth.login.CredentialException;

/**
 * Сервис для аутентификации и регистрации
 *
 * @author Yuri Luttsev
 */
public interface AuthService {

    /**
     * Регистрация пользователя
     *
     * @param signUpRequest {@link SignUpRequest} запрос на регистрацию пользователя
     * @return {@link SignUpRequest} ответ регистрации
     */
    SignUpResponse signUp(SignUpRequest signUpRequest);

    /**
     * Аутентификация пользователя
     *
     * @param signInRequest {@link SignInRequest} запрос на аутентификацию пользователя
     * @return {@link TokenResponse} ответ, содержащий токены доступа
     * @throws CredentialException ошибка проверки пользовательских данных
     */
    TokenResponse signIn(SignInRequest signInRequest) throws CredentialException;

    /**
     * Обновление токена доступа
     *
     * @param tokenRefreshRequest {@link TokenRefreshRequest} запрос, содержаший refresh токен
     * @return {@link TokenResponse} ответ с обновленным токеном доступа
     */
    TokenResponse refreshToken(TokenRefreshRequest tokenRefreshRequest);

}
