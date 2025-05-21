package com.example.demo1.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(basePackages = "com.example.demo1.controller")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
}
