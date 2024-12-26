package com.tinet.pushtest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
public class ChatEventGeneratorService {
    
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    
//    @Autowired
//    private ChatMetricsProcessor metricsProcessor;
    
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private final Random random = new Random();
    
    public void startGenerating(Integer enterpriseId) {
        if (isRunning.compareAndSet(false, true)) {
            threadPoolTaskExecutor.submit(() -> generateEvents(enterpriseId));
        }
    }
    
    public void stopGenerating() {
        isRunning.set(false);
    }
    
    private void generateEvents(Integer enterpriseId) {
        while (isRunning.get()) {
            try {
                // 生成一个新的会话开始事件
                String qno = "QNO" + (random.nextInt(100) + 1);
//                String cno = "CNO" + (random.nextInt(100) + 1);
                String appId = String.valueOf(random.nextInt(100) + 1);
                
                // 处理会话开始事件
//                metricsProcessor.processSessionStarted(enterpriseId, appId);
                
                // 控制生成速率
//                Thread.sleep(random.nextInt(1000) + 500);  // 500-1500ms间隔
            } catch (Exception e) {
                log.error("Error generating chat events", e);
            }
        }
    }
} 