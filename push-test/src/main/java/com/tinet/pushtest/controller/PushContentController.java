package com.tinet.pushtest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类说明
 *
 * @author DengJie
 * @date 2022/12/02
 */
@RestController
public class PushContentController {
    public static String pushCpntent = "暂无数据";

    @PostMapping("/pushContent")
    public String pushContent(@RequestBody String pushContent) {
        pushCpntent = pushContent;
        return "ok";
    }

    @GetMapping("/pushContent")
    public String getPushContent() {
        return pushCpntent;
    }
}
