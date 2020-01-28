package com.sxy.community.enums;

/**
 * 通知的状态枚举
 */
public enum NotificationStatusEnum {
    NO_READ(1),READ(0)
    ;
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
