package com.piero.backend.chat.app.auth.repository;

import com.piero.backend.chat.app.auth.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
    List<Token> findAllValidIsFalseOrRevokedIsFalseByUsuarioId(Integer idUsuario);
}
