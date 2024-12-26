package com.tinet.pushtest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tinet.pushtest.model.SessionMetrics;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionMetricsMapper extends BaseMapper<SessionMetrics> {
    SessionMetrics selectByKeys(@Param("qno") String qno, 
                              @Param("cno") String cno, 
                              @Param("app") String app);
} 