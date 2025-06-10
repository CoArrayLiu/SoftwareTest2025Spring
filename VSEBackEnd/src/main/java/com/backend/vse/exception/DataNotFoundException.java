package com.backend.vse.exception;

import com.backend.vse.common.ResponseCode;

public class DataNotFoundException extends BusinessException {
    public DataNotFoundException(String message) {
        super(ResponseCode.BUSINESS_DATA_NOT_FOUND, message);
    }
}
