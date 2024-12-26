//package com.tinet.pushtest.service;
//
//import com.tinet.pushtest.model.ReceptionRecords;
//import com.tinet.pushtest.model.SessionMetrics;
//import com.tinet.pushtest.model.SessionState;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Random;
//import java.util.UUID;
//
//@Slf4j
//@Service
//public class SessionGenerator {
//    @Autowired
//    private ReceptionRecordsServiceImpl receptionRecordsService;
//
//    @Autowired
//    private InfluxDBService influxDBService;
//
//    @Autowired
//    private SessionMetricsService sessionMetricsService;
//
//    private final Random random = new Random();
//
//    public void generateSession() {
//        // 生成基础信息
//        String qno = randomChoice(getArray(100, "QNO"));
//        String cno = randomChoice(getArray(100, "CNO"));
//        String app = randomChoice(getArray(20, "APP"));
//        String sessionId = UUID.randomUUID().toString();
//
//        // 开始会话流程
//        processSessionState(SessionState.STARTED, qno, cno, app, sessionId);
//
//        // 进入列
//        processSessionState(SessionState.QUEUED, qno, cno, app, sessionId);
//
//        // 1%概率停在这里还没完结
//        if (random.nextDouble() < 0.1) {
//            return;
//        }
//        // 10%概率放弃排队
//        if (random.nextDouble() < 0.1) {
//            processSessionState(SessionState.QUEUE_ABANDONED, qno, cno, app, sessionId);
//            return;
//        }
//
//        // 分配到座席
//        processSessionState(SessionState.ASSIGNED, qno, cno, app, sessionId);
//        // 10%概率停在这里还没完结
//        if (random.nextDouble() < 0.20) {
//            return;
//        }
//
//        // 随机选择三种情况之一
//        int scenario = random.nextInt(3);
//        switch (scenario) {
//            case 0 -> normalScenario(qno, cno, app, sessionId);
//            case 1 -> lockScenario(qno, cno, app, sessionId);
//            case 2 -> robotScenario(qno, cno, app, sessionId);
//        }
//    }
//
//    private void normalScenario(String qno, String cno, String app, String sessionId) {
//        processSessionState(SessionState.COMPLETED, qno, cno, app, sessionId);
//    }
//
//    private void lockScenario(String qno, String cno, String app, String sessionId) {
//        processSessionState(SessionState.LOCKED, qno, cno, app, sessionId);
//        // 10%概率停在这里还没完结
//        if (random.nextDouble() < 0.20) {
//            return;
//        }
//        processSessionState(SessionState.UNLOCKED, qno, cno, app, sessionId);
//        processSessionState(SessionState.COMPLETED, qno, cno, app, sessionId);
//    }
//
//    private void robotScenario(String qno, String cno, String app, String sessionId) {
//        processSessionState(SessionState.ROBOT_MANAGED, qno, cno, app, sessionId);
//        // 10%概率停在这里还没完结
//        if (random.nextDouble() < 0.20) {
//            return;
//        }
//        processSessionState(SessionState.ROBOT_CANCELLED, qno, cno, app, sessionId);
//        processSessionState(SessionState.COMPLETED, qno, cno, app, sessionId);
//    }
//
//    protected void processSessionState(SessionState state, String qno, String cno, String app, String sessionId) {
//        // 1. 更新中间表
//        SessionMetrics metrics = sessionMetricsService.getOrCreate(qno, cno, app);
//        updateMetrics(metrics, state, true);
//        sessionMetricsService.updateById(metrics);
//
//        // 2. 写入事件表
//        ReceptionRecords record = createEventRecord(state, qno, cno, app, sessionId, metrics);
////        receptionRecordsService.save(record);
////        influxDBService.insertData(record);
//    }
//
//    private void updateMetrics(SessionMetrics metrics, SessionState state, boolean isIncrement) {
//        int delta = isIncrement ? 1 : -1;
//        switch (state) {
//            case STARTED -> metrics.setRealtime_session_count(metrics.getRealtime_session_count() + delta);
//            case QUEUED -> metrics.setRealtime_session_queued_count(metrics.getRealtime_session_queued_count() + delta);
//            case QUEUE_ABANDONED -> {
//                metrics.setRealtime_session_queued_count(metrics.getRealtime_session_queued_count() - 1);  // 离开队列
//            }
//            case ASSIGNED -> {
//                metrics.setRealtime_session_queued_count(metrics.getRealtime_session_queued_count() - 1);  // 离开队列
//                metrics.setRealtime_session_assigned_count(metrics.getRealtime_session_assigned_count() + delta);
//            }
//            case LOCKED -> metrics.setRealtime_session_locked_count(metrics.getRealtime_session_locked_count() + delta);
//            case UNLOCKED ->
//                    metrics.setRealtime_session_locked_count(metrics.getRealtime_session_locked_count() - 1);  // 解除锁定
//            case ROBOT_MANAGED ->
//                    metrics.setRealtime_session_robot_count(metrics.getRealtime_session_robot_count() + delta);
//            case ROBOT_CANCELLED ->
//                    metrics.setRealtime_session_robot_count(metrics.getRealtime_session_robot_count() - 1);  // 取消机器人托管
//            case COMPLETED -> {
//                metrics.setRealtime_session_count(metrics.getRealtime_session_count() - 1);  // 会话结束
//                metrics.setRealtime_session_assigned_count(metrics.getRealtime_session_assigned_count() - 1);  // 离开座席
//            }
//        }
//    }
//
//    private ReceptionRecords createEventRecord(SessionState state, String qno, String cno, String app,
//                                               String sessionId, SessionMetrics metrics) {
//        ReceptionRecords record = new ReceptionRecords();
//        record.setQno(qno);
//        record.setCno(cno);
//        record.setApp(app);
//        record.setEvent(state.name());
//
//        // 设置各种计数器
//        record.setRealtime_session_count(metrics.getRealtime_session_count());
//        record.setRealtime_session_queued_count(metrics.getRealtime_session_queued_count());
//        record.setRealtime_session_assigned_count(metrics.getRealtime_session_assigned_count());
//        record.setRealtime_session_locked_count(metrics.getRealtime_session_locked_count());
//        record.setRealtime_session_robot_count(metrics.getRealtime_session_robot_count());
//
//        // 根据状态设置特定字段
//        switch (state) {
////            case STARTED -> record.setEvent("session");
////            case QUEUED -> record.setEvent("queue");
//            case QUEUE_ABANDONED -> {
//                record.setQueue_abort(1);
////                record.setEvent("queue");
//            }
//            case ASSIGNED -> {
//                record.setQueue_duration(random.nextInt(100, 1000));
////                record.setEvent("agent");
//            }
////            case LOCKED, UNLOCKED -> record.setEvent("lock");
////            case ROBOT_MANAGED, ROBOT_CANCELLED -> record.setEvent("robot");
//            case COMPLETED -> {
//                record.setSession_complete(1);
//                record.setSession_duration(random.nextInt(100, 1000));
////                record.setEvent("session");
//            }
//        }
//
//        return record;
//    }
//
//    private String[] getArray(int size, String prefix) {
//        String[] strings = new String[size];
//        for (int j = 1; j <= size; j++) {
//            strings[j - 1] = prefix + j;
//        }
//        return strings;
//    }
//
//    private String randomChoice(String[] options) {
//        int index = random.nextInt(options.length);
//        return options[index];
//    }
//
//    // 其他辅助方法保持不变...
//}