package com.tinet.pushtest.model;

//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableName;
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

    private LocalDateTime time;

    private String qno;

    private String app;

    private String cno;


    private int queue_abort;

    private int queue_duration;

    private int session_duration;

    private int session_complete;

    private int realtime_session_count;

    private int realtime_agent_session_count;

    private int realtime_session_queued_count;

    private int realtime_session_locked_count;

    private int realtime_session_bot_count;

}
