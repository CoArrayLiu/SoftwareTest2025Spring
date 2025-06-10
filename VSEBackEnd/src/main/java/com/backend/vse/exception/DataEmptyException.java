package com.backend.vse.exception;

import com.backend.vse.common.ResponseCode;
public class DataEmptyException extends BusinessException {
    public DataEmptyException(String message) {
        super(ResponseCode.BUSINESS_DATA_EMPTY, message);
    }
}