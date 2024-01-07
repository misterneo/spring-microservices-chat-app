package com.example.chatservice.services;


import com.example.chatservice.models.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserCacheService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private ValueOperations<String, String> valueOperations;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    private void init() {
        valueOperations = redisTemplate.opsForValue();
    }

    public void cacheUserInfo(String token, Map<String, String> userInfo) throws Exception {
        String expiresInStr = userInfo.get("expiresIn");
        LocalDateTime expiresIn = LocalDateTime.parse(expiresInStr, DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy"));
        Duration duration = Duration.between(LocalDateTime.now(), expiresIn);

        UserInfo newUserInfo = new UserInfo(
            userInfo.get("id"),
            userInfo.get("fullName"),
            userInfo.get("email")
        );

        String userInfoJson = objectMapper.writeValueAsString(newUserInfo);

        valueOperations.set(token, userInfoJson, duration.getSeconds(), TimeUnit.SECONDS);
    }

    public UserInfo getUserInfo(String token) throws Exception{
        String userInfoJson = valueOperations.get(token);
        if(userInfoJson == null) {
            return null;
        }
        return objectMapper.readValue(userInfoJson, UserInfo.class);
    }

    public void removeUserInfo(String token) {
        redisTemplate.delete(token);
    }
}
