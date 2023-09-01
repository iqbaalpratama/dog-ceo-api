package com.iqbaal.dogapi.controller;

import java.time.LocalTime;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.iqbaal.dogapi.exception.BreedNotFoundException;
import com.iqbaal.dogapi.exception.ErrorResponse;
import com.iqbaal.dogapi.exception.ImageNotFoundException;
import com.iqbaal.dogapi.exception.SubBreedNotFoundException;

@RestControllerAdvice
public class ErrorController {
    
    @ExceptionHandler(BreedNotFoundException.class)
    public ResponseEntity<ErrorResponse> breedNotFoundException(BreedNotFoundException exception) {
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exception.getMessage());
        error.setTimeStamp(LocalTime.now());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SubBreedNotFoundException.class)
    public ResponseEntity<ErrorResponse> subBreedNotFoundException(SubBreedNotFoundException exception) {
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exception.getMessage());
        error.setTimeStamp(LocalTime.now());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ErrorResponse> imageNotFoundException(ImageNotFoundException exception) {
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exception.getMessage());
        error.setTimeStamp(LocalTime.now());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> constraintViolationException(ConstraintViolationException exception) {
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exception.getMessage());
        error.setTimeStamp(LocalTime.now());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
