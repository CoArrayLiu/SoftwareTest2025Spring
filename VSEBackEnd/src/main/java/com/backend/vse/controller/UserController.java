package com.backend.vse.controller;

import com.backend.vse.common.ResponseCode;
import com.backend.vse.common.Result;
import com.backend.vse.dto.*;
import com.backend.vse.entity.User;
import com.backend.vse.exception.DataNotFoundException;
import com.backend.vse.exception.InvalidParameterException;
import com.backend.vse.interceptor.JwtInterceptor;
import com.backend.vse.interceptor.RequirePermission;
import com.backend.vse.interceptor.util.JwtUtil;
import com.backend.vse.service.CosService;
import com.backend.vse.service.impl.AccountManagementServiceImpl;
import com.backend.vse.service.impl.EmailVerificationServiceImpl;
import com.backend.vse.service.impl.UserServiceImpl;
import com.backend.vse.tools.AccountUtil;
import com.backend.vse.tools.Enums.PermissionType;
import com.backend.vse.tools.Enums.UserAndAccountActivity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;

import java.util.*;

@Tag(name = "User", description = "用户管理接口")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor // Lombok 自动生成构造函数
@Slf4j
public class UserController {
    // 自动注入各类服务和Mapper
    private final EmailVerificationServiceImpl emailVerificationService;
    private final UserServiceImpl userService;
    private final AccountManagementServiceImpl accountManagementService;
    private final CosService cosService;

    /**
     * 发送账户激活邮件
     * 发送包含验证码的激活邮件到用户邮箱，用于账户激活
     * 路径：/auth/login
     * @param email 用户邮箱地址
     * @return 返回发送结果
     */
    private Result<Map<String, Object>> sendActivationEmail(String email)
    {
        boolean codeSent = emailVerificationService.sendVerificationCode(email);

        Map<String, Object> response = new HashMap<>();
        if (codeSent) {
            log.info("验证码发送成功，邮箱：{}", email);
            response.put("message", "账户需要激活，验证码已发送至邮箱，请三分钟后重试");
            response.put("email", email);
            return Result.success(response); // 使用状态码1表示需要验证码激活
        } else {
            log.warn("验证码发送频繁，邮箱：{}", email);
            return Result.fail(ResponseCode.INTERNAL_SERVER_ERROR, "请勿频繁发送验证码，请稍后再试"); // 使用状态码3表示发送频繁
        }
    }

    /**
     * 用户登录
     * 根据用户提供的账号和密码进行登录验证，并返回登录结果或错误信息
     * 路径：/auth/login
     *
     * @param loginDto 用户登录数据传输对象
     * @return 返回登录结果和token
     */
    @Operation(summary = "根据用户的id和密码返回指定用户（登录）")
    @PostMapping("/auth/login")
    public Result<Map<String, Object>> login(
            @RequestBody @NotNull LoginDto loginDto)
    {
        log.info("进入用户登录");
        // 验证账号格式
        if (!AccountUtil.isValidAccount(loginDto.getAccount())) {
            log.info("{} 账号格式不正确", loginDto.getAccount());
            return Result.fail(ResponseCode.BUSINESS_PARAM_ERROR, "账号格式不正确");
        }

        // 获取账号类型
        AccountUtil.AccountType accountType = AccountUtil.getAccountType(loginDto.getAccount());

        // 根据账号类型进行不同的处理
        User user = Optional.ofNullable(
                accountType == AccountUtil.AccountType.EMAIL
                        ? userService.findUserByEmail(loginDto.getAccount())
                        : userService.findUserById(loginDto.getAccount())
        ).orElseThrow(() -> {
            log.info("{}账号不存在", loginDto.getAccount());
            return new DataNotFoundException("账号不存在");
        });

        // 验证密码
        if (!Objects.equals(user.getPassword(), loginDto.getPassword())) {
            log.warn("{}密码错误", loginDto.getAccount());
            return Result.fail(ResponseCode.AUTH_USER_PASSWORD_INVALID, "密码错误");
        }

        if (user.getStatus() == 1) { // 用户账号已激活，成功登录，返回token
            //查找用户user_index(数据库id)
            String userIndex = user.getUserIndex().toString();
            //根据用户index获取token
            String token = JwtUtil.sign(userIndex);
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("token", token);
            return Result.success(hashMap);
        }
        // 未激活用户需要进行邮箱验证
        return handleInactiveUser(user);
    }

