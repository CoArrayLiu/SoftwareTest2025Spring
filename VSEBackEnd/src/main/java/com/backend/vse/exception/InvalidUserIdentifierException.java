package com.backend.vse.exception;

import com.backend.vse.common.ResponseCode;

public class InvalidUserIdentifierException extends AuthException {
    public InvalidUserIdentifierException() {
      super(ResponseCode.AUTH_USER_IDENTIFIER_INVALID, "用户标识解析失败");
    }
}
