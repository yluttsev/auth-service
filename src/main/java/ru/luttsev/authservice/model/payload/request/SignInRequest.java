package ru.luttsev.authservice.model.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Запрос на аутентификацию
 *
 * @author Yuri Luttsev
 */
@Schema(description = "Запрос на аутентификацию")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInRequest {

    @Schema(description = "Логин пользователя")
    private String login;

    @Schema(description = "Пароль пользователя")
    private String password;

}
