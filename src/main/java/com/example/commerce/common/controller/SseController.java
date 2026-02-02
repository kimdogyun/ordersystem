package com.example.commerce.common.controller;

import com.example.commerce.common.repository.SseEmitterRegsitry;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@RequestMapping("/sse")
public class SseController {
    private final SseEmitterRegsitry sseEmitterRegsitry;
    @Autowired
    public SseController(SseEmitterRegsitry sseEmitterRegsitry) {
        this.sseEmitterRegsitry = sseEmitterRegsitry;
    }

    @GetMapping("/connect")
    public SseEmitter connect() throws IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        SseEmitter sseEmitter = new SseEmitter(60*60*1000L); // 1시간 유효시간
        sseEmitterRegsitry.addSseEmitter(email, sseEmitter);

        sseEmitter.send(SseEmitter.event().name("connect").data("연결완료."));
        return sseEmitter;
    }

}
