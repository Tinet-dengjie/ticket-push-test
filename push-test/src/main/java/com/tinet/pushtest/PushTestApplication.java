package com.tinet.pushtest;

//import org.mybatis.spring.annotation.MapperScan;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

import java.math.BigDecimal;

@MapperScan(basePackages = {"com.tinet.pushtest.mapper"})
@SpringBootApplication(scanBasePackages = {"com.tinet.pushtest"}, exclude = UserDetailsServiceAutoConfiguration.class)
public class PushTestApplication {


    public static void main(String[] args) {
        System.setProperty("rocketmq.client.logUseSlf4j", "true");
        SpringApplication.run(PushTestApplication.class, args);
    }

}
