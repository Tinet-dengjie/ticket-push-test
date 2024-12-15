package com.tinet.pushtest.test;

import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.RPCHook;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RocketMQProducer {
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    static final  AtomicLong atomicLong = new AtomicLong(0);
    static final  long total = 5000;
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
        DefaultMQProducer producer = new DefaultMQProducer(getAclRPCHook());
        producer.setNamespaceV2("rmq-cn-vc24046op09");
        // 使用VPC接入点时，无需配置RPCHook。
        // 如果实例类型为Serverless实例，则必须配置RPCHook。
        // DefaultMQProducer producer = new DefaultMQProducer();

        //您在消息队列RocketMQ版控制台创建的Group ID。
        producer.setProducerGroup("GID_LIVECHAT_CURL_TEST");

        // 设置接入方式为阿里云，在使用云上消息轨迹的时候，需要设置此项；如果不开启消息轨迹功能，则不需要运行此项。
        producer.setAccessChannel(AccessChannel.CLOUD);
        // 5.3.0版本及以上SDK开启消息轨迹除需设置AccessChannel外，需要增加，EnableTrace参数
        producer.setEnableTrace(true);

        // 设置为您从阿里云消息队列RocketMQ版控制台获取的接入点信息，类似“rmq-cn-XXXX.rmq.aliyuncs.com:8080”。
        // 注意！！！直接填写控制台提供的域名和端口即可，请勿添加http://或https://前缀标识，也不要用IP解析地址。
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        String json = "{\"name\":\"\",\"id\":\"123\",\"data\":{\n" +
                "    \"MAX_RETRY\": 1, \n" +
                "    \"url\": \"http://127.0.0.1:8089/pushContent\", \n" +
                "    \"params\": {\n" +
                "        \"messageType\": 14,\n" +
                "        \"sendName\": \"小P同学\",\n" +
                "        \"messageUniqueId\": \"ffd03af7-ca6b-4fed-960c-ac6e66254e57\",\n" +
                "        \"sender\": \"970291\",\n" +
                "        \"createTime\": 1731663281234,\n" +
                "        \"recipient\": \"733c3b34-d5c1-4c9e-b681-2e55150dc1da\",\n" +
                "        \"recipientName\": \"江苏南京0dc1da\",\n" +
                "        \"senderType\": 4,\n" +
                "        \"sessionId\": \"2619b1c0-79ef-44b7-8d23-260e47e36ffe.1731663120\",\n" +
                "        \"enterpriseId\": 8001678,\n" +
                "        \"content\": \"[{\\\"answerSource\\\":\\\"【FAQ】地方忽高忽低上次\\\",\\\"questionMessageId\\\":\\\"36611b37-b03b-4b32-b72b-7c4dbcbf9ca6\\\",\\\"text\\\":\\\"<p>尊敬的用户，您好，感谢您使用关爱平台，羊城通卡充值如果转账失败，所扣除金额会在三个工作日内原路返回到您的账户。</p>\\\",\\\"type\\\":5}]\"\n" +
                "    },\n" +
                "    \"type\": 10,\n" +
                "    \"timeout\": 10, \n" +
                "    \"requestTime\": \"\", \n" +
                "    \"uniqueId\": \"2619b1c0-79ef-44b7-8d23-260e47e36ffe.1731663120\", \n" +
                "    \"enterpriseId\": 8001678, \n" +
                "    \"method\": \"POST\", \n" +
                "    \"contentType\": \"application/json\", \n" +
                "    \"sync\": 0, \n" +
                "    \"retry\": 0, \n" +
                "    \"totalRetry\": 0, \n" +
                "    \n" +
                "    \"isLogRetry\": 0,\n" +
                "    \"uuid\": \"1111\", \n" +
                "    \"score\": 0 \n" +
                "}}";
        System.out.println(json);
        Thread.ofVirtual().start(() -> {
            System.out.println("start" + LocalDateTime.now().format(formatter));
            while (atomicLong.get() < total) {
                try {
                    Message msg = new Message("CHAT",
                            "CURL_EVENT",
                            json.getBytes(RemotingHelper.DEFAULT_CHARSET));
                    SendResult sendResult = producer.send(msg);
                    atomicLong.incrementAndGet();
                } catch (Exception e) {
                    //消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理。
                    System.out.println(new Date() + " Send mq message failed.");
                    e.printStackTrace();
                }
            }
            System.out.println("end" + LocalDateTime.now().format(formatter)+"-"+atomicLong.get());
        });


        // 在应用退出前，销毁Producer对象。
        // 注意：销毁Producer对象可以节约系统内存，若您需要频繁发送消息，则无需销毁Producer对象。
    }
}