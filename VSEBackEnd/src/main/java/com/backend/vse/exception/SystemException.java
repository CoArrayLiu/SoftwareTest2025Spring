package com.backend.vse.exception;

import lombok.Getter;

@Getter
public class SystemException extends RuntimeException {
    private final int code;

    public SystemException(int code, String message) {
        super(message);
        this.code = code;
    }

    public SystemException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
