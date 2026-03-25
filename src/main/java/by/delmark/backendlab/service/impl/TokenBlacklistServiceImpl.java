package by.delmark.backendlab.service.impl;

import by.delmark.backendlab.service.TokenBlacklistService;
import io.jsonwebtoken.JwtParser;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    private final JwtParser jwtParser;
    private final Map<String, Date> tokenBlacklist = new HashMap<>();

    @Override
    public void putToken(String token) {
        if (isTokenBlacklisted(token)) {
            return;
        }

        Date expirationDate = jwtParser.parseSignedClaims(token).getPayload().getExpiration();
        tokenBlacklist.put(token, expirationDate);
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklist.containsKey(token);
    }

    @Scheduled(cron = "0 0 * * * *")
    public void cleanExpiredTokens() {
        Date now = Date.from(Instant.now());
        for (Map.Entry<String, Date> entry : tokenBlacklist.entrySet()) {
            if (entry.getValue().after(now)) {
                tokenBlacklist.remove(entry.getKey());
            }
        }
    }
}
