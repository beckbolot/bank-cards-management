package com.example.demo1.mapper;

import com.example.demo1.dto.UserReadDto;
import com.example.demo1.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto>{
    @Override
    public UserReadDto map(User object) {
        return new UserReadDto(
                object.getId(),
                object.getUsername(),
                object.getFirstname(),
                object.getLastname(),
                object.getPassportNo(),
                object.getBirthDate(),
                object.getRole()
        );
    }
}
