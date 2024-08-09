package ru.luttsev.authservice.model.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Запрос на установку ролей пользователю
 *
 * @author Yuri Luttsev
 */
@Schema(description = "Запрос на установку ролей пользователю")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddRoleRequest {

    @Schema(description = "Логин пользователя")
    private String user;

    @Schema(description = "Список ролей")
    private List<String> roles;

}
