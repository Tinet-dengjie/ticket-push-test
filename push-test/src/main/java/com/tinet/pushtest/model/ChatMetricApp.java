package com.tinet.pushtest.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("chat_metric_app")
public class ChatMetricApp {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 企业ID
     */
    private Integer enterprise_id;

    /**
     * 应用ID
     */
    private String app_id;

    /**
     * 今日访客会话数
     */
    private Integer today_visitor_sessions;

    /**
     * 今日已完成会话数
     */
    private Integer today_completed_sessions;

    /**
     * 实时会话数
     */
    private Integer real_time_sessions;

    /**
     * 实时接待中会话数
     */
    private Integer real_time_reception_sessions;
} 