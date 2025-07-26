package com.piero.backend.chat.app.auth.dto;

// Angular envía estos datos para registrar un nuevo usuario
public record RegisterRequest(
        String correo,
        String clave,
        String nombre,
        String apellido,
        String rol
) { }
