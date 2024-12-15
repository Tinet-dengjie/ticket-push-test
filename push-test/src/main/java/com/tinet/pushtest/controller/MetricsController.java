package com.tinet.pushtest.controller;

import com.tinet.pushtest.service.MetricsService;
import com.tinet.pushtest.service.MetricsService.MetricRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/metrics")
public class MetricsController {
    private final MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping("/history")
    public List<MetricRecord> getMetricHistory() {
        return metricsService.getMetricHistory();
    }

    @GetMapping("/total")
    public double getTotalInserts() {
        return metricsService.getTotalInserts();
    }
} 