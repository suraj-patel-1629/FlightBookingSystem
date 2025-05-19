package com.example.adminservice.globalexception;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus; // Correct import
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.adminservice.customexception.FlightNotFoundException;

import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); 
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<String> handleNoSuchElement(NoSuchElementException ex) {
    return new ResponseEntity<>("Flight not found", HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(FlightNotFoundException.class)
public ResponseEntity<Map<String, String>> handleFlightNotFound(FlightNotFoundException ex) {
    Map<String, String> error = new HashMap<>();
    error.put("error", ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
}
}
