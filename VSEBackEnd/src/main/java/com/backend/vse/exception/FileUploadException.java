package com.backend.vse.exception;

import com.backend.vse.common.ResponseCode;

public class FileUploadException extends SystemException {
    public FileUploadException(String message) {
        super(ResponseCode.SYSTEM_FILE_UPLOAD_ERROR, message);
    }

    public FileUploadException(String message, Throwable cause) {
        super(ResponseCode.SYSTEM_FILE_UPLOAD_ERROR, message, cause);
    }
}
