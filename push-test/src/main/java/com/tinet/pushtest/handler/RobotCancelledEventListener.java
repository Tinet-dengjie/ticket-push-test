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
public class RobotCancelledEventListener implements ChatEventListener {
    
    @Autowired
    private ChatMetricsProcessor metricsProcessor;
    
    @Override
    public boolean supports(ChatEvent event) {
        return ChatEventType.ROBOT_CANCELLED == event.getEventType();
    }
    
    @EventListener
    @Override
    public void handleEvent(ChatEvent event) {
        if(!supports(event)){
            return;
        }
        log.info("Handling robot cancelled event: {}", event);
        metricsProcessor.processRobotCancelled(
            event.getEnterpriseId(),
            event.getQno(),
            event.getCno(),
            event.getAppId()
        );
    }
} 