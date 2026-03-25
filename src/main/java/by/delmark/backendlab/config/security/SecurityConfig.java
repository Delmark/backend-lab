package by.delmark.backendlab.config.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@Getter
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/auth/login").permitAll()
                           .requestMatchers("/auth/register").permitAll()
                           .requestMatchers("/auth/logout").authenticated()
                           .requestMatchers("/error**").permitAll()
                           .requestMatchers("/swagger-ui/**").permitAll()
                           .requestMatchers("/swagger-ui.html").permitAll()
                           .requestMatchers("/v3/api-docs/**").permitAll()
                           .anyRequest().authenticated()
                )
                .formLogin(FormLoginConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
