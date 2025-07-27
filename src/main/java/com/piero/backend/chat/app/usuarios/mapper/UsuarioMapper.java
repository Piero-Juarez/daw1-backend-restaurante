package com.piero.backend.chat.app.usuarios.mapper;

import com.piero.backend.chat.app.usuarios.dto.UsuarioDTO;
import com.piero.backend.chat.app.usuarios.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioDTO toDto(Usuario usuario) {
        if (usuario == null) { return null; }
        return UsuarioDTO
                .builder()
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .correo(usuario.getCorreo())
                .rol(capitalizar(usuario.getRol().name()))
                .build();
    }

    public static String capitalizar(String texto) {
        if (texto == null || texto.isBlank()) { return texto; }
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }

}
