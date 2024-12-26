package com.tinet.pushtest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tinet.pushtest.model.ReceptionRecords;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ReceptionRecordsMapper extends BaseMapper<ReceptionRecords> {
    Map<String, Object> simpleQuery(@Param("qno") String qno);

    Long complexQuery(@Param("qnos") String[] qnos, @Param("cnos") String[] cnos);

    List<ReceptionRecords> listByPage(@Param("offset") int current, @Param("size") int i);
}
