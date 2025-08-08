package com.piero.backend.chat.app.usuarios.repository;

import com.piero.backend.chat.app.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findAllByActivo(Boolean activo);

    Optional<Usuario> findByCorreo(String correo);

    @Modifying
    @Query("UPDATE Usuario u SET u.activo = false WHERE u.id = :id")
    void eliminacionLogicaPorId(@Param("id") Integer id);

}
