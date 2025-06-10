package com.backend.vse.interceptor;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.backend.vse.config.JwtProperties;
import com.backend.vse.exception.*;
import com.backend.vse.tools.Enums.PermissionType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.backend.vse.interceptor.util.JwtUtil;
import com.backend.vse.service.AuthorizationService;

/**
 * JWT认证拦截器

 * 功能说明：
 * 1. 开发模式直接放行（方便测试）
 * 2. 验证请求头中的Bearer Token
 * 3. 解析并验证JWT的有效性
 * 4. 基于注解的细粒度权限控制
 * 5. 用户身份信息线程绑定（ThreadLocal实现）
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);
    private static final String BEARER_PREFIX = "Bearer ";
    private static final Pattern USER_ID_PATTERN = Pattern.compile("^\\d+$");
    /**
     * 线程安全的用户ID存储容器
     * 使用场景：在本次请求处理周期内，任何地方都可以通过getLoginUser()获取当前登录用户ID
     */
    private static final ThreadLocal<Long> userIndex = new ThreadLocal<>();

    /**
     * 开发环境标志
     * true - 开发模式：跳过所有验证
     * false - 生产模式：执行完整验证流程
     */
    private final boolean devMode;

    /**
     * 权限验证服务（用于RBAC权限控制）
     */
    @Autowired
    private AuthorizationService permissionService;

    @Autowired
    public JwtInterceptor(JwtProperties jwtProperties) {
        this.devMode = jwtProperties.isDevMode();
    }

    /**
     * 请求预处理入口
     *
     * @return boolean 是否允许继续处理请求

     * 执行流程：
     * 1. 开发模式设置测试用户
     * 2. 验证Token格式和有效性
     * 3. 解析用户身份信息
     * 4. 执行权限检查（如果方法有@RequirePermission注解）
     */
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        try{
            // 开发环境直接放行（设置默认测试用户）
            if (handleDevMode()) {return true;}

            // 仅处理Controller方法级别的请求
            if (!(handler instanceof HandlerMethod)) {return true;}

            // 认证流程
            authenticateRequest(request,handler);
            return true;
        }catch (AuthException e) {
            handleAuthException(e, request, response);
            return false;
        }
    }

    private boolean handleDevMode() {
        if (this.devMode) {
            logger.debug("Dev mode activated, setting test user");
            setUserContext("9");
            return true;
        }
        return false;
    }

    private void authenticateRequest(@NotNull HttpServletRequest request, @NotNull Object handler) {
        String token = extractAndValidateToken(request);
        String userId = validateTokenAndGetUserId(token);
        setUserContext(userId);
        checkPermission((HandlerMethod) handler, Long.parseLong(userId));
    }

    // 获取并验证Authorization头格式
    private String extractAndValidateToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken == null) {
            throw new TokenMissingException();
        }
        if (!bearerToken.startsWith(BEARER_PREFIX)) {
            throw new InvalidTokenException();
        }
        return bearerToken.substring(BEARER_PREFIX.length());
    }

    // 提取并验证JWT有效性，解析用户ID并进行格式验证
    private String validateTokenAndGetUserId(String token) {
        if (!JwtUtil.checkSign(token)) {
            logger.warn("检测到无效token: {}", maskToken(token));
            throw new InvalidTokenException();
        }

        String userId = JwtUtil.getUserId(token);
        if (userId == null || !USER_ID_PATTERN.matcher(userId).matches()) {
            throw new InvalidUserIdentifierException();
        }
        return userId;
    }

    private String maskToken(String token) {
        if (token.length() < 10) return "*****";
        return token.substring(0, 5) + "..." + token.substring(token.length()-5);
    }

    private void setUserContext(String userId) {
        try {
            userIndex.set(Long.parseLong(userId));
        } catch (NumberFormatException e) {
            throw new InvalidUserIdentifierException();
        }
    }

    /**
     * 请求后清理线程数据
     */
    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) {
        userIndex.remove(); // 必须清除防止内存泄漏
    }

    /**
     * 获取当前登录用户ID
     *
     * @return 当前线程绑定的用户ID（可能为null）
     */
    public static Long getLoginUser() {
        return userIndex.get();
    }

    /**
     * 权限检查核心逻辑
     *
     * @param handlerMethod  被访问的Controller方法
     * @param userIndexValue 当前用户ID
     *                       执行流程：
     *                       1. 检查方法是否需要权限控制
     *                       2. 验证用户角色是否存在
     *                       3. 根据权限类型进行具体验证
     */
    private void checkPermission(HandlerMethod handlerMethod, Long userIndexValue) {
        // 获取方法上的权限注解
        RequirePermission requirePermission = handlerMethod.getMethodAnnotation(RequirePermission.class);
        if (requirePermission == null) {
            return; // 无需权限控制
        }

        // 验证用户角色是否存在
        String role = permissionService.getUserRole(userIndexValue);

        // 根据权限类型进行验证
        if (!checkSpecificPermission(userIndexValue, role, requirePermission)) {
            logger.warn("权限拒绝: user={} role={}",
                    userIndexValue, role);
            throw new PermissionDeniedException();
        }
    }

    /**
     * 具体权限类型检查
     *
     * @return 是否具有指定类型的权限
     */
    private boolean checkSpecificPermission(Long userIndexValue, String role, RequirePermission requirePermission) {
        // 根据权限类型选择验证路径
        return requirePermission.type() == PermissionType.TEACHING ?
                permissionService.checkUserPermission(userIndexValue, role, requirePermission.teaching()) :
                permissionService.checkUserPermission(userIndexValue, role, requirePermission.account());
    }

    /**
     * 统一错误响应方法
     */

    private void handleAuthException(AuthException e,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {
        logger.error("认证失败 [{}] {} - {}",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());

        response.setStatus(e.getCode());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(
                new ObjectMapper().writeValueAsString(
                        Map.of(
                                "code", e.getCode(),
                                "message", e.getMessage()
                        )
                )
        );
    }
}