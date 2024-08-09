package ru.luttsev.authservice.model.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ответ с токеном доступа и refresh токеном
 *
 * @author Yuri Luttsev
 */
@Schema(description = "Ответ с токеном доступа и refresh токеном")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResponse {

    @Schema(description = "Токен доступа")
    @JsonProperty("access_token")
    private String accessToken;

    @Schema(description = "Refresh токен")
    @JsonProperty("refresh_token")
    private String refreshToken;

    @Schema(description = "Дата истечения срока действия токена доступа")
    @JsonProperty("expired_at")
    private long expiredAt;

}
