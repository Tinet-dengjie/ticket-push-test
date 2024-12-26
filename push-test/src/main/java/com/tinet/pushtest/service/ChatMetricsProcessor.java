package com.tinet.pushtest.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tinet.pushtest.model.ChatMetricAgent;
import com.tinet.pushtest.model.ChatMetricApp;
import com.tinet.pushtest.model.ChatMetricEnterprise;
import com.tinet.pushtest.model.ChatMetricQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ChatMetricsProcessor {
    
    @Autowired
    private ChatMetricEnterpriseService enterpriseService;
    
    @Autowired
    private ChatMetricQueueService queueService;
    
    @Autowired
    private ChatMetricAppService appService;
    
    @Autowired
    private ChatMetricAgentService agentService;
    
    @Transactional
    public void processSessionStarted(Integer enterpriseId, String appId) {
        // 1. 更新企业级指标
        var enterpriseMetric = new ChatMetricEnterprise();
        enterpriseMetric.setEnterprise_id(enterpriseId);
        enterpriseMetric.setReal_time_sessions(1);
        enterpriseService.getBaseMapper().insertOrUpdate(enterpriseMetric);
        
        // 2. 更新应用级指标
        var appMetric = new ChatMetricApp();
        appMetric.setEnterprise_id(enterpriseId);
        appMetric.setApp_id(appId);
        appMetric.setReal_time_sessions(1);
        appService.getBaseMapper().insertOrUpdate(appMetric);
        
        log.info("Processed session start event: enterprise={}, appId={}", 
                enterpriseId, appId);
    }
    
    @Transactional
    public void processSessionQueued(Integer enterpriseId, String qno, String cno, String appId) {
        // 1. 更新企业级指标
        var enterpriseMetric = new ChatMetricEnterprise();
        enterpriseMetric.setEnterprise_id(enterpriseId);
        enterpriseMetric.setReal_time_queue_count(1);
        enterpriseService.getBaseMapper().insertOrUpdate(enterpriseMetric);
        
        // 2. 更新队列级指标
        var queueMetric = new ChatMetricQueue();
        queueMetric.setEnterprise_id(enterpriseId);
        queueMetric.setQno(qno);
        queueMetric.setReal_time_queue_count(1);
        queueService.getBaseMapper().insertOrUpdate(queueMetric);

        log.info("Processed session queue event: enterprise={}, qno={}, cno={}, appId={}", 
                enterpriseId, qno, cno, appId);
    }
    
    @Transactional
    public void processQueueAbandoned(Integer enterpriseId, String qno, String cno, String appId, int queueDuration) {
        // 1. 更新企业级指标
        var enterpriseMetric = new ChatMetricEnterprise();
        enterpriseMetric.setEnterprise_id(enterpriseId);
        enterpriseMetric.setReal_time_queue_count(-1);
        enterpriseMetric.setQueue_abandonment_count(1);
        enterpriseService.getBaseMapper().insertOrUpdate(enterpriseMetric);
        
        // 2. 更新队列级指标
        var queueMetric = new ChatMetricQueue();
        queueMetric.setEnterprise_id(enterpriseId);
        queueMetric.setQno(qno);
        queueMetric.setReal_time_queue_count(-1);
        queueMetric.setQueue_abandonment_count(1);
        queueService.getBaseMapper().insertOrUpdate(queueMetric);
        

        log.info("Processed queue abandoned: enterprise={}, qno={}, cno={}, appId={}, duration={}", 
                enterpriseId, qno, cno, appId, queueDuration);
    }
    
    @Transactional
    public void processSessionAssigned(Integer enterpriseId, String qno, String cno, String appId, int queueDuration) {
        // 1. 更新企业级指标
        var enterpriseMetric = new ChatMetricEnterprise();
        enterpriseMetric.setEnterprise_id(enterpriseId);
        enterpriseMetric.setReal_time_queue_count(-1);
        enterpriseMetric.setReal_time_reception_sessions(1);
        enterpriseMetric.setAverage_queue_access_duration(queueDuration);
        enterpriseService.getBaseMapper().insertOrUpdate(enterpriseMetric);
        
        // 2. 更新队列级指标
        var queueMetric = new ChatMetricQueue();
        queueMetric.setEnterprise_id(enterpriseId);
        queueMetric.setQno(qno);
        queueMetric.setReal_time_queue_count(-1);
        queueMetric.setReal_time_reception_sessions(1);
        queueMetric.setAverage_queue_access_duration(queueDuration);
        queueService.getBaseMapper().insertOrUpdate(queueMetric);
        
        // 3. 更新应用级指标
        var appMetric = new ChatMetricApp();
        appMetric.setEnterprise_id(enterpriseId);
        appMetric.setApp_id(appId);
        appMetric.setReal_time_reception_sessions(1);
        appService.getBaseMapper().insertOrUpdate(appMetric);
        
        // 4. 更新座席级指标
        var agentMetric = new ChatMetricAgent();
        agentMetric.setEnterprise_id(enterpriseId);
        agentMetric.setCno(cno);
        agentMetric.setReal_time_reception_sessions(1);
        agentService.getBaseMapper().insertOrUpdate(agentMetric);
        
        log.info("Processed session assigned: enterprise={}, qno={}, cno={}, appId={}, queueDuration={}", 
                enterpriseId, qno, cno, appId, queueDuration);
    }
    
    @Transactional
    public void processSessionLocked(Integer enterpriseId, String qno, String cno, String appId) {
        
        // 4. 更新座席级指标
        var agentMetric = new ChatMetricAgent();
        agentMetric.setEnterprise_id(enterpriseId);
        agentMetric.setCno(cno);
        agentMetric.setReal_time_locked_sessions(1);
        agentMetric.setReal_time_reception_sessions(-1);
        agentService.getBaseMapper().insertOrUpdate(agentMetric);
        
        log.info("Processed session locked: enterprise={}, qno={}, cno={}, appId={}", 
                enterpriseId, qno, cno, appId);
    }
    
    @Transactional
    public void processSessionRobotManaged(Integer enterpriseId, String qno, String cno, String appId) {
        // 4. 更新座席级指标
        var agentMetric = new ChatMetricAgent();
        agentMetric.setEnterprise_id(enterpriseId);
        agentMetric.setCno(cno);
        agentMetric.setReal_time_bot_sessions(1);
        agentService.getBaseMapper().insertOrUpdate(agentMetric);
        
        log.info("Processed session robot managed: enterprise={}, qno={}, cno={}, appId={}", 
                enterpriseId, qno, cno, appId);
    }
    
    @Transactional
    public void processSessionCompleted(Integer enterpriseId, String qno, String cno, String appId, int sessionDuration) {
        // 1. 更新企业级指标
        var enterpriseMetric = new ChatMetricEnterprise();
        enterpriseMetric.setEnterprise_id(enterpriseId);
        enterpriseMetric.setReal_time_sessions(-1);
        enterpriseMetric.setReal_time_reception_sessions(-1);
        enterpriseMetric.setToday_completed_sessions(1);
        enterpriseMetric.setAverage_session_duration(sessionDuration);
        enterpriseService.getBaseMapper().insertOrUpdate(enterpriseMetric);
        
        // 2. 更新队列级指标
        var queueMetric = new ChatMetricQueue();
        queueMetric.setEnterprise_id(enterpriseId);
        queueMetric.setQno(qno);
        queueMetric.setReal_time_sessions(-1);
        queueMetric.setReal_time_reception_sessions(-1);
        queueMetric.setCompleted_sessions(1);
        queueMetric.setAverage_session_duration(sessionDuration);
        queueService.getBaseMapper().insertOrUpdate(queueMetric);
        
        // 3. 更新应用级指标
        var appMetric = new ChatMetricApp();
        appMetric.setEnterprise_id(enterpriseId);
        appMetric.setApp_id(appId);
        appMetric.setReal_time_sessions(-1);
        appMetric.setReal_time_reception_sessions(-1);
        appMetric.setToday_completed_sessions(1);
        appService.getBaseMapper().insertOrUpdate(appMetric);
        
        // 4. 更新座席级指标
        var agentMetric = new ChatMetricAgent();
        agentMetric.setEnterprise_id(enterpriseId);
        agentMetric.setCno(cno);
        agentMetric.setToday_completed_sessions(1);
        agentMetric.setReal_time_reception_sessions(-1);
        agentService.getBaseMapper().insertOrUpdate(agentMetric);
        
        log.info("Processed session completed: enterprise={}, qno={}, cno={}, appId={}, duration={}", 
                enterpriseId, qno, cno, appId, sessionDuration);
    }
    
    @Transactional
    public void processSessionUnlocked(Integer enterpriseId, String qno, String cno, String appId) {
        // 4. 更新座席级指标
        var agentMetric = new ChatMetricAgent();
        agentMetric.setEnterprise_id(enterpriseId);
        agentMetric.setCno(cno);
        agentMetric.setReal_time_locked_sessions(-1);
        agentService.getBaseMapper().insertOrUpdate(agentMetric);
        
        log.info("Processed session unlocked: enterprise={}, qno={}, cno={}, appId={}", 
                enterpriseId, qno, cno, appId);
    }
    
    @Transactional
    public void processRobotCancelled(Integer enterpriseId, String qno, String cno, String appId) {
        // 4. 更新座席级指标
        var agentMetric = new ChatMetricAgent();
        agentMetric.setEnterprise_id(enterpriseId);
        agentMetric.setCno(cno);
        agentMetric.setReal_time_bot_sessions(-1);
        agentService.getBaseMapper().insertOrUpdate(agentMetric);
        
        log.info("Processed robot cancelled: enterprise={}, qno={}, cno={}, appId={}", 
                enterpriseId, qno, cno, appId);
    }

    @Transactional
    public void processFlink(String str) {
        JSONObject event = JSONObject.parse(str);
        // 1. 更新企业级指标
//        var enterpriseMetric = new ChatMetricEnterprise();
//        enterpriseMetric.setEnterprise_id(event.getInteger("enterpriseId"));
//        enterpriseMetric.setReal_time_sessions(event.getInteger("enterpriseSessionCount"));
        enterpriseService.update(
                Wrappers.lambdaUpdate(ChatMetricEnterprise.class)
                        .set(ChatMetricEnterprise::getReal_time_sessions, event.getInteger("enterpriseSessionCount"))
                        .eq(ChatMetricEnterprise::getEnterprise_id, event.getInteger("enterpriseId")));
    }
} 