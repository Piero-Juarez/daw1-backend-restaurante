package com.piero.backend.chat.app.exception;

import java.time.LocalDateTime;

public record ResponseError(
        String message,
        LocalDateTime timestamp
) { }
