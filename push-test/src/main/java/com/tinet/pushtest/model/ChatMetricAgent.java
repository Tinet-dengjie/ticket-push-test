package com.tinet.pushtest.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("chat_metric_agent")
public class ChatMetricAgent {
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
     * 座席工号
     */
    private String cno;

    /**
     * 今日已完成会话数
     */
    private Integer today_completed_sessions;

    /**
     * 实时机器人会话数
     */
    private Integer real_time_bot_sessions;

    /**
     * 实时锁定会话数
     */
    private Integer real_time_locked_sessions;

    /**
     * 实时接待中会话数
     */
    private Integer real_time_reception_sessions;
} 