package com.tinet.pushtest.controller;

import com.tinet.pushtest.service.ChatEventGenerator;
import com.tinet.pushtest.service.ChatEventGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat-test")
public class ChatEventTestController {
    
    @Autowired
    private ChatEventGenerator chatEventGenerator;
    
    @PostMapping("/start")
    public String startTest(@RequestParam(defaultValue = "8001678") Integer enterpriseId) {
        chatEventGenerator.start(enterpriseId);
        return "Started generating chat events";
    }
    
    @PostMapping("/stop")
    public String stopTest() {
        chatEventGenerator.stop();
        return "Stopped generating chat events";
    }
} 