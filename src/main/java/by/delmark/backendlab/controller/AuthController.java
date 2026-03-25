package by.delmark.backendlab.controller;

import by.delmark.backendlab.pojo.request.TokenResponse;
import by.delmark.backendlab.pojo.request.UserRequest;
import by.delmark.backendlab.service.AuthService;
import by.delmark.backendlab.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authorization", description = "Авторизация")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody UserRequest user) {
        userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @Operation(summary = "Получение JWT токена")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponse> authorize(@Valid @RequestBody UserRequest user) {
        return ResponseEntity.ok(authService.authorize(user));
    }

    @Operation(summary = "Завершение сессии")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> invalidateJwtSession() {
        authService.invalidateSession();
        return ResponseEntity.ok().build();
    }
}
