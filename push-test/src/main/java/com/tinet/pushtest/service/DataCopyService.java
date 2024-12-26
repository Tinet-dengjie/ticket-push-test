//package com.tinet.pushtest.service;
//
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.core.metadata.OrderItem;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.tinet.pushtest.model.ReceptionRecords;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//
///**
// * 类说明
// *
// * @author DengJie
// * @date 2024/12/17
// */
//@Service
//public class DataCopyService {
//    @Autowired
//    private LindormService lindormService;
//    @Autowired
//    private ReceptionRecordsServiceImpl receptionRecordsService;
//
//    public void copyData() {
//        // 从MySQL中查询数据
//        int current = 5847000;
//        while (true) {
//            List<ReceptionRecords> records = receptionRecordsService.listByPage(current,10000);
//            if (records.isEmpty()) {
//            }
//            // 将数据写入InfluxDB
//            lindormService.insertData(records);
//            current += records.size();
//        }
//    }
//}
