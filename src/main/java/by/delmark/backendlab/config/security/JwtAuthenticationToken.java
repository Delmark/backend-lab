package by.delmark.backendlab.config.security;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String token;
    private final UserDetails userDetails;

    public JwtAuthenticationToken(String token, UserDetails userDetails) {
        super(Collections.emptyList()); // у нас нет системы прав
        this.token = token;
        this.userDetails = userDetails;
    }

    @Override
    public @Nullable Object getCredentials() {
        return token;
    }

    @Override
    public @Nullable Object getPrincipal() {
        return userDetails;
    }

    @Override
    public @Nullable String getName() {
        return userDetails.getUsername();
    }
}
