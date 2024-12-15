package com.tinet.pushtest.service;

import com.google.common.util.concurrent.RateLimiter;
import com.tinet.pushtest.model.ReceptionRecords;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 类说明
 *
 * @author DengJie
 * @date 2024/11/11
 */
@Service
@Slf4j
public class SqlRun {
    @Autowired
    private ReceptionRecordsServiceImpl receptionRecordsService;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
//    @PostConstruct
    public void test() {
        for (int i = 0; i < 4; i++) {
//            AtomicLong atomicLong = new AtomicLong(0);
            threadPoolTaskExecutor.submit(()->{
//                RateLimiter rateLimiter = RateLimiter.create();
                float duration = 0;
                while (true){
//                    rateLimiter.tryAcquire();
                    try {
                        long timestart = System.currentTimeMillis();
                        long l = receptionRecordsService.countFinish();
                        float selectCost = (float) (System.currentTimeMillis() - timestart) / 1000;
                        if(duration != 0){
                            duration = (selectCost + duration) / 2;
                        }else{
                            duration = selectCost;
                        }
                        // 不使用事务插入
                        log.info("完成回话查询平均耗时{}", duration);
                    } catch (Exception e) {
                        log.error("插入数据失败", e);
                    }
                }
            });
        }

    }

//    @PostConstruct
    public void main() {
        for (int i = 0; i < 20; i++) {
            AtomicLong atomicLong = new AtomicLong(0);
            threadPoolTaskExecutor.submit(()->{
                float duration = 0;
                while (true){
                    long timestart = System.currentTimeMillis();
//                    long l = receptionRecordsService.countAvg();
                    float selectCost = (float) (System.currentTimeMillis() - timestart) / 1000;
                    if(duration != 0){
                        duration = (selectCost + duration) / 2;
                    }else{
                        duration = selectCost;
                    }
                    // 不使用事务插入
                    log.info("平均回话查询平均耗时{}", duration);
                }
            });
        }
    }

}
