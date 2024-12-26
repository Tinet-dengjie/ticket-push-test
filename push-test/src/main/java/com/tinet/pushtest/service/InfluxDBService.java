//package com.tinet.pushtest.service;
//
//import com.tinet.pushtest.model.ReceptionRecords;
//import org.influxdb.InfluxDB;
//import org.influxdb.dto.Point;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class InfluxDBService {
//
////    @Autowired
////    private InfluxDB influxDB;
//
//    public void insertData(ReceptionRecords entity) {
//        Point point = Point.measurement("session_event_new")
//                .tag("app", entity.getApp())
//                .tag("qno", entity.getQno())
//                .tag("cno", entity.getCno())
//                .tag("event", entity.getEvent())
//                .addField("queue_abort", entity.getQueue_abort())
//                .addField("queue_duration", entity.getQueue_duration())
//                .addField("session_duration", entity.getSession_duration())
//                .addField("session_complete", entity.getSession_complete())
//                .addField("realtime_session_count", entity.getRealtime_session_count())
//                .addField("realtime_session_queued_count", entity.getRealtime_session_queued_count())
//                .addField("realtime_session_locked_count", entity.getRealtime_session_locked_count())
//                .addField("realtime_session_assigned_count", entity.getRealtime_session_assigned_count())
//                .addField("realtime_session_robot_count", entity.getRealtime_session_robot_count())
//                .build();
////        influxDB.write(point); // 写入数据
//    }
//}