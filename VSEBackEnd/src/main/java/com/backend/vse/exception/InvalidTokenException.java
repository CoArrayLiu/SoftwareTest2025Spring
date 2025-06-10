package com.backend.vse.exception;

import com.backend.vse.common.ResponseCode;

public class InvalidTokenException extends AuthException {
    public InvalidTokenException() {
        super(ResponseCode.AUTH_TOKEN_INVALID, "凭证无效或已过期");
    }
}