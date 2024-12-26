//package com.tinet.pushtest.service;
//
//import com.aliyun.lindorm.tsdb.client.LindormTSDBClient;
//import com.aliyun.lindorm.tsdb.client.exception.LindormTSDBException;
//import com.aliyun.lindorm.tsdb.client.model.Record;
//import com.aliyun.lindorm.tsdb.client.model.WriteResult;
//import com.aliyun.lindorm.tsdb.client.utils.ExceptionUtils;
//import com.tinet.pushtest.model.ReceptionRecords;
//import org.influxdb.dto.Point;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//
///**
// * 类说明
// *
// * @author DengJie
// * @date 2024/12/18
// */
//@Service
//public class LindormService {
//    @Autowired
//    private LindormTSDBClient lindormTSDBClient;
//    public void insertData(List<ReceptionRecords> receptionRecords) {
//        List<Record> records = new ArrayList<>(receptionRecords.size());
//        receptionRecords.forEach(entity -> {
//            Record record = Record
//                    .table("session_event_new")
//                    .time(System.currentTimeMillis())
//                    .tag("app", entity.getApp())
//                    .tag("qno", entity.getQno())
//                    .tag("cno", entity.getCno())
//                    .tag("event", entity.getEvent())
//                    .addField("queue_abort", Double.valueOf(entity.getQueue_abort()))
//                    .addField("queue_duration", Double.valueOf(entity.getQueue_duration()))
//                    .addField("session_duration", Double.valueOf(entity.getSession_duration()))
//                    .addField("session_complete", Double.valueOf(entity.getSession_complete()))
//                    .addField("realtime_session_count", Double.valueOf(entity.getRealtime_session_count()))
//                    .addField("realtime_session_queued_count", Double.valueOf(entity.getRealtime_session_queued_count()))
//                    .addField("realtime_session_locked_count", Double.valueOf(entity.getRealtime_session_locked_count()))
//                    .addField("realtime_session_assigned_count", Double.valueOf(entity.getRealtime_session_assigned_count()))
//                    .addField("realtime_session_robot_count", Double.valueOf(entity.getRealtime_session_robot_count()))
//                    .build();
//            records.add(record);
//        });
//
//        CompletableFuture<WriteResult> future = lindormTSDBClient.write("session_event_interval_1d", records);
//        // 处理异步写入结果
//        future.whenComplete((r, ex) -> {
//            // 处理写入失败
//            if (ex != null) {
//                System.out.println("Failed to write.");
//                Throwable throwable = ExceptionUtils.getRootCause(ex);
//                if (throwable instanceof LindormTSDBException) {
//                    LindormTSDBException e = (LindormTSDBException) throwable;
//                    System.out.println("Caught an LindormTSDBException, which means your request made it to Lindrom TSDB, "
//                            + "but was rejected with an error response for some reason.");
//                    System.out.println("Error Code: " + e.getCode());
//                    System.out.println("SQL State:  " + e.getSqlstate());
//                    System.out.println("Error Message: " + e.getMessage());
//                }  else {
//                    throwable.printStackTrace();
//                }
//            } else  {
//                System.out.println("Write successfully.");
//            }
//        });
//    }
//}