    /**
     * 处理未激活的用户
     * 检查用户是否有邮箱，如果有邮箱则发送激活验证码；否则提示用户输入邮箱
     * 路径：/auth/activations
     *
     * @param user 用户对象
     * @return 返回需要的结果，包括是否需要输入邮箱
     */
    private Result<Map<String, Object>> handleInactiveUser(User user) {
        // 检查用户是否有邮箱
        String email = user.getEmail();
        if (email == null) {
            log.info("账户未激活且无邮箱信息，请输入邮箱进行激活");
            Map<String, Object> response = new HashMap<>();
            response.put("needEmail", true);
            response.put("studentId", user.getId());
            response.put("message", "账户未激活且无邮箱信息，请输入邮箱进行激活");
            return Result.fail(ResponseCode.AUTH_USER_NOT_ACTIVATION, response.toString()); // 使用特定的状态码2表示需要输入邮箱
        }

        // 有邮箱的情况下发送验证码
        return sendActivationEmail(user.getEmail());
    }

    /**
     * 用户账户激活或用于邮箱修改密码
     * 根据验证码激活账户或者修改密码
     * 路径：/auth/activations
     *
     * @param activateDto 激活数据传输对象
     * @return 返回激活结果
     */
    @Operation(summary = "用户账户激活或用于邮箱修改密码")
    @PostMapping("/activations")
    public Result<String> accountActivate(
            @RequestBody @NotNull ActivateDto activateDto)
    {
        if (!emailVerificationService.verifyCode(activateDto.getEmail(), activateDto.getCode())) {
            throw new InvalidParameterException("验证码错误，请重试");
        }

        log.info("用户进行账户激活");
        userService.activateUserAccount(activateDto.getEmail(), activateDto.getPassword(), (byte) 1);

        log.info("账户激活成功");
        return Result.success("账户激活成功");
    }

    /**
     * 用户已知旧密码修改密码
     * 根据用户提供的旧密码和新密码，修改用户的密码
     * 请求方式：PUT
     * 路径：/me/password
     *
     * @param changePasswordDto 包含旧密码和新密码的数据传输对象
     * @return 返回密码修改结果
     */
    @Operation(summary = "用户已知旧密码修改密码")
    @PutMapping("/me/password")
    public Result<String> changePassword(
            @RequestBody @NotNull ChangePasswordDto changePasswordDto)
    {
        Long index = JwtInterceptor.getLoginUser();
        log.info("用户尝试修改密码，用户ID：{}", index);

        User user = Optional.ofNullable(userService.findUserByIndex(index))
                .orElseThrow(() -> new DataNotFoundException("修改密码失败，用户不存在或登录失效"));

        String queryPassword = user.getPassword();
        if (!queryPassword.equals(changePasswordDto.getOldPassword())) {
            log.warn("用户输入旧密码错误，用户ID：{}", index);
            return Result.fail(ResponseCode.AUTH_USER_PASSWORD_INVALID, "密码有误");
        }

        userService.updatePassword(user.getEmail(), changePasswordDto.getNewPassword());
        log.info("用户修改密码成功，用户ID：{}", index);
        return Result.success("用户修改密码成功");
    }

