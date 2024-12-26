package com.tinet.pushtest.configuration;

import com.tinet.pushtest.service.ChatMetricsProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.RPCHook;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 类说明
 *
 * @author DengJie
 * @date 2024/12/24
 */
@Configuration
@Slf4j
public class MqConfig {
    private static RPCHook getAclRPCHook() {
        return new AclClientRPCHook(new SessionCredentials("8Is3hW0b0ipC6i0o", "49YT4viNZQa57cEG"));
    }

    @Bean
    public DefaultMQProducer defaultMQProducer(){
        try {
            DefaultMQProducer producer = new DefaultMQProducer(getAclRPCHook());
            producer.setNamespaceV2("rmq-cn-zqb424r7c02");
            producer.setProducerGroup("GID_LIVECHAT_CURL_TEST");
            producer.setAccessChannel(AccessChannel.CLOUD);
            producer.setEnableTrace(true);
            producer.setNamesrvAddr("rmq-cn-zqb424r7c02.cn-beijing.rmq.aliyuncs.com:8080");
            producer.start();
            return producer;
        } catch (MQClientException e) {
            log.error("MQClientException", e);
        }
        return null;
    }
    @Bean
    public DefaultMQPushConsumer defaultMQPushConsumer(ChatMetricsProcessor chatMetricsProcessor){
        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(getAclRPCHook());
            consumer.setNamespaceV2("rmq-cn-zqb424r7c02");
            consumer.setConsumerGroup("GID_LIVECHAT_CURL_TEST");
            consumer.setAccessChannel(AccessChannel.CLOUD);
            consumer.setEnableTrace(true);
            consumer.setNamesrvAddr("rmq-cn-zqb424r7c02.cn-beijing.rmq.aliyuncs.com:8080");
            consumer.subscribe("sink_topic", "*");
            consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
                msgs.forEach(item -> {
                   chatMetricsProcessor.processFlink(new String(item.getBody(), StandardCharsets.UTF_8));
                });
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();
            return consumer;
        } catch (MQClientException e) {
            log.error("MQClientException", e);
        }
        return null;
    }
}
