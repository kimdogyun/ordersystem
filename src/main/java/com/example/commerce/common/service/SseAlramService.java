package com.example.commerce.common.service;

import com.example.commerce.common.dtos.SseMessageDto;
import com.example.commerce.common.repository.SseEmitterRegsitry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Component
public class SseAlramService {
    private final SseEmitterRegsitry sseEmitterRegsitry;
    private final ObjectMapper objectMapper;

    @Autowired
    public SseAlramService(SseEmitterRegsitry sseEmitterRegsitry, ObjectMapper objectMapper) {
        this.sseEmitterRegsitry = sseEmitterRegsitry;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(String receiver, String sender, String message) {
        SseEmitter sseEmitter = sseEmitterRegsitry.getEmitter(receiver);
        SseMessageDto dto = SseMessageDto.builder()
                .receiver(receiver)
                .sender(sender)
                .message(message)
                .build();

        try {
            String data = objectMapper.writeValueAsString(dto);
            sseEmitter.send(SseEmitter.event().name("ordered").data(data));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