    /**
     * 发送忘记密码验证码
     * 根据用户邮箱发送验证码，用于找回密码
     * 请求方式：POST
     * 路径：/verification-codes
     *
     * @param email 用户邮箱地址
     * @return 返回验证码发送结果
     */
    @Operation(summary = "发送忘记密码验证码")
    @PostMapping("/verification-codes")
    public Result<Map<String, Object>> sendVerificationCode(
            @Parameter(description = "用户邮箱", required = true)
            @RequestParam("email") @Length(min = 5) @NotNull String email
    ) {
        log.info("发送忘记密码验证码请求，邮箱：{}", email);
        // 验证用户是否存在
        userService.findUserByEmail(email);

        // 生成并发送验证码
        boolean codeSent = emailVerificationService.sendVerificationCode(email);

        Map<String, Object> response = new HashMap<>();
        if (codeSent) {
            log.info("验证码发送成功，邮箱：{}", email);
            response.put("message", "验证码已发送至邮箱，请三分钟后重试");
            response.put("email", email);
            return Result.success(response); // 使用状态码1表示需要验证码进行修改
        } else {
            log.warn("验证码发送频繁，邮箱：{}", email);
            return Result.fail(ResponseCode.INTERNAL_SERVER_ERROR, "请勿频繁发送验证码，请稍后再试"); // 使用状态码3表示发送频繁
        }
    }

    /**
     * 获取用户信息（登录之后根据token）
     * 根据用户的token获取用户的详细信息
     * 请求方式：GET
     * 路径：/me
     *
     * @return 返回用户信息数据
     */
    @Operation(summary = "获取用户信息（登录之后根据token）")
    @GetMapping("/me")
    public Result<UserInfoDto> getUserInfo() {
        // 从拦截器中获取用户index
        Long index = JwtInterceptor.getLoginUser();
        log.info("获取当前用户信息，请求用户ID：{}", index);

        User user = userService.findUserByIndex(index);

        UserInfoDto userInfo = new UserInfoDto(user.getUserIndex(),
                user.getName(), user.getEmail(), user.getAvatar(), user.getRole());

        log.info("用户信息获取成功，用户ID：{}", index);
        return Result.success(userInfo);
    }

