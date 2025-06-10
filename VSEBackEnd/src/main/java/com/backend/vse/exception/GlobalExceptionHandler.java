// GlobalExceptionHandler.java
package com.backend.vse.exception;

import com.backend.vse.common.ResponseCode;
import com.backend.vse.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //------------------- 参数自动校验异常 -------------------
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public Result<?> handleValidationException(Exception e) {
        String errorMsg = extractValidationErrorMessage(e);
        log.warn("参数校验失败: {}", errorMsg);
        return Result.fail(ResponseCode.BUSINESS_PARAM_ERROR, errorMsg);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> handleJSR380Exception(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining("; "));

        return Result.fail(ResponseCode.BUSINESS_PARAM_ERROR, message);
    }

    //------------------- 参数业务校验异常 -------------------
    @ExceptionHandler(InvalidParameterException.class)
    public Result<?> handleInvalidParameterException(
            InvalidParameterException e,
            HttpServletRequest request
    ) {
        log.warn("参数异常 [{}] {} - {}",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());
        return Result.fail(ResponseCode.BUSINESS_PARAM_ERROR, e.getMessage());
    }

    //------------------- 用户操作异常 -------------------
    @ExceptionHandler(OperationException.class)
    public Result<?> handleStudentOperationError(OperationException e) {
        log.warn("操作异常: {}", e.getMessage());
        return Result.fail(e.getCode(), e.getMessage());
    }

    //------------------- 业务级异常 -------------------
    @ExceptionHandler({
            BusinessException.class,  // 基础业务异常
            ExcelParseException.class, // Excel解析异常
            DataNotFoundException.class // 数据不存在异常
    })
    public Result<?> handleBusinessExceptions(
            BusinessException e,
            HttpServletRequest request
    ) {
        logBusinessException(e, request);
        return Result.fail(e.getCode(), e.getMessage());
    }

    //------------------- 系统级异常 -------------------
    @ExceptionHandler({
            SystemException.class,     // 基础系统异常
            DatabaseOperationException.class // 数据库操作异常
    })
    public Result<?> handleSystemExceptions(
            SystemException e,
            HttpServletRequest request
    ) {
        logSystemException(e, request);
        return Result.fail(e.getCode(), e.getMessage());
    }

    //------------------- 数据格式异常 -------------------
    @ExceptionHandler({
            HttpMessageNotReadableException.class,  // JSON解析错误
            IllegalArgumentException.class         // 非法参数
    })
    public Result<?> handleDataFormatExceptions(Exception e) {
        log.warn("数据格式异常: {}", e.getMessage());
        return Result.fail(ResponseCode.BAD_REQUEST, e.getMessage());
    }

    //------------------- 文件操作异常 -------------------
    @ExceptionHandler(MultipartException.class)
    public Result<?> handleFileUploadExceptions(MultipartException e) {
        log.error("文件上传异常: {}", e.getMessage());
        return Result.fail(ResponseCode.BUSINESS_FILE_ERROR, e.getMessage());
    }

    @ExceptionHandler(FileGenerationException.class)
    public Result<?> handleFileGenerationError(FileGenerationException e) {
        log.error("文件生成失败: {}", e.getMessage(), e);
        return Result.fail(e.getCode(), e.getMessage());
    }

    // ------------------- 认证处理异常 -------------------
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Result<?>> handleAuthException(AuthException e) {
        log.warn("认证异常: code={}, msg={}", e.getCode(), e.getMessage());
        return ResponseEntity
                .status(getHttpStatus(e.getCode()))
                .body(Result.fail(e.getCode(), e.getMessage()));
    }

    // 根据错误码映射HTTP状态
    private HttpStatus getHttpStatus(int code) {
        return switch (code) {
            // 4xx 错误
            case ResponseCode.BUSINESS_PARAM_ERROR -> HttpStatus.BAD_REQUEST;
            case ResponseCode.AUTH_TOKEN_MISSING,
                 ResponseCode.AUTH_TOKEN_INVALID -> HttpStatus.UNAUTHORIZED;
            case ResponseCode.PERMISSION_DENIED -> HttpStatus.FORBIDDEN;
            // 5xx 错误
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    //------------------- 全局兜底异常处理 -------------------
    @ExceptionHandler(Exception.class)
    public Result<?> handleUnknownException(
            Exception e,
            HttpServletRequest request
    ) {
        logGlobalException(e, request);
        return Result.fail(
                ResponseCode.INTERNAL_SERVER_ERROR,
                "系统内部错误"
        );
    }

    //------------------- 私有工具方法 -------------------
    private String extractValidationErrorMessage(Exception e) {
        BindingResult result = null;
        if (e instanceof MethodArgumentNotValidException) {
            result = ((MethodArgumentNotValidException) e).getBindingResult();
        } else if (e instanceof BindException) {
            result = ((BindException) e).getBindingResult();
        }

        return result != null ?
                result.getFieldErrors().stream()
                        .map(f -> f.getField() + ": " + f.getDefaultMessage())
                        .collect(Collectors.joining("; "))
                : "参数校验失败";
    }

    private void logBusinessException(BusinessException e, HttpServletRequest request) {
        String logMsg = String.format("业务异常 [%s] %s -> %s",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());

        if (e instanceof DataNotFoundException) {
            log.warn(logMsg);
        } else {
            log.error(logMsg, e);
        }
    }

    private void logSystemException(SystemException e, HttpServletRequest request) {
        String logMsg = String.format("系统异常 [%s] %s -> %s",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());

        if (e instanceof DatabaseOperationException) {
            log.error("{}", logMsg);
        } else {
            log.error(logMsg, e);
        }
    }

    private void logGlobalException(Exception e, HttpServletRequest request) {
        String logTemplate = "未处理异常 [%s] %s - IP: %s";
        String clientIP = request.getRemoteAddr();

        log.error(String.format(logTemplate,
                request.getMethod(),
                request.getRequestURI(),
                clientIP), e);
    }
}