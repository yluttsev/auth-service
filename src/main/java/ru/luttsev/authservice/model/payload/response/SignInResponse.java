package ru.luttsev.authservice.model.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expired_at")
    private long expiredAt;

}
