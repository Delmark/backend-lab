package by.delmark.backendlab.config;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
public class JwtConfiguration {

    @Value("${jwt.private.key: }")
    private String privateKey;

    @Getter
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        if (StringUtils.isBlank(privateKey)) {
            secretKey = Jwts.SIG.HS512.key().build();
            return;
        }
        secretKey = new SecretKeySpec(privateKey.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        Keys.hmacShaKeyFor(secretKey.getEncoded());
    }

    @Bean
    public JwtParser jwtParser() {
        return Jwts.parser().verifyWith(secretKey).build();
    }
}
