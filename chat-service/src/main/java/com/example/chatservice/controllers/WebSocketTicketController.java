package com.example.chatservice.controllers;

import com.example.chatservice.models.UserInfo;
import com.example.chatservice.services.TicketService;
import com.example.chatservice.services.UserCacheService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/ws-ticket")
public class WebSocketTicketController {
    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserCacheService userCacheService;

    @GetMapping
    public ResponseEntity<String> getTicket(@RequestHeader("X-User-Info") String userInfoString) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> userInfoMap = objectMapper.readValue(userInfoString, new TypeReference<Map<String, String>>(){});

        String token = userInfoMap.get("token");
        UserInfo userInfo = userCacheService.getUserInfo(token);
        if(userInfo == null) {
            userCacheService.cacheUserInfo(token, userInfoMap);
        }

        String ticket = UUID.randomUUID().toString();
        ticketService.storeTicket(ticket);
        return ResponseEntity.ok(ticket);
    }
}
