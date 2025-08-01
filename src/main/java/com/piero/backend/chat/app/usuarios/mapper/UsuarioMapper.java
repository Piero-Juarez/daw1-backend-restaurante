package com.piero.backend.chat.app.usuarios.mapper;

import com.piero.backend.chat.app.config.AppUtils;
import com.piero.backend.chat.app.usuarios.dto.UsuarioDTORequest;
import com.piero.backend.chat.app.usuarios.dto.UsuarioDTOResponse;
import com.piero.backend.chat.app.usuarios.model.Usuario;
import com.piero.backend.chat.app.usuarios.model.enums.Rol;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UsuarioMapper {

    private final PasswordEncoder passwordEncoder;

    public UsuarioDTOResponse toDto(Usuario usuario) {
        if (usuario == null) { return null; }
        return UsuarioDTOResponse
                .builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .correo(usuario.getCorreo())
                .rol(AppUtils.capitalizarTexto(usuario.getRol().name()))
                .build();
    }

    public List<UsuarioDTOResponse> listToDto(List<Usuario> usuarios) {
        if (usuarios.isEmpty()) { return null; }
        return usuarios.stream().map(this::toDto).toList();
    }

    public Usuario toEntity(UsuarioDTORequest usuarioDTORequest) {
        if (usuarioDTORequest == null) { return null; }
        return Usuario
                .builder()
                .nombre(usuarioDTORequest.nombre())
                .apellido(usuarioDTORequest.apellido())
                .correo(usuarioDTORequest.correo())
                .clave(passwordEncoder.encode(usuarioDTORequest.clave()))
                .rol(Rol.valueOf(usuarioDTORequest.rol()))
                .build();
    }

    /*
    public static String capitalizar(String texto) {
        if (texto == null || texto.isBlank()) { return texto; }
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }
     */

}
