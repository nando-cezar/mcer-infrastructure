package org.mcer.authentication.exception;

import org.mcer.authentication.entities.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        fieldErrors.put("username/password", "Credenciais inválidas");

        return new ResponseEntity<>(
                new ErrorResponse("Credenciais inválidas", HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now(), fieldErrors),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NoSuchElementException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        fieldErrors.put("resource", "Recurso não encontrado");

        return new ResponseEntity<>(
                new ErrorResponse("Recurso não encontrado", HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), fieldErrors),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                fieldErrors.put(err.getField(), err.getDefaultMessage())
        );

        return new ResponseEntity<>(
                new ErrorResponse("Erro de validação", HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), fieldErrors),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        fieldErrors.put("erro", ex.getMessage() != null ? ex.getMessage() : "Erro inesperado");

        return new ResponseEntity<>(
                new ErrorResponse("Erro interno do servidor", HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now(), fieldErrors),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
