package com.movie.movie_service.ExceptionHandler;

import com.movie.movie_service.DTO.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TheatreNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFound(TheatreNotFoundException ex) {
      ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage(), 404, LocalDateTime.now());
      return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TheatreAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> alreadyExists(TheatreAlreadyExistsException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage(), 400, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> invalidCredentials(InvalidCredentialsException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage(), 422, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }
}
