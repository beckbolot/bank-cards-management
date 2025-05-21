package com.example.demo1.exception;

public class CardNotFoundException extends RuntimeException{

    public CardNotFoundException(String message) {
        super(message);
    }
}
