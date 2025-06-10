package com.backend.vse.exception;

import com.backend.vse.common.ResponseCode;

public class DataProcessingException extends BusinessException {
    public DataProcessingException(String message) {
        super(ResponseCode.BUSINESS_DATA_PROCESS_ERROR, message);
    }
}
