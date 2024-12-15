//package com.tinet.pushtest.test;
//
//import java.time.Instant;
//import java.util.List;
//import java.util.Random;
//
//import com.influxdb.client.InfluxDBClient;
//import com.influxdb.client.InfluxDBClientFactory;
//import com.influxdb.client.QueryApi;
//import com.influxdb.client.WriteApiBlocking;
//import com.influxdb.client.write.Point;
//import com.influxdb.query.FluxRecord;
//import com.influxdb.query.FluxTable;
//import com.tinet.pushtest.model.ReceptionRecords;
//
//public final class WriteQueryExample {
//    private static final Random random = new Random();
//    public static void main(final String[] args) throws Exception {
//        String hostUrl = "http://127.0.0.1:8086";
//        char[] authToken = "scoUaJowKGruXMiYSF5UBLVofnR7iTfmKqOEigcXZuXLSrYJNxIMZIo1fRY7vdE40EzVY4lbptsCTKOoW8JX8w==".toCharArray();
//
//        try (InfluxDBClient client = InfluxDBClientFactory.create(hostUrl, authToken)) {
//            String bucket = "shad0w";
//            String org = "shad0w";
//            WriteApiBlocking writeApi = client.getWriteApiBlocking();
//            while (true){
//                ReceptionRecords entity = generateRandomReceptionRecord();
//                Point point = Point.measurement("session_event")
//                        .addTag("app", entity.getApp())
//                        .addTag("qno", entity.getQno())
//                        .addTag("cno", entity.getCno())
//                        .addField("queue_abort", entity.getQueue_abort())
//                        .addField("queue_duration", entity.getQueue_duration())
//                        .addField("session_duration", entity.getSession_duration())
//                        .addField("session_complete", entity.getSession_complete());
//                writeApi.writePoint(bucket, org, point);
//            }
//
////
////            System.out.println("Complete. Return to the InfluxDB UI.");
////            QueryApi queryApi = client.getQueryApi();
////
////            String query = "from(bucket: \"shad0w\")" +
////                    " |> range(start: -10m)";
//
////            for (FluxTable table : queryApi.query(query, org)) {
////                List<FluxRecord> records = table.getRecords();
////                for (FluxRecord record : records) {
////                    String field = record.getField();
////                    Object value = record.getValue();
////                    Instant time = record.getTime();
////
////                    System.out.printf("| %-5s | %-5s | %-30s |%n", field, value, time);
////                }
////            }
////            String queryAggregate = "from(bucket: \"shad0w\")" +
////                    " |> range(start: -10m)" +
////                    " |> mean()";
////
////            for (FluxTable table : queryApi.query(queryAggregate, org)) {
////                List<FluxRecord> records = table.getRecords();
////                for (FluxRecord record : records) {
////                    String field = record.getField();
////                    Object value = record.getValue();
////
////                    System.out.printf("| %-5s | %-20s |%n", field, value);
////                }
////            }
//        }
//
//    }
//    private static ReceptionRecords generateRandomReceptionRecord() {
//        ReceptionRecords entity = new ReceptionRecords();
//        entity.setQno(randomChoice(WriteQueryExample.getArray(100, "QNO")));
//        entity.setCno(randomChoice(WriteQueryExample.getArray(1000, "CNO")));
//        entity.setApp(randomChoice(WriteQueryExample.getArray(1000, "APP")));
//        entity.setQueue_abort(generateSessionUniqueId(false) > 0 ? 1 : 0);
//        entity.setQueue_duration(generateSessionUniqueId(false));
//
//        if (entity.getQueue_abort() == 0 && entity.getQueue_duration() == 0) {
//            int sessionDuration = generateSessionUniqueId(true);
//            entity.setSession_duration(sessionDuration);
//            entity.setSession_complete(sessionDuration > 0 ? 1 : 0);
//        }
//
//        return entity;
//    }
//
//    private static String randomChoice(String[] options) {
//        int index = random.nextInt(options.length);
//        return options[index];
//    }
//
//    private static String[] getArray(int i, String qno) {
//        String[] strings = new String[i];
//        for (int j = 1; j <= i; j++) {
//            strings[j - 1] = qno + j;
//        }
//        return strings;
//    }
//
//    private static int generateSessionUniqueId(boolean forceNew) {
//        // 有10%的概率生成一个新的session_unique_id
//        if (random.nextDouble() < 0.5 || forceNew) {
//            return random.nextInt(100, 100000);
//        } else {
//            // 否则，从已有的session_unique_id中随机选择一个
//            return 0;
//        }
//    }
//}
