package com.piero.backend.chat.app.exception;

public class BusinessError extends RuntimeException{
    public BusinessError(String message) {
        super(message);
    }
}
