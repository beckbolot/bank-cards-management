package com.example.demo1.dto;

import com.example.demo1.entity.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.time.LocalDate;

@Value
public class UserCreateEditDto {
    @NotNull
    @Size(max = 100)
    String username;

    @NotNull
    String password;

    @NotNull
    @Size(max = 50)
    String firstname;

    @NotNull
    @Size(max = 50)
    String lastname;

    @NotNull
    @Size(max = 60)
    String passportNo;

    LocalDate birthDate;

    @NotNull
    Role role;


}
