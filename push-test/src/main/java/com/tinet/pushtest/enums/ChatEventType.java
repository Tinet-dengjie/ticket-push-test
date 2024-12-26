package com.tinet.pushtest.enums;

public enum ChatEventType {
    SESSION_STARTED,      // 会话开始
    SESSION_QUEUED,       // 会话排队
    QUEUE_ABANDONED,      // 放弃排队
    SESSION_ASSIGNED,     // 分配座席
    SESSION_LOCKED,       // 会话锁定
    SESSION_UNLOCKED,     // 解除锁定
    ROBOT_MANAGED,        // 机器人托管
    ROBOT_CANCELLED,      // 取消机器人
    SESSION_COMPLETED     // 会话结束
} 