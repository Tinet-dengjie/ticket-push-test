package com.tinet.pushtest.configuration;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // 核心线程数
        executor.setMaxPoolSize(100); // 最大线程数
        executor.setQueueCapacity(5000); // 队列容量
        executor.setKeepAliveSeconds(60); // 线程空闲时间
        executor.setThreadNamePrefix("MyThreadPoolTaskExecutor-"); // 线程名前缀
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 拒绝策略
        executor.initialize(); // 初始化
        return executor;
    }

//    @Bean
//    public DataSource dataSource() {
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setMaximumPoolSize(100); // 设置最大连接池大小
//        // 其他配置...
//        return dataSource;
//    }

    @Value("${rocketmq.producer.group}")
    private String producerGroup;

    @Value("${rocketmq.name-server}")
    private String nameServer;

    /**
     * 由于使用的Spring版本是3.0.Q以上，与rocketMq不是很获容，对于rocketMgTemplate
     * 的自动注入存在差异，如果不采用这种方式注入则会报出缺少bean的信息
     */
//    @Bean("RocketMQTemplate")
//    public RocketMQTemplate rocketMqTemplate() {
//        RocketMQTemplate rocketMqTemplate = new RocketMQTemplate();
//
//        DefaultMQProducer defaultMqProducer = new DefaultMQProducer();
//        defaultMqProducer.setProducerGroup(producerGroup);
//        defaultMqProducer.setNamesrvAddr(nameServer);
//        defaultMqProducer.seta
//        rocketMqTemplate.setProducer(defaultMqProducer);
//
//        DefaultLitePullConsumer defaultLitePullConsumer = new DefaultLitePullConsumer();
//        rocketMqTemplate.setConsumer(defaultLitePullConsumer);
//        return rocketMqTemplate;
//    }
}