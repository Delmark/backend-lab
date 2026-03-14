package by.delmark.backendlab.pojo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class TokenResponse {
    @JsonProperty("jwt_token")
    String jwtToken;

    @JsonProperty("expires_in")
    Long expiresIn;
}
