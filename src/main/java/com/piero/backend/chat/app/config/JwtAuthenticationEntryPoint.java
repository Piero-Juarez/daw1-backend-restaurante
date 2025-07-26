package com.piero.backend.chat.app.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.warn("Unauthorized error: {}", authException.getMessage());

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Respuesta JSON personalizada
        String jsonResponse = """
            {
                "error": "Unauthorized",
                "message": "Token JWT inválido o ausente",
                "status": 401,
                "timestamp": "%s",
                "path": "%s"
            }
            """.formatted(Instant.now().toString(), request.getRequestURI());

        response.getWriter().write(jsonResponse);

    }
}
