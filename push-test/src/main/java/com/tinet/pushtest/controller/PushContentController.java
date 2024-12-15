package com.tinet.pushtest.controller;

import com.alibaba.fastjson2.JSONObject;
import com.tinet.pushtest.service.Run;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 类说明
 *
 * @author DengJie
 * @date 2022/12/02
 */
@RestController
@Slf4j
public class PushContentController {
//
    @Autowired
    private Run run;
    public static String pushCpntent = "暂无数据";
    public static String pushTime = "暂无时间";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    AtomicLong atomicLong = new AtomicLong(0);
    @Autowired
    private TestService testService;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

//    @Autowired
//    private MeterRegistry meterRegistry;
//    private Timer myEndpointTimer = Timer.builder("api.requests.tps")
//            .description("API requests TPS")
//            .register(meterRegistry);
//

    @PostMapping("/pushContent")
    public String pushContent(@RequestBody String pushContent) {
//        myEndpointTimer.record();
        log.info("pushContent: " + pushContent);
        pushCpntent = pushContent;
        pushTime = LocalDateTime.now().format(formatter);
        try {
            Thread.sleep( 100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info(pushTime+"-"+atomicLong.incrementAndGet());
        return pushTime;
    }

    @GetMapping("/pushContent")
    public String getPushContent() {
        for (int i = 0; i < 1; i++) {
            threadPoolTaskExecutor.execute(new Thread(() -> testService.test1()));
        }
        return "推送时间: \n" + pushTime + "\n" + "推送内容: \n" + pushCpntent;
    }

    @GetMapping("/doRun")
    public void dorun() {
        run.test();
    }
}
