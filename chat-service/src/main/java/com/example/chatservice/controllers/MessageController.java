package com.example.chatservice.controllers;

import com.example.chatservice.dtos.CreateMessageDto;
import com.example.chatservice.models.Message;
import com.example.chatservice.models.UserInfo;
import com.example.chatservice.services.MessageService;
import com.example.chatservice.services.UserCacheService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequestMapping("/messages")
@Controller
public class MessageController {

    private final MessageService messageService;
    private final UserCacheService userCacheService;

    public MessageController(MessageService messageService, UserCacheService userCacheService) {
        this.messageService = messageService;
        this.userCacheService = userCacheService;
    }

    @RequestMapping("")
    public ResponseEntity<List<Message>> getMessages() {
        return ResponseEntity.ok(messageService.getMessages());
    }

    @RequestMapping("/create")
    @MessageMapping("/create")
    @SendTo("/topic/newMessage")
    public ResponseEntity<Message> createMessage(@RequestBody CreateMessageDto input, @Header(name = "token") String token) throws Exception{
        UserInfo user = userCacheService.getUserInfo(token);
        String userId = user.getId();

        Message message = new Message();
        message.setUserId(userId);
        message.setFullName(user.getFullName());
        message.setContent(input.getContent());
        message.setCreatedAt(new Date());

        return ResponseEntity.ok(messageService.createMessage(message));
    }

    @GetMapping("/healthy")
    public ResponseEntity<String> healthy() {
        return ResponseEntity.ok("Healthy");
    }
}
