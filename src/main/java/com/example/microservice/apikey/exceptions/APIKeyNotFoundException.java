package com.example.microservice.apikey.exceptions;

public class APIKeyNotFoundException extends RuntimeException {
    public APIKeyNotFoundException(String message) {
        super(message);
    }
}
