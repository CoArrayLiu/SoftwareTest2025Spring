package com.backend.vse.exception;

public class OperationException extends BusinessException {
    public OperationException(int code, String message) {
        super(code, message);
    }
}
