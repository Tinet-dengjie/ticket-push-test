package com.tinet.pushtest.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author dengjie
 * @since 2024/11/08
 */
@Data
@TableName("session_event_new")
public class ReceptionRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = com.baomidou.mybatisplus.annotation.IdType.AUTO)
    private Long id;

    private String qno;

    private String app;

    private String cno;

    private String event;

    @TableField("queue_abort")
    private Integer queue_abort;

    @TableField("queue_complete")
    private Integer queue_duration;

    @TableField("session_duration")
    private Integer session_duration;

    @TableField("session_complete")
    private Integer session_complete;

    @TableField("realtime_session_count")
    private Integer realtime_session_count;

    @TableField("realtime_session_queued_count")
    private Integer realtime_session_queued_count;
    @TableField("realtime_session_locked_count")
    private Integer realtime_session_locked_count;
    @TableField("realtime_session_assigned_count")
    private Integer realtime_session_assigned_count;
    @TableField("realtime_session_robot_count")
    private Integer realtime_session_robot_count;

}
