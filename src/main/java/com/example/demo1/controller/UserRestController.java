package com.example.demo1.controller;

import com.example.demo1.dto.AuthRequestDto;
import com.example.demo1.dto.UserCreateEditDto;
import com.example.demo1.dto.UserReadDto;
import com.example.demo1.mapper.UserReadMapper;
import com.example.demo1.service.UserService;
import com.example.demo1.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final UserReadMapper userReadMapper;
    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserReadDto>> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserReadDto> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(userService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserReadDto> createUser(@RequestBody @Validated UserCreateEditDto dto) {
        return ResponseEntity.ok(userService.createUser(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserReadDto> updateUser(@PathVariable("id") Integer id, @RequestBody @Validated UserCreateEditDto dto) {
        return ResponseEntity.ok(userService.update(id, dto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return userService.deleteUser(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/auth")
    public String authenticateAndGetToken(@RequestBody AuthRequestDto authRequestDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword()));
        if (authentication.isAuthenticated()){
            return jwtUtil.generateToken(authRequestDto.getUsername());
        }else {
            throw new AuthenticationCredentialsNotFoundException(authentication.getPrincipal() + " not found");
        }
    }


}
