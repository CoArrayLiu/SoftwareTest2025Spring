package com.backend.vse.exception;

import com.backend.vse.common.ResponseCode;

public class ExcelParseException extends BusinessException {
    public ExcelParseException(String message) {
        super(ResponseCode.BUSINESS_EXCEL_PARSE_ERROR,message);
    }
}
