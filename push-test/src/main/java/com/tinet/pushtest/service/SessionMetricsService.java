package com.tinet.pushtest.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tinet.pushtest.mapper.SessionMetricsMapper;
import com.tinet.pushtest.model.SessionMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SessionMetricsService extends ServiceImpl<SessionMetricsMapper, SessionMetrics> {
    
    @Autowired
    private SessionMetricsMapper sessionMetricsMapper;

    public SessionMetrics getOrCreate(String qno, String cno, String app) {
        SessionMetrics metrics = sessionMetricsMapper.selectByKeys(qno, cno, app);
        if (metrics == null) {
            metrics = new SessionMetrics();
            metrics.setQno(qno);
            metrics.setCno(cno);
            metrics.setApp(app);
            metrics.setRealtime_session_count(0);
            metrics.setRealtime_session_queued_count(0);
            metrics.setRealtime_session_assigned_count(0);
            metrics.setRealtime_session_locked_count(0);
            metrics.setRealtime_session_robot_count(0);
            this.save(metrics);
        }
        return metrics;
    }
} 