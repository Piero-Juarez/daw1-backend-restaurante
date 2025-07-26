package com.piero.backend.chat.app.auth.service;

import com.piero.backend.chat.app.auth.dto.LoginRequest;
import com.piero.backend.chat.app.auth.dto.RegisterRequest;
import com.piero.backend.chat.app.auth.dto.TokenResponse;
import com.piero.backend.chat.app.auth.model.Token;
import com.piero.backend.chat.app.auth.model.enums.TokenType;
import com.piero.backend.chat.app.auth.repository.TokenRepository;
import com.piero.backend.chat.app.usuarios.model.Usuario;
import com.piero.backend.chat.app.usuarios.model.enums.Rol;
import com.piero.backend.chat.app.usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenResponse register(RegisterRequest request) {
        Usuario usuario = Usuario.builder()
                .nombre(request.nombre())
                .apellido(request.apellido())
                .correo(request.correo())
                .clave(passwordEncoder.encode(request.clave()))
                .rol(Rol.valueOf(request.rol()))
                .build();
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        String jwtToken = jwtService.generateToken(usuarioGuardado);
        String refreshToken = jwtService.generateRefreshToken(usuarioGuardado);
        saveUserToken(usuarioGuardado, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    }

    public TokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.correo(),
                        request.clave()
                )
        );
        Usuario usuarioEncontrado = usuarioRepository.findByCorreo(request.correo()).orElseThrow();
        String jwtToken = jwtService.generateToken(usuarioEncontrado);
        String refreshToken = jwtService.generateRefreshToken(usuarioEncontrado);
        revokeAllUserTokens(usuarioEncontrado);
        saveUserToken(usuarioEncontrado, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    }

    private void saveUserToken(Usuario usuario, String jwtToken) {
        Token token = Token.builder()
                .usuario(usuario)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public TokenResponse refreshToken(final String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Bearer token");
        }
        final String refreshToken = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail == null) {
            throw new IllegalArgumentException("Invalid Refresh token");
        }
        final Usuario usuarioEncontrado = usuarioRepository.findByCorreo(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario con correo: " + userEmail + ", no encontrado"));
        if (!jwtService.isTokenValid(refreshToken, usuarioEncontrado)) {
            throw new IllegalArgumentException("Invalid Refresh token");
        }
        final String accessToken = jwtService.generateToken(usuarioEncontrado);
        revokeAllUserTokens(usuarioEncontrado);
        saveUserToken(usuarioEncontrado, accessToken);
        return new TokenResponse(accessToken, refreshToken);
    }

    private void revokeAllUserTokens(final Usuario usuario) {
        final List<Token> validUserTokens = tokenRepository.findAllValidIsFalseOrRevokedIsFalseByUsuarioId(usuario.getId());
        if (!validUserTokens.isEmpty()) {
            for (final Token token : validUserTokens) {
                token.setExpired(true);
                token.setRevoked(true);
            }
            tokenRepository.saveAll(validUserTokens);
        }
    }

}
