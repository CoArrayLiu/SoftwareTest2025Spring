package com.backend.vse.exception;

import lombok.Getter;

public class DatabaseOperationException extends SystemException {

    public DatabaseOperationException(int code,String message) {
        super(code, message);
    }

}
