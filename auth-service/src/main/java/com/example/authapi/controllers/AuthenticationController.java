package com.example.authapi.controllers;

import com.example.authapi.dtos.LoginUserDto;
import com.example.authapi.dtos.RegisterUserDto;
import com.example.authapi.entities.User;
import com.example.authapi.responses.LoginResponse;
import com.example.authapi.services.AuthenticationService;
import com.example.authapi.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterUserDto registerUserDto) {
        User registerUser = authenticationService.signup(registerUserDto);

        String token = jwtService.generateToken(registerUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String token = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/healthy")
    public ResponseEntity<String> healthy() {
        return ResponseEntity.ok("Healthy");
    }
}
