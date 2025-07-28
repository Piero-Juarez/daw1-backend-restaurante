package com.piero.backend.chat.app.usuarios.dto;

import lombok.Builder;

@Builder
public record UsuarioDTOResponse(
        Integer id,
        String nombre,
        String apellido,
        String correo,
        String rol
) { }
