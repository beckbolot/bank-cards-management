package com.example.demo1.service;

import com.example.demo1.dto.UserCreateEditDto;
import com.example.demo1.dto.UserReadDto;
import com.example.demo1.mapper.UserCreateEditMapper;
import com.example.demo1.mapper.UserReadMapper;
import com.example.demo1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;


    public List<UserReadDto> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userReadMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<UserReadDto> findById(Integer id) {
        return userRepository.findById(id).map(userReadMapper::map);
    }
    @Transactional
    public UserReadDto createUser(UserCreateEditDto dto) {
        return Optional.of(dto)
                .map(userCreateEditMapper::map)
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();
    }
    @Transactional
    public Optional<UserReadDto> update(Integer id, UserCreateEditDto dto) {
        return userRepository.findById(id)
                .map(entity -> userCreateEditMapper.map(dto, entity))
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }

    @Transactional
    public boolean deleteUser(Integer id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);

    }


}
