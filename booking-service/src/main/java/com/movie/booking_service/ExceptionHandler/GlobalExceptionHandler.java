package com.movie.booking_service.ExceptionHandler;

import com.movie.booking_service.DTO.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFound(UserNotFoundException ex) {
      ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage(), 404, LocalDateTime.now());
      return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDTO> alreadyExists(UserAlreadyExistException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage(), 404, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> invalidCredentials(InvalidCredentialsException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage(), 404, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateBookingException.class)
    public ResponseEntity<ErrorResponseDTO> DuplicateBookingFound(DuplicateBookingException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage(), 404, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SeatAlreadyBookedException.class)
    public ResponseEntity<ErrorResponseDTO> SeatBookedAlready(SeatAlreadyBookedException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage(), 404, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceUnavailableException .class)
    public ResponseEntity<ErrorResponseDTO> ServiceNotAvailable(ServiceUnavailableException  ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage(), 404, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
