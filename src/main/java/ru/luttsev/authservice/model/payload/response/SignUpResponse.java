package ru.luttsev.authservice.model.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ответ регистрации
 *
 * @author Yuri Luttsev
 */
@Schema(description = "Ответ регистрации пользователя")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignUpResponse {

    @Schema(description = "Сообщение ответа")
    private String message;

}
