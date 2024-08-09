package ru.luttsev.authservice.model.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Запрос на обновление токена доступа
 *
 * @author Yuri Luttsev
 */
@Schema(description = "Запрос на обновление токена доступа")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenRefreshRequest {

    @Schema(description = "Refresh токен")
    @JsonProperty("refresh_token")
    private String refreshToken;

}
