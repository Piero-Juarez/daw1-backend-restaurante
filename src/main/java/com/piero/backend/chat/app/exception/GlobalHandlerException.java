package com.piero.backend.chat.app.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalHandlerException  {

    @ExceptionHandler(BusinessError.class)
    public ResponseEntity<Object> handleBusinessError(BusinessError ex){
        return ResponseEntity.badRequest().body(new ResponseError(ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(SystemError.class)
    public ResponseEntity<Object> handleSystemError(SystemError ex){
        return ResponseEntity.badRequest().body(new ResponseError(ex.getMessage(), LocalDateTime.now()));
    }

}