    /**
     * 根据URL获取用户头像
     * 根据用户头像的URL获取头像图片
     * 请求方式：GET
     * 路径：/me/avatar
     *
     * @param avatarUrl 用户头像的URL
     * @return 返回用户头像图片
     */
    @Operation(summary = "获取用户头像", description = "根据用户ID获取头像图片")
    @GetMapping("/me/avatar")
    public ResponseEntity<Object> downloadAvatar(
            @Parameter(description = "用户头像url", required = true)
            @RequestParam("avatarUrl") @Length @NotNull String avatarUrl) {
        log.info("请求用户头像，URL：{}", avatarUrl);

        try {
            // 获取文件元数据
            FileMetadataResponseDto metadata = cosService.getFileMetadataByUrl(avatarUrl);

            // 获取文件内容
            InputStream fileContent = metadata.getFileContent();
            byte[] content = fileContent.readAllBytes();

            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(metadata.getContentType()));
            headers.setContentLength(metadata.getContentLength());
            headers.set("Content-Disposition", "inline; filename=\"" +
                    avatarUrl.substring(avatarUrl.lastIndexOf("/") + 1) + "\"");

            log.info("用户头像获取成功，URL：{}", avatarUrl);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(content);
        } catch (IOException e) {
            log.error("获取用户头像失败，URL：{}，异常：{}", avatarUrl, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Result.fail(ResponseCode.SYSTEM_FILE_DOWNLOAD_ERROR, "获取用户头像失败"));
        }
    }

    /**
     * 获取测试Token
     * 根据用户index生成一个测试用的Token
     * 请求方式：GET
     * 路径：/token
     *
     * @param index 用户index
     * @return 返回生成的Token
     */
    @GetMapping("/token")
    public Result<String> getTestToken(@RequestParam("index") @Min(0) String index) {
        log.info("请求生成测试Token，用户index：{}", index);
        final String token = JwtUtil.sign(index);
        if (token != null) {
            log.info("测试Token生成成功，用户index：{}", index);
            return Result.success(token);
        } else {
            log.error("测试Token生成失败，用户index：{}", index);
            return Result.fail(ResponseCode.INTERNAL_SERVER_ERROR, "Token生成失败");
        }
    }

    /**
     * 更新用户名
     * 根据用户输入的新用户名更新用户的用户名
     * 请求方式：PUT
     * 路径：/me/username
     *
     * @param newUsername 新用户名
     * @return 返回用户名更新结果
     */
    @Operation(summary = "更新用户名")
    @PutMapping("/me/username")
    public Result<Boolean> updateUsername(
            @Parameter(description = "用户新用户名", required = true)
            @RequestParam @Length @NotNull String newUsername) {
        log.info("更新用户名请求，新用户名：{}", newUsername);

        // 参数校验
        if (newUsername.trim().isEmpty()) {
            log.warn("用户名不能为空");
            return Result.fail(ResponseCode.BUSINESS_PARAM_ERROR, "用户名不能为空");
        }
        Long index = JwtInterceptor.getLoginUser();
        boolean success = accountManagementService.updateUsername(index, newUsername);
        log.info("用户名更新成功，用户ID：{}，新用户名：{}", index, newUsername);
        return Result.success(success);
    }

    /**
     * 更新用户头像
     * 根据用户上传的新头像更新用户的头像
     * 请求方式：PUT
     * 路径：/me/avatar
     *
     * @param avatarFile 新头像文件
     * @return 返回头像更新结果
     */
    @Operation(summary = "更新用户头像")
    @PutMapping("/me/avatar")
    public Result<String> updateAvatar(
            @RequestParam("file") @NotNull MultipartFile avatarFile)
    {
        log.info("更新用户头像请求，文件：{}", avatarFile.getOriginalFilename());

        // 参数校验
        if (avatarFile.isEmpty()) {
            log.warn("头像文件不能为空");
            return Result.fail(ResponseCode.BUSINESS_DATA_EMPTY, "头像文件不能为空");
        }

        Long index = JwtInterceptor.getLoginUser();
        String pictureUrl = accountManagementService.updateAvatar(index, avatarFile);
        log.info("用户头像更新成功，用户ID：{}，新头像URL：{}", index, pictureUrl);
        return Result.success(pictureUrl);
    }

    /**
     * 更新用户邮箱
     * 根据用户输入的新邮箱更新用户的邮箱
     * 请求方式：PUT
     * 路径：/me/email
     *
     * @param newEmail 新邮箱
     * @return 返回邮箱更新结果
     */
    @Operation(summary = "更新用户邮箱")
    @PutMapping("/me/email")
    public Result<Boolean> updateEmail(
            @Parameter(description = "用户新邮箱", required = true)
            @RequestParam @Length(min = 5) @NotNull String newEmail) {
        log.info("更新用户邮箱请求，新邮箱：{}", newEmail);

        // 参数校验
        if (newEmail.trim().isEmpty()) {
            return Result.fail(ResponseCode.BUSINESS_DATA_EMPTY, "邮箱不能为空");
        }
        Long index = JwtInterceptor.getLoginUser();
        boolean success = accountManagementService.updateEmail(index, newEmail);
        log.info("用户邮箱更新成功，用户ID：{}，新邮箱：{}", index, newEmail);
        return Result.success(success);
    }

    /**
     * 批量更新用户角色
     * 根据传入的用户ID列表批量更新用户角色
     * 请求方式：PUT
     * 路径：/role
     *
     * @param request 包含用户ID列表和新角色的请求数据
     * @return 返回批量更新用户角色结果
     */
    @Operation(summary = "批量更新用户角色")
    @PutMapping("/role")
    @RequirePermission(type = PermissionType.ACCOUNT, account = UserAndAccountActivity.ASSIGN_ACCOUNT_PERMISSION)
    public Result<Boolean> batchUpdateRole(
            @RequestBody @NotNull Map<String, Object> request)
    {
        log.info("批量更新用户角色请求，参数：{}", request);

        @SuppressWarnings("unchecked")
        List<Long> userIds = (List<Long>) request.get("userIds");
        String newRole = (String) request.get("newRole");

        if (userIds == null || userIds.isEmpty()) {
            log.warn("用户ID列表不能为空");
            return Result.fail(ResponseCode.BUSINESS_DATA_EMPTY, "用户ID列表不能为空");
        }
        if (newRole == null || newRole.trim().isEmpty()) {
            log.warn("新角色不能为空");
            return Result.fail(ResponseCode.BUSINESS_DATA_EMPTY, "新角色不能为空");
        }

        boolean success = accountManagementService.batchUpdateRole(userIds, newRole);
        log.info("批量更新用户角色成功，更新的角色：{}", newRole);
        return Result.success(success);
    }

