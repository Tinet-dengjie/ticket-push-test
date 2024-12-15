package com.tinet.pushtest.configuration;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class OkHttpConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        // 配置连接池
        ConnectionPool connectionPool = new ConnectionPool(
                50,         // 最大空闲连接数
                5,          // 保持空闲连接的最大时间
                TimeUnit.MINUTES // 时间单位
        );

        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)   // 连接超时时间
                .readTimeout(30, TimeUnit.SECONDS)      // 读取超时时间
                .writeTimeout(30, TimeUnit.SECONDS)     // 写入超时时间
                .connectionPool(connectionPool)         // 设置连接池
                .build();
    }
}