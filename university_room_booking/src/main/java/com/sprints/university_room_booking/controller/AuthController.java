package com.sprints.university_room_booking.controller;

import com.sprints.university_room_booking.dto.AuthResponseDto;
import com.sprints.university_room_booking.dto.LoginRequestDto;
import com.sprints.university_room_booking.dto.RegisterUserDto;
import com.sprints.university_room_booking.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        AuthResponseDto response = authService.login(loginRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/registerStudent")
    public ResponseEntity<AuthResponseDto> registerStudent(@Valid @RequestBody RegisterUserDto registerRequest) {
        AuthResponseDto response = authService.registerUser(registerRequest, "ROLE_STUDENT");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<AuthResponseDto> registerAdmin(@Valid @RequestBody RegisterUserDto registerRequest) {
        AuthResponseDto response = authService.registerUser(registerRequest, "ROLE_ADMIN");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/registerProfessor")
    public ResponseEntity<AuthResponseDto> registerProfessor(@Valid @RequestBody RegisterUserDto registerRequest) {
        AuthResponseDto response = authService.registerUser(registerRequest, "ROLE_PROFESSOR");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}