package com.example.FinanceApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // Automatically returns 400
public class UnsupportedChatQueryException extends RuntimeException {
    public UnsupportedChatQueryException() {
        super("I can only help with finance-related questions.");
    }

    public UnsupportedChatQueryException(String message) {
        super(message);
    }
}