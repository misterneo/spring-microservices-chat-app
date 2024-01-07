package com.example.chatservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TicketService {
    private static final long TICKET_EXPIRATION_TIME = 60L;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void storeTicket(String ticket) {
        redisTemplate.opsForValue().set(ticket, "valid", TICKET_EXPIRATION_TIME, TimeUnit.SECONDS);
    }

    public boolean validateTicket(String ticket) {
        return redisTemplate.opsForValue().get(ticket) != null;
    }
}
