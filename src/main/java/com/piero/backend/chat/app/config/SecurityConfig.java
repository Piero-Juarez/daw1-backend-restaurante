package com.piero.backend.chat.app.config;

import com.piero.backend.chat.app.auth.model.Token;
import com.piero.backend.chat.app.auth.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final AuthenticationProvider authenticationProvider;
    private final TokenRepository tokenRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        // Endpoints públicos
                        .requestMatchers("api/auth/**").permitAll()
                        .requestMatchers("/websocket/**").permitAll()
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        /* API USUARIOS */
                        .requestMatchers("/api/usuarios/me").permitAll()
                        .requestMatchers("/api/usuarios/**").hasRole("ADMINISTRADOR")

                        /* API CATEGORIAS */
                        .requestMatchers("/api/categorias/**").hasRole("ADMINISTRADOR")

                        /* API MESAS */
                        .requestMatchers(
                                "/api/mesas/disponibles",
                                "/api/mesas/con-orden-pendiente",
                                "api/mesas/cambiar-estado/**"
                        ).hasAnyRole("ADMINISTRADOR", "CAMARERO")
                        .requestMatchers("/api/mesas/**").hasRole("ADMINISTRADOR")

                        /* API ITEMS MENU */
                        .requestMatchers("/api/items-menu/**").hasRole("ADMINISTRADOR")

                        /* API ORDENES */
                        .requestMatchers("/api/ordenes/pagar/**").hasAnyRole("ADMINISTRADOR", "CAJERO")
                        .requestMatchers("/api/ordenes/**").hasAnyRole("ADMINISTRADOR","CAJERO", "CAMARERO")

                        /* API REPORTES */
                        .requestMatchers("/api/reportes/**").hasAnyRole("ADMINISTRADOR", "CAJERO")

                        // Lo demás requiere autenticación
                        .anyRequest().authenticated()
                )

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                .logout(logout ->
                        logout.logoutUrl("/api/auth/logout")
                                .addLogoutHandler((request, response, authentication) ->{
                                    final var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
                                    logout(authHeader);
                                })
                                .logoutSuccessHandler((request, response, authentication) ->
                                        SecurityContextHolder.clearContext())
                );
        return http.build();
    }

    private void logout(final String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid token");
        }
        final String jwtToken = token.substring(7);
        final Token foundToken = tokenRepository.findByToken(jwtToken).orElseThrow(() -> new IllegalArgumentException("nvalid token"));
        foundToken.setExpired(true);
        foundToken.setRevoked(true);
        tokenRepository.save(foundToken);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost:*" // Cualquier puerto local
        ));

        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "HEAD"
        ));

        configuration.setAllowedHeaders(List.of("*"));

        configuration.setExposedHeaders(Arrays.asList(
                "Authorization",
                "X-Total-Count"
        ));

        configuration.setAllowCredentials(true);

        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
