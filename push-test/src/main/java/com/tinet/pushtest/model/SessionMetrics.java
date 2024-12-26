package com.tinet.pushtest.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("session_metrics")
public class SessionMetrics {

    @TableId(value = "id", type=IdType.AUTO)
    private Integer id;
    private String qno;
    private String cno;
    private String app;
    private int realtime_session_count;           // 实时会话数
    private int realtime_session_queued_count;    // 实时排队数
    private int realtime_session_assigned_count;  // 实时分配数
    private int realtime_session_locked_count;    // 实时锁定数
    private int realtime_session_robot_count;     // 实时机器人托管数
} 