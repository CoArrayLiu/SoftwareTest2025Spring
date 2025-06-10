package com.backend.vse.exception;

import com.backend.vse.common.ResponseCode;

public class InvalidParameterException extends BusinessException {
    public InvalidParameterException(String message) {
        super(ResponseCode.BUSINESS_PARAM_ERROR , message);
    }
}