//    @Operation(summary = "批量注销用户")
//    @RequirePermission(type = PermissionType.ACCOUNT, account = UserAndAccountActivity.DELETE_ACCOUNT)
//    @DeleteMapping("/batch")
//    public Result<Boolean> batchDeleteUsers(@RequestBody Map<String, Object> request) {
//        try {
//
//            Long index = JwtInterceptor.getLoginUser();
//            String operatorId = (String) request.get("operatorId");
//            @SuppressWarnings("unchecked")
//            List<Long> userIds = (List<Long>) request.get("userIds");
//
//            if (userIds == null || userIds.isEmpty()) {
//                return Result.fail(400, "用户ID列表不能为空");
//            }
//
//            boolean success = userService.batchDeleteUsers(index, userIds);
//            return Result.success(success);
//        } catch (IllegalArgumentException e) {
//            return Result.fail(403, e.getMessage());
//        } catch (Exception e) {
//            return Result.fail(500, "批量注销用户失败: 内部服务器错误");
//        }
//    }

    /**
     * 批量更新用户状态
     * 根据传入的用户ID列表批量更新用户状态
     * 请求方式：PUT
     * 路径：/status
     *
     * @param request 包含用户ID列表和新状态的请求数据
     * @return 返回批量更新用户状态的结果
     */
    @Operation(summary = "批量更新用户状态")
    @RequirePermission(type = PermissionType.ACCOUNT, account = UserAndAccountActivity.ACTIVATE_ACCOUNT)
    @PutMapping("/status")
    public Result<Boolean> batchUpdateStatus(
            @RequestBody @NotNull Map<String, Object> request)
    {
        log.info("批量更新用户状态请求，参数：{}", request);

        @SuppressWarnings("unchecked")
        List<Long> userIds = (List<Long>) request.get("userIds");
        String newStatus = (String) request.get("newStatus");

        if (userIds == null || userIds.isEmpty()) {
            log.warn("用户ID列表不能为空");
            return Result.fail(ResponseCode.BUSINESS_DATA_EMPTY, "用户ID列表不能为空");
        }
        if (newStatus == null || newStatus.trim().isEmpty()) {
            log.warn("新状态不能为空");
            return Result.fail(ResponseCode.BUSINESS_DATA_EMPTY, "新状态不能为空");
        }

        boolean success = accountManagementService.batchUpdateStatus(userIds, newStatus);
        log.info("批量更新用户状态成功，更新的状态：{}", newStatus);
        return Result.success(success);
    }

    /**
     * 更新课程用户状态
     * 根据传入的课程ID和新状态更新指定课程的用户状态
     * 请求方式：PUT
     * 路径：/status/courses/{courseId}
     *
     * @param courseId 课程ID
     * @param status   新的用户状态
     * @return 返回更新课程用户状态的结果
     */
    @Operation(summary = "更新课程用户状态")
    @RequirePermission(type = PermissionType.ACCOUNT, account = UserAndAccountActivity.ACTIVATE_ACCOUNT)
    @PutMapping("/status/courses/{courseId}")
    public Result<Boolean> updateCourseUsersStatus(
            @Parameter(description = "课程ID", required = true, example = "42041301")
            @PathVariable("courseId") @Min(1) @NotNull Long courseId,
            @Parameter(description = "状态", required = true)
            @RequestParam @Length(max = 1) @NotNull String status
    ) {
        log.info("更新课程用户状态，参数：{}", courseId);
        boolean success = accountManagementService.updateUserStatusInCourse(courseId, status);
        return Result.success(success);
    }
}