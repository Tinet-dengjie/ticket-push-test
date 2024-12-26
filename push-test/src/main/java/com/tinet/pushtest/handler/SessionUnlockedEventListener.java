package com.tinet.pushtest.handler;

import com.tinet.pushtest.enums.ChatEventType;
import com.tinet.pushtest.model.ChatEvent;
import com.tinet.pushtest.service.ChatMetricsProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SessionUnlockedEventListener implements ChatEventListener {
    
    @Autowired
    private ChatMetricsProcessor metricsProcessor;
    
    @Override
    public boolean supports(ChatEvent event) {
        return ChatEventType.SESSION_UNLOCKED == event.getEventType();
    }
    
    @EventListener
    @Override
    public void handleEvent(ChatEvent event) {
        if(!supports(event)){
            return;
        }
        log.info("Handling session unlocked event: {}", event);
        metricsProcessor.processSessionUnlocked(
            event.getEnterpriseId(),
            event.getQno(),
            event.getCno(),
            event.getAppId()
        );
    }
} 