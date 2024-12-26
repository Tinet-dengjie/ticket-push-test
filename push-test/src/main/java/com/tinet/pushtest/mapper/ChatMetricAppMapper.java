package com.tinet.pushtest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tinet.pushtest.model.ChatMetricApp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMetricAppMapper extends BaseMapper<ChatMetricApp> {

    void insertOrUpdate(ChatMetricApp appMetric);
}