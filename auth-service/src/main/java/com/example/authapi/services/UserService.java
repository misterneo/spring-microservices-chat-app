package com.example.authapi.services;

import com.example.authapi.entities.User;
import com.example.authapi.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private JwtService jwtService;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public String createUserInfo(User user, String token)  throws Exception {
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("id", user.getId().toString());
        userInfo.put("email", user.getEmail());
        userInfo.put("fullName", user.getFullName());

        final String jwt = token.substring(7);
        userInfo.put("token",  jwt);
        userInfo.put("expiresIn",  jwtService.extractExpiration(jwt).toString());

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(userInfo);
    }
}
