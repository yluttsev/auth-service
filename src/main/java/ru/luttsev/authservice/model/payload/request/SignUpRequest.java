package ru.luttsev.authservice.model.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Запрос на регистрацию
 *
 * @author Yuri Luttsev
 */
@Schema(description = "Запрос на регистрацию пользователя")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequest {

    @Schema(description = "Логин пользователя")
    private String login;

    @Schema(description = "Пароль пользователя")
    private String password;

    @Schema(description = "Email пользователя")
    private String email;

}
