package com.backend.vse.exception;

import com.backend.vse.common.ResponseCode;
import lombok.Getter;

import java.io.Serial;

/**
 * 业务异常类
 */
@Getter
public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private final int code; // 错误码

    /**
     * 构造函数
     * @param message 错误消息
     */
    public BusinessException(String message) {
        super(message);
        this.code = ResponseCode.INTERNAL_SERVER_ERROR; // 默认业务异常码
    }

    /**
     * 构造函数
     * @param code 错误码
     * @param message 错误消息
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }
}