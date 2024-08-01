package ru.luttsev.authservice.model.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * Ответ со списком ролей пользователя
 *
 * @author Yuri Luttsev
 */
@Schema(description = "Ответ со списом ролей пользователя")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRolesResponse {

    @Schema(description = "Логин пользователя")
    private String user;

    @Schema(description = "Список ролей пользователя")
    private Collection<String> roles;

}
