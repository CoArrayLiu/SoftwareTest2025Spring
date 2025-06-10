package com.backend.vse.exception;
import com.backend.vse.common.ResponseCode;

public class FileDownloadException extends SystemException {
    public FileDownloadException(String message) {
        super(ResponseCode.SYSTEM_FILE_DOWNLOAD_ERROR, message);
    }

    public FileDownloadException(String message, Throwable cause) {
        super(ResponseCode.SYSTEM_FILE_DOWNLOAD_ERROR, message, cause);
    }
}
