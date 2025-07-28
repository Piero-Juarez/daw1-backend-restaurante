package com.piero.backend.chat.app.usuarios.dto;

public record UsuarioDTORequest(
        String nombre,
        String apellido,
        String correo,
        String clave,
        String rol
) { }
