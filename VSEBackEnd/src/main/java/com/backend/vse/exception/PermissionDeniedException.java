package com.backend.vse.exception;

import com.backend.vse.common.ResponseCode;

public class PermissionDeniedException extends AuthException {
    public PermissionDeniedException() {
        super(ResponseCode.PERMISSION_DENIED, "操作被拒绝：权限不足");
    }
}
