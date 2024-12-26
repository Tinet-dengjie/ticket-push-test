package com.tinet.pushtest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tinet.pushtest.model.ChatMetricEnterprise;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMetricEnterpriseMapper extends BaseMapper<ChatMetricEnterprise> {
    int insertOrUpdate(ChatMetricEnterprise metric);
} 