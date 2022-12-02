package com.tinet.pushtest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类说明
 *
 * @author DengJie
 * @date 2022/12/02
 */
@RestController
public class PushContentController {
    public static String pushCpntent = "暂无数据";
    SimpleDateFormat pdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String pushTime = "暂无时间";

    @PostMapping("/pushContent")
    public String pushContent(@RequestBody String pushContent) {
        pushCpntent = pushContent;
        pushTime = pdf.format(new Date());
        return "ok";
    }

    @GetMapping("/pushContent")
    public String getPushContent() {
        return "推送时间: \n" + pushTime + "\n" + "推送内容: \n" + pushCpntent;
    }
}
