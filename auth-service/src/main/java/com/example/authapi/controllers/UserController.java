package com.example.authapi.controllers;

import com.example.authapi.entities.User;
import com.example.authapi.repositories.UserRepository;
import com.example.authapi.services.UserService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User authenticatedUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(authenticatedUser);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(@RequestHeader("Authorization") String token) throws Exception {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            User authenticatedUser = (User) authentication.getPrincipal();

            String userInfoJson = userService.createUserInfo(authenticatedUser, token);

            HttpHeaders headers = new HttpHeaders();
            headers.add("X-User-Info", userInfoJson);

            return ResponseEntity.ok()
                    .headers(headers)
                    .build();
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
