//package com.tinet.pushtest.util;
//
//import com.baomidou.mybatisplus.generator.FastAutoGenerator;
//import com.baomidou.mybatisplus.generator.config.OutputFile;
//import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
//import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
//
//import java.sql.Types;
//import java.util.Collections;
//
///**
// * 代码快速生产
// *
// * @author DengJie
// * @date 2024/07/16
// */
//public class CodeGenerator {
//    public  void main(String[] args) {
//        FastAutoGenerator.create("jdbc:postgresql://pgm-bp1x0wpjo59wxgoguo.pg.rds.aliyuncs.com:5432/timescaledb_test?useSSL=false&serverTimezone=Asia/Shanghai", "appserver", "Aa1122334@!#$")
//                .globalConfig(builder -> {
//                    builder.author("dengjie") // 设置作者
//                            .commentDate("yyyy/MM/dd")
//                            .outputDir("push-test/src/main/java"); // 指定输出目录
//                })
//                .dataSourceConfig(builder ->
//                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
//                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
//                            if (typeCode == Types.SMALLINT) {
//                                // 自定义类型转换
//                                return DbColumnType.INTEGER;
//                            }
//                            if (typeCode == Types.TIMESTAMP_WITH_TIMEZONE) {
//                                // 自定义类型转换
//                                return DbColumnType.LOCAL_DATE_TIME;
//                            }
//                            return typeRegistry.getColumnType(metaInfo);
//                        })
//                )
//                .packageConfig(builder ->
//                        builder.parent("com.tinet.pushtest") // 设置父包名
//                                .pathInfo(Collections.singletonMap(OutputFile.xml, "resources")) // 设置mapperXml生成路径
//                                .entity("model")
//                                .service("service")
//                                .serviceImpl("service")
//                                .mapper("mapper")
//                                .controller("controller")
//                                .xml("mapper")
//                                .pathInfo(Collections.singletonMap(OutputFile.xml, "push-test/src/main/resources/"))
//                                .build()
//                )
//                .strategyConfig(builder ->
//                                builder.addInclude("reception_records") // 设置需要生成的表名\
////                                .addTablePrefix("t_", "c_") // 设置过滤表前缀
//                )
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
//                .execute();
//    }
//}
