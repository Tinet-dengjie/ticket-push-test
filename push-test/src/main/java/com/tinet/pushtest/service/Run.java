package com.tinet.pushtest.service;

import com.tinet.pushtest.model.ReceptionRecords;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

@Component
@Slf4j
public class Run {
    @Autowired
    private ReceptionRecordsServiceImpl receptionRecordsService;

    @Autowired
    private MetricsService metricsService;

    // 用于存储已生成的session_unique_id
    private final Random random = new Random();

//    @PostConstruct
    public void doWrite() {
        AtomicLong atomicLong = new AtomicLong(0);
        // 使用虚拟线程执行器
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 100; i++) {
                executor.submit(() -> {
                    while (atomicLong.getAndIncrement() < 1000_0000) {
                        try {
                            ReceptionRecords entity = generateRandomReceptionRecord();
                            receptionRecordsService.save(entity);
                            metricsService.incrementInsertCount();
                        } catch (Exception e) {
                            log.error("插入数据失败", e);
                        }
                    }
                });
            }
            executor.shutdown();
        }
    }

    private ReceptionRecords generateRandomReceptionRecord() {
        ReceptionRecords entity = new ReceptionRecords();
        entity.setQno(randomChoice(this.getArray(1000, "QNO")));
        entity.setCno(randomChoice(this.getArray(1000, "CNO")));
        entity.setApp(randomChoice(this.getArray(1000, "APP")));
        entity.setQueue_abort(generateSessionUniqueId(false) > 0 ? 1 : 0);
        if (entity.getQueue_abort() == 0) {
            entity.setQueue_duration(generateSessionUniqueId(false));
        }

        if (entity.getQueue_abort() == 0 && entity.getQueue_duration() == 0) {
            int sessionDuration = generateSessionUniqueId(true);
            entity.setSession_duration(sessionDuration);
            entity.setSession_complete(sessionDuration > 0 ? 1 : 0);
        }

        return entity;
    }

    private String[] getArray(int i, String qno) {
        String[] strings = new String[i];
        for (int j = 1; j <= i; j++) {
            strings[j - 1] = qno + j;
        }
        return strings;
    }

    private String randomChoice(String[] options) {
        int index = random.nextInt(options.length);
        return options[index];
    }

    private String randomAlphanumeric(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char) (random.nextInt(24) + 'A');
            sb.append(c);
        }
        return sb.toString();
    }

    private int generateSessionUniqueId(boolean forceNew) {
        // 有10%的概率生成一个新的session_unique_id
        if (random.nextDouble() < 0.8 || forceNew) {
            return random.nextInt(100, 10000);
        } else {
            // 否则，从已有的session_unique_id中随机选择一个
            return 0;
        }
    }
}