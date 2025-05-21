package com.example.demo1.dto;

import com.example.demo1.entity.Role;
import lombok.Value;

import java.time.LocalDate;

@Value
public class UserReadDto {

    Integer id;
    String username;
    String firstname;
    String lastname;
    String passport_no;
    LocalDate birthDate;
    Role role;


}
