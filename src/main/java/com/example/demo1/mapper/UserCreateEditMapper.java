package com.example.demo1.mapper;

import com.example.demo1.dto.UserCreateEditDto;
import com.example.demo1.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto,User>{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User map(UserCreateEditDto object) {

        User user = new User();
        copy(object,user);

        return user;
    }

    @Override
    public User map(UserCreateEditDto fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(UserCreateEditDto object,User user){
        user.setUsername(object.getUsername());
        user.setPassword(passwordEncoder.encode(object.getPassword()));
        user.setFirstname(object.getFirstname());
        user.setLastname(object.getLastname());
        user.setPassportNo(object.getPassportNo());
        user.setBirthDate(object.getBirthDate());
        user.setRole(object.getRole());
    }

}
