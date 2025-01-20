package com.example.microservice.apikey.exceptions;

public class InvalidAPIKeyException extends RuntimeException {
    public InvalidAPIKeyException(String message) {
        super(message);
    }
}