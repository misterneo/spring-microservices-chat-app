package com.example.chatservice.services;

import com.example.chatservice.models.Message;
import com.example.chatservice.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getMessages(){
        return messageRepository.findAll();
    }

    public Message createMessage(Message message){
        return messageRepository.save(message);
    }
}
