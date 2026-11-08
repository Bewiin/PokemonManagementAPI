package org.example.pokemonmanagementapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Gestion de l'erreur avec le code 201
    @ExceptionHandler(DresseurNotFoundException.class)
    public ResponseEntity<String> handleDresseurNotFound(DresseurNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ex.getMessage());
    }
}
