package com.tinet.pushtest.model;

public enum SessionState {
    STARTED,            // 会话开始
    QUEUED,            // 进入队列
    QUEUE_ABANDONED,   // 放弃排队
    ASSIGNED,          // 分配到座席
    LOCKED,            // 会话锁定
    UNLOCKED,          // 解除锁定
    ROBOT_MANAGED,     // 托管到机器人
    ROBOT_CANCELLED,   // 取消机器人托管
    COMPLETED          // 会话完成
} 