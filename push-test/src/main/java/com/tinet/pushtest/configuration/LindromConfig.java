//package com.tinet.pushtest.configuration;
//
//import com.aliyun.lindorm.tsdb.client.ClientOptions;
//import com.aliyun.lindorm.tsdb.client.LindormTSDBClient;
//import com.aliyun.lindorm.tsdb.client.LindormTSDBFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 类说明
// *
// * @author DengJie
// * @date 2024/12/18
// */
//@Configuration
//public class LindromConfig {
////    @Bean
//    public LindormTSDBClient lindormTSDBClient() {
//        // 1.创建客户端实例
//        String url = "http://ld-2vcr04jf541gumg4u-proxy-tsdb-pub.lindorm.rds.aliyuncs.com:8242";
//        // LindormTSDBClient线程安全，可以重复使用，无需频繁创建和销毁
//        ClientOptions options = ClientOptions.newBuilder(url).build();
//        return LindormTSDBFactory.connect(options);
//    }
//
//}
