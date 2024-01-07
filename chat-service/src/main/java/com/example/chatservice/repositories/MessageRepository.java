package com.example.chatservice.repositories;

import com.example.chatservice.models.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> { }
