package com.backend.vse.exception;
import com.backend.vse.common.ResponseCode;

public class FileGenerationException extends SystemException {
    public FileGenerationException(String message, Throwable cause) {
        super(ResponseCode.SYSTEM_FILE_PROCESS_ERROR, message, cause);
    }
}
