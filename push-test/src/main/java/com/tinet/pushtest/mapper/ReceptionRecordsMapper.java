package com.tinet.pushtest.mapper;

import com.tinet.pushtest.model.ReceptionRecords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dengjie
 * @since 2024/11/08
 */
public interface ReceptionRecordsMapper extends BaseMapper<ReceptionRecords> {

    Map<Object,Object> countFinish();

    long countAvg();

}
