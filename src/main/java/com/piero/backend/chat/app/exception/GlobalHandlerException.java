package com.piero.backend.chat.app.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalHandlerException  {

    @ExceptionHandler(ErrorResponse.class)
    public ResponseEntity<Object> handleBusinessError(ErrorResponse ex, HttpServletRequest request){
        HttpStatus status = ex.getHttpStatus();
        ResponseError responseError = new ResponseError(
                status.getReasonPhrase(),
                ex.getMessage(),
                status.value(),
                LocalDateTime.now(),
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(responseError);
    }

}
