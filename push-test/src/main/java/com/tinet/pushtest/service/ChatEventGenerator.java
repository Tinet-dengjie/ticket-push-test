package com.tinet.pushtest.service;

import com.alibaba.fastjson2.JSONObject;
import com.tinet.pushtest.enums.ChatEventType;
import com.tinet.pushtest.model.ChatEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
public class ChatEventGenerator {
    
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private DefaultMQProducer defaultMQProducer;
    
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private final Random random = new Random();
    
    public void start(Integer enterpriseId) {
//        if (isRunning.compareAndSet(false, true)) {
            threadPoolTaskExecutor.submit(() -> generateEvents(enterpriseId));
//        }
    }
    
    public void stop() {
        isRunning.set(false);
    }
    
    private void generateEvents(Integer enterpriseId) {
//        while (isRunning.get()) {
            try {
                // 随机生成事件类型
                ChatEventType eventType = randomEventType();
                
                // 生成随机事件
                ChatEvent event = ChatEvent.builder()
//                        .enterpriseId(8000000+(random.nextInt(998) + 1))
                        .enterpriseId(8001678)
                        .qno("QNO" + (random.nextInt(1000) + 1))
                        .cno("CNO" + (random.nextInt(1000) + 1))
                        .appId("APP"+(random.nextInt(1000) + 1))
                        .eventType(eventType)
                        .timestamp(System.currentTimeMillis())
                        .duration(random.nextInt(10000)) // 随机时长 0-10s
                        .build();
                
//                publishEvent(event);
                sendMQ(event);
            } catch (Exception e) {
                log.error("Error generating chat events", e);
            }
//        }
    }

    private void sendMQ(ChatEvent event) {
        try {
            String jsonString = JSONObject.toJSONString(event);
            Message msg = new Message("flinkTopic",
                    "SESSION_EVENT",
                    jsonString.getBytes(StandardCharsets.UTF_8));
            SendResult sendResult = defaultMQProducer.send(msg);
            log.info("Send mq message success. MsgId: {} {}", sendResult.getMsgId(),jsonString);
        } catch (Exception e) {
            //消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理。
            log.error("Send mq message failed.", e);
        }
    }

    private ChatEventType randomEventType() {
        // 根据不同概率返回不同事件类型
        double rand = random.nextDouble();
        if (rand < 0.3) return ChatEventType.SESSION_STARTED;
        if (rand < 0.6) return ChatEventType.SESSION_QUEUED;
        if (rand < 0.65) return ChatEventType.QUEUE_ABANDONED;
        if (rand < 0.94) return ChatEventType.SESSION_ASSIGNED;
        if (rand < 0.95) return ChatEventType.SESSION_LOCKED;
        if (rand < 0.96) return ChatEventType.SESSION_UNLOCKED;
        if (rand < 0.97) return ChatEventType.ROBOT_MANAGED;
        if (rand < 0.98) return ChatEventType.ROBOT_CANCELLED;
        return ChatEventType.SESSION_COMPLETED;
    }
    
    private void publishEvent(ChatEvent event) {
        eventPublisher.publishEvent(event);
        log.info("Generated event: {}", event);
    }
} 