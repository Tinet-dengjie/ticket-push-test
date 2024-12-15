package com.tinet.pushtest.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class MetricsService {
    private final AtomicLong insertCounter = new AtomicLong(0);
    private final Counter insertTotal;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    
    // 使用双端队列存储最近30分钟的数据
    private final ConcurrentLinkedDeque<MetricRecord> metricHistory = new ConcurrentLinkedDeque<>();
    private static final int MAX_HISTORY_SIZE = 30; // 保存30分钟的数据

    @Data
    public static class MetricRecord {
        private final LocalDateTime timestamp;
        private final long count;
        private final long totalCount;
    }

    public MetricsService(MeterRegistry registry) {
        this.insertTotal = Counter.builder("db.insert.total")
                .description("总插入记录数")
                .register(registry);
                
        // 每分钟计算TPM并保存历史记录
        scheduler.scheduleAtFixedRate(() -> {
            long count = insertCounter.getAndSet(0);
            long total = (long) insertTotal.count();
            
            // 创建新的记录
            MetricRecord record = new MetricRecord(LocalDateTime.now(), count, total);
            metricHistory.addLast(record);
            
            // 如果历史记录超过30条，删除最旧的记录
            while (metricHistory.size() > MAX_HISTORY_SIZE) {
                metricHistory.removeFirst();
            }
            
            log.info("Current TPM: {}", count);
        }, 1, 1, TimeUnit.MINUTES);
    }

    public void incrementInsertCount() {
        insertCounter.incrementAndGet();
        insertTotal.increment();
    }

    public List<MetricRecord> getMetricHistory() {
        return new ArrayList<>(metricHistory);
    }

    public double getTotalInserts() {
        return insertTotal.count();
    }
} 