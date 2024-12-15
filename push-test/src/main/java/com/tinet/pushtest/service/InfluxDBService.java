package com.tinet.pushtest.service;

import com.tinet.pushtest.model.ReceptionRecords;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfluxDBService {

    @Autowired
    private InfluxDB influxDB;

    public void insertData(ReceptionRecords entity) {
        Point point = Point.measurement("session_event")
                .tag("app", entity.getApp())
                .tag("qno", entity.getQno())
                .tag("cno", entity.getCno())
                .addField("queue_abort", entity.getQueue_abort())
                .addField("queue_duration", entity.getQueue_duration())
                .addField("session_duration", entity.getSession_duration())
                .addField("session_complete", entity.getSession_complete())
                .build();
        influxDB.write(point); // 写入数据
    }
}