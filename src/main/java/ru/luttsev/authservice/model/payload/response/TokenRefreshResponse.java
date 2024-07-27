package ru.luttsev.authservice.model.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenRefreshResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expired_at")
    private long expiredAt;

}
