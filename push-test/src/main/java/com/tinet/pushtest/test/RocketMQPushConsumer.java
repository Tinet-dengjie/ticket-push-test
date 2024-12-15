package com.tinet.pushtest.test;

import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.RPCHook;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class RocketMQPushConsumer {
    /**
     * 如果是使用公网接入点访问，则必须设置RpcHook，里面填写实例的用户名和密码。实例用户名和密码在控制台访问控制的智能身份识别页签中获取。
     * 注意！！！这里填写的不是阿里云账号的AccessKey ID和AccessKey Secret，请务必区分开。
     * 如果是在阿里云ECS内网访问，无需初始化RpcHook，服务端会根据内网VPC信息智能获取。
     * 如果实例类型为Serverless实例，公网访问必须设置实例的用户名密码，当开启内网免身份识别时，内网访问可以不设置用户名和密码。
     */
    private static RPCHook getAclRPCHook() {
        return new AclClientRPCHook(new SessionCredentials("3ppQMwZgV0FJW03Q", "25s5j5TJIyncAg5f"));
    }

    public static void main(String[] args) throws MQClientException {
        // 使用公网接入点时，需要配置RPCHook。
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(getAclRPCHook());
        // 使用VPC接入点时，无需配置RPCHook。
        // 如果实例类型为Serverless实例，则必须配置RPCHook。
        // DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        consumer.setNamespaceV2("rmq-cn-vc24046op09");
        // 使用VPC接入点时，无需配置RPCHook。
        // 如果实例类型为Serverless实例，则必须配置RPCHook。
        // DefaultMQProducer producer = new DefaultMQProducer();

        //您在消息队列RocketMQ版控制台创建的Group ID。
        consumer.setConsumerGroup("GID_LIVECHAT_CURL_TEST");
        //您在消息队列RocketMQ版控制台创建的Group ID。

        // 设置接入方式为阿里云，在使用云上消息轨迹的时候，需要设置此项；如果不开启消息轨迹功能，则不需要运行此项。
        consumer.setAccessChannel(AccessChannel.CLOUD);
        // 5.3.0版本及以上SDK开启消息轨迹除需设置AccessChannel外，需要增加，EnableTrace参数
        consumer.setEnableTrace(true);

        // 设置为您从阿里云消息队列RocketMQ版控制台获取的接入点信息，类似“rmq-cn-XXXX.rmq.aliyuncs.com:8080”。
        // 注意！！！直接填写控制台提供的域名和端口即可，请勿添加http://或https://前缀标识，也不要用IP解析地址。
        consumer.setNamesrvAddr("127.0.0.1:9876");
        // 设置为您在阿里云云消息队列 RocketMQ 版控制台上创建的Topic。
        consumer.subscribe("TestT", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                System.out.println("RocketMQPushConsumer 接收到数据大小{} " + msgs.size());
                msgs.forEach(item -> {
                    System.out.printf("%s -RocketMQPushConsumer Receive New Messages: %s %n",System.currentTimeMillis(), new String(item.getBody(), StandardCharsets.UTF_8));
                });
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}