package com.backend.vse.common;

public class ResponseCode {
    // 成功状态码
    public static final int SUCCESS = 200;

    // 通用错误码
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int INTERNAL_SERVER_ERROR = 500;

    // 业务错误码 (前缀BUSINESS_)
    public static final int BUSINESS_PARAM_ERROR = 1001;
    public static final int BUSINESS_DATA_NOT_FOUND = 1002;
    public static final int BUSINESS_EXCEL_PARSE_ERROR = 1003;
    public static final int BUSINESS_FILE_ERROR = 1004;
    public static final int BUSINESS_DATA_PROCESS_ERROR = 1005;
    public static final int BUSINESS_DATA_FORMAT_CONVERSION_ERROR = 1006;
    public static final int BUSINESS_OPERATION_FAILED = 1007;
    public static final int BUSINESS_DATA_EMPTY = 1008;
    public static final int BUSINESS_REPORT_GRADED = 1008;

    // 系统错误码 (前缀SYSTEM_)
    public static final int SYSTEM_DB_ERROR = 2001;
    public static final int SYSTEM_NETWORK_ERROR = 2002;
    public static final int SYSTEM_FILE_PROCESS_ERROR = 2003;
    public static final int SYSTEM_FILE_DOWNLOAD_ERROR = 2004;
    public static final int SYSTEM_FILE_UPLOAD_ERROR = 2004;
    public static final int SYSTEM_FILE_DELETE_ERROR = 2005;
    public static final int SYSTEM_MESSAGE_ERROR = 2006;
    public static final int SYSTEM_SEND_VERIFY_CODE_ERROR = 2007;

    // 认证授权错误 (前缀AUTH_)
    public static final int AUTH_TOKEN_MISSING      = 3000; // 未提供Token
    public static final int AUTH_TOKEN_INVALID      = 3001; // Token无效或过期
    public static final int AUTH_ROLE_NOT_FOUND     = 3002; // 用户角色未配置
    public static final int AUTH_USER_IDENTIFIER_INVALID     = 3003; // 用户标识解析失败
    public static final int AUTH_USER_NOT_ACTIVATION     = 3004; // 用户账号未激活
    public static final int AUTH_USER_PASSWORD_INVALID     = 3005; // 用户密码错误

    // 权限错误 (前缀PERMISSION_)
    public static final int PERMISSION_DENIED       = 4000; // 权限不足
    public static final int PERMISSION_RESOURCE_LOCKED = 4001; // 资源被锁定
}
