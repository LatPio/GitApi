package com.example.GitApi.exeption.config;

import com.example.GitApi.exeption.ErrorWrapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ErrorWrapper.class)
    protected ResponseEntity handleGlobalException(ErrorWrapper e){
        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorResponse.builder()
                        .status(e.getStatus())
                        .message(e.getMessage())
                        .build());
    }
}
