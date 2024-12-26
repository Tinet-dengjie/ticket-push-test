//package com.tinet.pushtest.service;
//
//import com.tinet.pushtest.enums.ChatEventType;
//import com.tinet.pushtest.model.ChatEvent;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Service;
//
//@Slf4j
////@Service
//public class ChatEventHandler {
//
//    @Autowired
//    private ChatMetricsProcessor metricsProcessor;
//
//    @EventListener
//    public void handleChatEvent(ChatEvent event) {
//        log.info("Handling chat event: {}", event);
//
//        try {
//            switch (event.getEventType()) {
//                case SESSION_STARTED ->
//                    metricsProcessor.processSessionStarted(
//                        event.getEnterpriseId(),
//                        event.getAppId()
//                    );
//
//                case SESSION_QUEUED ->
//                    metricsProcessor.processSessionQueued(
//                        event.getEnterpriseId(),
//                        event.getQno(),
//                        event.getCno(),
//                        event.getAppId()
//                    );
//
//                case QUEUE_ABANDONED ->
//                    metricsProcessor.processQueueAbandoned(
//                        event.getEnterpriseId(),
//                        event.getQno(),
//                        event.getCno(),
//                        event.getAppId(),
//                        event.getDuration()
//                    );
//
//                case SESSION_ASSIGNED ->
//                    metricsProcessor.processSessionAssigned(
//                        event.getEnterpriseId(),
//                        event.getQno(),
//                        event.getCno(),
//                        event.getAppId(),
//                        event.getDuration()
//                    );
//
//                case SESSION_LOCKED ->
//                    metricsProcessor.processSessionLocked(
//                        event.getEnterpriseId(),
//                        event.getQno(),
//                        event.getCno(),
//                        event.getAppId()
//                    );
//
//                case ROBOT_MANAGED ->
//                    metricsProcessor.processSessionRobotManaged(
//                        event.getEnterpriseId(),
//                        event.getQno(),
//                        event.getCno(),
//                        event.getAppId()
//                    );
//
//                case SESSION_COMPLETED ->
//                    metricsProcessor.processSessionCompleted(
//                        event.getEnterpriseId(),
//                        event.getQno(),
//                        event.getCno(),
//                        event.getAppId(),
//                        event.getDuration()
//                    );
//            }
//        } catch (Exception e) {
//            log.error("Error processing chat event: {}", event, e);
//        }
//    }
//}