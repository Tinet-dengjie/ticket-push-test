package com.tinet.pushtest.model;

import com.tinet.pushtest.enums.ChatEventType;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class ChatEvent {
    private String sessionId;
    private Integer enterpriseId;
    private String qno;
    private String cno;
    private String appId;
    private ChatEventType eventType;
    private Long timestamp;
    private Integer duration;  // 用于记录排队时长或会话时长
} 