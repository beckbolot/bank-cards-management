package com.example.demo1.dto;

import lombok.Value;
import org.springframework.validation.annotation.Validated;

@Value
public class AuthRequestDto {

    String username;
    String password;
}
