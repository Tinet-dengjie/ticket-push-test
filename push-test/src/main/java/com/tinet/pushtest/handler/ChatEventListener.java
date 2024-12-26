package com.tinet.pushtest.handler;

import com.tinet.pushtest.model.ChatEvent;
import org.springframework.context.event.EventListener;

public interface ChatEventListener {
    boolean supports(ChatEvent event);
    void handleEvent(ChatEvent event);
} 