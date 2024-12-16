package com.tinet.pushtest.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tinet.pushtest.mapper.ReceptionRecordsMapper;
import com.tinet.pushtest.model.ReceptionRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author dengjie
 * @since 2024/11/08
 */
@Service
public class ReceptionRecordsServiceImpl extends ServiceImpl<ReceptionRecordsMapper, ReceptionRecords> {
    @Autowired
    private ReceptionRecordsMapper receptionRecordsMapper;

    public long simpleQuery(String qno) {
        baseMapper.simpleQuery(qno);
        return 0;
    }

    public long complexQuery(String[] qnos, String[] cnos) {
        return receptionRecordsMapper.complexQuery(qnos, cnos);
    }
}
