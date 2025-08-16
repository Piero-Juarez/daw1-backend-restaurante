package com.piero.backend.chat.app.exception;

import java.time.LocalDateTime;

public record ResponseError(
        String error,
        String message,
        int status,
        LocalDateTime timestamp,
        String path
) { }
