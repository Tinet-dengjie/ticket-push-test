package com.tinet.pushtest.controller;

import com.tinet.pushtest.service.QueryMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/query-metrics")
public class QueryMetricsController {
    
    @Autowired
    private QueryMetricsService queryMetricsService;

    @GetMapping
    public QueryMetricsService.QueryMetrics getMetrics() {
        return queryMetricsService.getMetrics();
    }
} 