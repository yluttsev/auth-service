package ru.luttsev.authservice.model.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ответ обновления токена доступа
 *
 * @author Yuri Luttsev
 */
@Schema(description = "Ответ обновления токена доступа")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenRefreshResponse {

    @Schema(description = "Токен доступа")
    @JsonProperty("access_token")
    private String accessToken;

    @Schema(description = "Дата истечения срока действия токена доступа")
    @JsonProperty("expired_at")
    private long expiredAt;

}
