package com.tinet.pushtest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Random;

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

    @Autowired
    private QueryMetricsService queryMetricsService;

    private final Random random = new Random();

    public void simpleQuery() {
        for (int i = 0; i < 4; i++) {
            threadPoolTaskExecutor.submit(()->{
                while (true){
                    try {
                        long timestart = System.currentTimeMillis();
                        long l = receptionRecordsService.simpleQuery(randomChoice(getArray(1000, "QNO")));
                        float selectCost = (float) (System.currentTimeMillis() - timestart) / 1000;
                        queryMetricsService.recordLatency(selectCost);
                    } catch (Exception e) {
                        log.error("查询数据失败", e);
                    }
                }
            });
        }
    }

    public void complexQuery() {
        for (int i = 0; i < 20; i++) {
            threadPoolTaskExecutor.submit(()->{
                while (true){
                    try {
                        // 随机生成查询参数
                        String[] qnos = getRandomSubArray(getArray(1000, "QNO"), 20);
                        String[] cnos = getRandomSubArray(getArray(1000, "CNO"), 20);
                        
                        long timestart = System.currentTimeMillis();
                        long l = receptionRecordsService.complexQuery(qnos, cnos);
                        float selectCost = (float) (System.currentTimeMillis() - timestart) / 1000;
                        queryMetricsService.recordLatency(selectCost);
                    } catch (Exception e) {
                        log.error("复杂查询失败", e);
                    }
                }
            });
        }
    }

    private String randomChoice(String[] options) {
        int index = random.nextInt(options.length);
        return options[index];
    }

    private String[] getArray(int size, String prefix) {
        String[] strings = new String[size];
        for (int j = 1; j <= size; j++) {
            strings[j - 1] = prefix + j;
        }
        return strings;
    }

    private String[] getRandomSubArray(String[] source, int count) {
        count = Math.min(count, source.length);
        String[] result = new String[count];
        // 使用Fisher-Yates洗牌算法随机选择元素
        String[] temp = source.clone();
        for (int i = 0; i < count; i++) {
            int index = random.nextInt(temp.length - i);
            result[i] = temp[index];
            temp[index] = temp[temp.length - 1 - i];
        }
        return result;
    }
}
