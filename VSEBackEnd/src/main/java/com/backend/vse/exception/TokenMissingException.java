package com.backend.vse.exception;

import com.backend.vse.common.ResponseCode;

public class TokenMissingException extends AuthException {
  public TokenMissingException() {
    super(ResponseCode.AUTH_TOKEN_MISSING, "未提供认证凭证");
  }
}
