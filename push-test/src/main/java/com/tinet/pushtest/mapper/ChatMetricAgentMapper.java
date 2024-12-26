package com.tinet.pushtest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tinet.pushtest.model.ChatMetricAgent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMetricAgentMapper extends BaseMapper<ChatMetricAgent> {

    void insertOrUpdate(ChatMetricAgent agentMetric);
}