package by.delmark.backendlab.config.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;

@Configuration
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtParser jwtParser;
    private final UserDetailsService userDetailsService;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .addMixIn(ProblemDetail.class, ProblemDetailsRenameMixin.class)
            .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Jws<Claims> claims = jwtParser.parseSignedClaims(token);
                String username = claims.getPayload().getSubject();
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                JwtAuthenticationToken auth = new JwtAuthenticationToken(token, userDetails);
                auth.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                writeErrorResponse(e, response, request);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void writeErrorResponse(Exception e, HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage());
        detail.setInstance(URI.create(request.getRequestURI()));
        detail.setProperty("timestamp", System.currentTimeMillis());

        String messsageBody = objectMapper.writeValueAsString(detail);
        writer.append(messsageBody);
        writer.flush();
    }
}
