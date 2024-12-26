package com.tinet.pushtest.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("chat_metric_queue")
public class ChatMetricQueue {
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
     * 队列号
     */
    private String qno;

    /**
     * 实时会话数
     */
    private Integer real_time_sessions;

    /**
     * 已完成会话数
     */
    private Integer completed_sessions;

    /**
     * 实时接待中会话数
     */
    private Integer real_time_reception_sessions;

    /**
     * 平均会话时长(秒)
     */
    private Integer average_session_duration;

    /**
     * 实时排队数
     */
    private Integer real_time_queue_count;

    /**
     * 平均排队接入时长(秒)
     */
    private Integer average_queue_access_duration;

    /**
     * 排队放弃数
     */
    private Integer queue_abandonment_count;

    /**
     * 签入座席数
     */
    private Integer signed_in_agents_count;
} 