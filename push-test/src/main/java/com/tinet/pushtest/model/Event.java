package com.tinet.pushtest.model;

import lombok.Data;

import java.util.UUID;

/**
 * @project livechat-java
 * @description 事件类型
 * @author zhanglicai
 * @date 2024-05-27 14:10:08
 * @version 1.0
 */
@Data
public class Event<T> {

    /**
     * 事件名称，可根据名称做分发
     */
    private String name;

    /**
     * 事件id，可根据该id做幂等
     */
    private String id;

    /**
     * 事件内容
     */
    private T data;


    public Event() {
        this.id = UUID.randomUUID().toString();
    }

    public Event(String name, String id, T data) {
        this.name = name;
        this.id = id;
        this.data = data;
    }

    public Event(String name, T data) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
        this.data = data;
    }

    /**
     * 新建事件
     */
    public static <T> Event<T> valueOf(String name, T data) {
        return new Event<T>(name, data);
    }




}
