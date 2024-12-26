package com.tinet.pushtest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tinet.pushtest.model.ChatMetricQueue;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMetricQueueMapper extends BaseMapper<ChatMetricQueue> {

    void insertOrUpdate(ChatMetricQueue queueMetric);
}