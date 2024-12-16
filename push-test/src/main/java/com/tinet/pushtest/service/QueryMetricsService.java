package com.tinet.pushtest.service;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class QueryMetricsService {
    private static final AtomicLong queryCount = new AtomicLong(0);
    private static volatile long startTime = System.currentTimeMillis();
    private static final ConcurrentLinkedQueue<Double> latencies = new ConcurrentLinkedQueue<>();
    private static final int MAX_LATENCY_SAMPLES = 120;

    public void recordLatency(double latencySeconds) {
        queryCount.incrementAndGet();
        latencies.offer(latencySeconds);
        while (latencies.size() > MAX_LATENCY_SAMPLES) {
            latencies.poll();
        }
    }

    public static void reset() {
        queryCount.set(0);
        startTime = System.currentTimeMillis();
        latencies.clear();
    }

    public QueryMetrics getMetrics() {
        QueryMetrics metrics = new QueryMetrics();
        long currentTime = System.currentTimeMillis();
        long timeElapsedSeconds = (currentTime - startTime) / 1000;
        
        metrics.setTps(timeElapsedSeconds > 0 ? 
                (double) queryCount.get() / timeElapsedSeconds : 0);

        List<Double> sortedLatencies = new ArrayList<>(latencies);
        Collections.sort(sortedLatencies);
        
        if (!sortedLatencies.isEmpty()) {
            metrics.setLatencyP50(getPercentile(sortedLatencies, 0.5));
            metrics.setLatencyP95(getPercentile(sortedLatencies, 0.95));
            metrics.setLatencyP99(getPercentile(sortedLatencies, 0.99));
        }

        return metrics;
    }

    private double getPercentile(List<Double> sortedList, double percentile) {
        int index = (int) Math.ceil(percentile * sortedList.size()) - 1;
        return sortedList.get(Math.max(0, index));
    }

    @Data
    public static class QueryMetrics {
        private double tps;
        private double latencyP50;
        private double latencyP95;
        private double latencyP99;
    }
} 