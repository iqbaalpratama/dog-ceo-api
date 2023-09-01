package com.iqbaal.dogapi.exception;

public class BreedNotFoundException extends RuntimeException{
    public BreedNotFoundException(String message) {
        super(message);
    }
}
