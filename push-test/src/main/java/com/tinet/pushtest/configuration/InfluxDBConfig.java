//package com.tinet.pushtest.configuration;
//
//import org.influxdb.InfluxDB;
//import org.influxdb.InfluxDBFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.concurrent.TimeUnit;
//
//@Configuration
//public class InfluxDBConfig {
//
//    @Value("${influxdb.url}")
//    private String url;
//
//    @Value("${influxdb.username}")
//    private String username;
//
//    @Value("${influxdb.password}")
//    private String password;
//
//    @Value("${influxdb.database}")
//    private String database;
//
//    @Bean
//    public InfluxDB influxDB() {
//        InfluxDB influxDB = InfluxDBFactory.connect(url, username, password);
//        influxDB.setDatabase(database);
//        influxDB.setRetentionPolicy("autogen"); // 设置默认的保留策略
//        influxDB.enableBatch(500, 1000, TimeUnit.MILLISECONDS); // 启用批量写入（可选）
//        return influxDB;
//    }
//}