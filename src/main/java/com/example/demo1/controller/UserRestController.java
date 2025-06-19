package com.example.demo1.controller;

import com.example.demo1.dto.AuthRequestDto;
import com.example.demo1.dto.UserCreateEditDto;
import com.example.demo1.dto.UserReadDto;
import com.example.demo1.mapper.UserReadMapper;
import com.example.demo1.service.UserService;
import com.example.demo1.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User Controller",description = "API for managing users")
public class UserRestController {

    private final UserService userService;
    private final UserReadMapper userReadMapper;
    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    @Operation(summary = "Getting all users, only User with role ADMIN can see all users")
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserReadDto>> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @Operation(summary = "Getting user by id,only ADMIN can view user details")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserReadDto> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(userService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @Operation(summary = "Creating user ")
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserReadDto> createUser(@RequestBody @Validated UserCreateEditDto dto) {
        return ResponseEntity.ok(userService.createUser(dto));
    }

    @Operation(summary = "Updating user information")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserReadDto> updateUser(@PathVariable("id") Integer id, @RequestBody @Validated UserCreateEditDto dto) {
        return ResponseEntity.ok(userService.update(id, dto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @Operation(summary = "Deleting user by id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return userService.deleteUser(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Authenticating new user and getting Jwt token to make request")
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
