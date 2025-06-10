package com.backend.vse.tools.Enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE("1"),
    INACTIVE("0"),
    DEACTIVATED("2");

    private final String code;

    UserStatus(String code) {
        this.code = code;
    }

    public static UserStatus fromCode(String code) {
        for (UserStatus status : UserStatus.values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("无效的状态类型: " + code);
    }
}

