package ru.luttsev.authservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.luttsev.authservice.model.payload.request.SignInRequest;
import ru.luttsev.authservice.model.payload.request.SignUpRequest;
import ru.luttsev.authservice.model.payload.request.TokenRefreshRequest;
import ru.luttsev.authservice.model.payload.response.SignUpResponse;
import ru.luttsev.authservice.model.payload.response.TokenResponse;
import ru.luttsev.authservice.service.AuthService;

import javax.security.auth.login.CredentialException;

/**
 * Контроллер для работы с аутентификацией
 *
 * @author Yuri Luttsev
 */
@Tag(name = "auth", description = "API для аутентификации и регистрации пользователей")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Регистрирует пользователя
     *
     * @param signUpRequest запрос на регистрацию
     * @return {@link SignUpResponse} ответ регистрации
     */
    @Operation(summary = "Регистрация пользователя", method = "PUT")
    @ApiResponse(
            responseCode = "200",
            description = "Успешная регистрация пользователя",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(
                            implementation = SignUpResponse.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "409",
            description = "Пользователь с таким логином/email уже существует",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(
                            implementation = ProblemDetail.class
                    )
            )
    )
    @PutMapping("/signup")
    public SignUpResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        return authService.signUp(signUpRequest);
    }

    /**
     * Аутентифицирует пользователя
     *
     * @param signInRequest запрос на аутентификацию
     * @return {@link TokenResponse} ответ с ключом доступа
     * @throws CredentialException ошибка проверки пользовательских данных
     */
    @Operation(summary = "Аутентификация пользователя", method = "POST")
    @ApiResponse(
            responseCode = "200",
            description = "Успешная аутентификация пользователя",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(
                            implementation = TokenResponse.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Неверные учетные данные пользователя",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(
                            implementation = ProblemDetail.class
                    )
            )
    )
    @PostMapping("/signin")
    public TokenResponse signIn(@RequestBody SignInRequest signInRequest) throws CredentialException {
        return authService.signIn(signInRequest);
    }

    /**
     * Обновляет ключ доступа с помощью Refresh токена
     *
     * @param request запрос на обновление ключа доступа
     * @return {@link TokenResponse} ответ с обновленным ключом доступа
     */
    @Operation(summary = "Обновление токена доступа с помощью refresh токена", method = "POST")
    @ApiResponse(
            responseCode = "200",
            description = "Успешное обновление токена доступа",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(
                            implementation = TokenResponse.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Проблемы с refresh токеном",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(
                            implementation = ProblemDetail.class
                    )
            )
    )
    @PostMapping("/refresh-token")
    public TokenResponse refreshToken(@RequestBody TokenRefreshRequest request) {
        return authService.refreshToken(request);
    }

}
