package com.piero.backend.chat.app.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

// Respuesta del token enviado a Angular
public record TokenResponse(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("refresh_token")
        String refreshToken
) { }
