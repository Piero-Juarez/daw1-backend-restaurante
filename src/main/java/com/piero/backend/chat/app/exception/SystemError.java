package com.piero.backend.chat.app.exception;

public class SystemError extends RuntimeException {
    public SystemError(String message) {
        super(message);
    }
}
