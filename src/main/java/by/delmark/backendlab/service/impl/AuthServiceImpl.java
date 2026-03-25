package by.delmark.backendlab.service.impl;

import by.delmark.backendlab.config.JwtConfiguration;
import by.delmark.backendlab.pojo.model.User;
import by.delmark.backendlab.pojo.request.TokenResponse;
import by.delmark.backendlab.pojo.request.UserRequest;
import by.delmark.backendlab.service.AuthService;
import by.delmark.backendlab.service.TokenBlacklistService;
import by.delmark.backendlab.service.UserService;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final TokenBlacklistService tokenBlacklistService;
    private final JwtConfiguration jwtConfiguration;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TokenResponse authorize(UserRequest userRequest) {
        User user = userService.findByLogin(userRequest.getLogin())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Некорректный логин или пароль"));


        if (BooleanUtils.isFalse(passwordEncoder.matches(userRequest.getPassword(), user.getPassword()))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Некорректный логин или пароль");
        }

        Date exp = Date.from(OffsetDateTime.now().plusHours(1).toInstant());
        String token = Jwts.builder()
                .signWith(jwtConfiguration.getSecretKey(), Jwts.SIG.HS512)
                .subject(user.getLogin())
                .expiration(exp)
                .issuedAt(Date.from(OffsetDateTime.now().toInstant()))
                .compact();

        return new TokenResponse(token, exp.getTime());
    }

    @Override
    public void invalidateSession() {
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        if (BooleanUtils.isFalse(tokenBlacklistService.isTokenBlacklisted(token))) {
            tokenBlacklistService.putToken(token);
        }
    }
}
