package com.example.demo1.exception;

public class TransferRequestException extends RuntimeException {

    public TransferRequestException(String message) {
        super(message);
    }
}
