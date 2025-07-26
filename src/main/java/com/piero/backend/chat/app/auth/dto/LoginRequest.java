package com.piero.backend.chat.app.auth.dto;

// Lo que Angular necesita enviarnos para iniciar sesión
public record LoginRequest(
        String correo,
        String clave
) { }
