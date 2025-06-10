package com.backend.vse.service.impl;

import com.backend.vse.common.ResponseCode;
import com.backend.vse.exception.*;
import com.backend.vse.mapper.CourseMapper;
import com.backend.vse.mapper.UserMapper;
import com.backend.vse.service.AccountManagementService;
import com.backend.vse.service.CosService;
import com.backend.vse.tools.Enums.UserStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.backend.vse.tools.Enums.Roles;


@Service
@RequiredArgsConstructor // Lombok 自动生成构造函数
@Slf4j
public class AccountManagementServiceImpl implements AccountManagementService {
    private final UserMapper userMapper;
    private final CosService cosService;
    private final CourseMapper courseMapper;

    // 更新用户名
    @Override
    public boolean updateUsername(Long userIndex, String newUsername) {
        if (userMapper.updateUsername(userIndex, newUsername) <= 0) {
            throw new DatabaseOperationException(
                    ResponseCode.SYSTEM_DB_ERROR, "用户名更新失败"
            );
        }
        log.info("user with index: {} name is updated to {}", userIndex, newUsername);
        return true;
    }

    // 更新头像url
    @Override
    public String updateAvatar(Long userIndex, MultipartFile avatarFile) {
        String newAvatar = uploadAvatar(userIndex, avatarFile);
        if (userMapper.updateAvatar(userIndex, newAvatar) <= 0) {
            throw new DatabaseOperationException(
                    ResponseCode.SYSTEM_DB_ERROR, "更新用户头像失败"
            );
        }
        log.info("user with index: {} avatar is updated to {}", userIndex, newAvatar);
        return newAvatar;
    }

    private boolean checkEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        // 定义邮箱格式的正则表达式
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // 使用正则表达式匹配邮箱
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    // 更新邮箱
    @Override
    public boolean updateEmail(Long userIndex, String newEmail) {
        if (!checkEmail(newEmail)) {
            throw new InvalidParameterException("邮箱不合法");
        }
        if (userMapper.updateEmail(userIndex, newEmail) <= 0) {
            throw new DatabaseOperationException(
                    ResponseCode.SYSTEM_DB_ERROR, "更新用户邮箱失败"
            );
        }
        log.info("user with index: {} email is updated to {}", userIndex, newEmail);
        return true;
    }


    @Override
    @Transactional
    public boolean batchUpdateRole(List<Long> userIndices, String newRole) {
        try {
            log.info("set new role to {}", newRole);
            Roles.valueOf(newRole.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("无效的角色类型: {}", newRole);
            throw new InvalidParameterException("无效的角色类型: " + newRole);
        }
        try{
            if (userMapper.batchUpdateRole(userIndices, newRole.toLowerCase()) <= 0) {
                throw new DatabaseOperationException(
                        ResponseCode.SYSTEM_DB_ERROR, "批量更新用户角色失败"
                );
            }
        }catch (DataAccessException e) {
            throw handleDatabaseException(e, "批量更新用户角色");
        }

        return true;
    }

    // 批量注销用户
//    @Override
//    @Transactional
//    public boolean batchDeleteUsers(Long operatorIndex, List<Long> userIndices){
//        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
//        wrapper.in(User::getUserIndex, userIndices);
//        log.info("batch deleting operation");
//        if(userMapper.delete(wrapper)<=0){
//            throw new IllegalArgumentException("内部数据库错误");
//        }
//        return true;
//
//    }

    // 批量修改用户状态
    @Override
    @Transactional
    public boolean batchUpdateStatus(List<Long> userIndices, String newStatus) {
        byte status;
        try {
            UserStatus statusEnum = UserStatus.fromCode(newStatus);
            status = Byte.parseByte(statusEnum.getCode());
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException(e.getMessage());
        }
        try{
            if (userMapper.batchUpdateStatus(userIndices, status) <= 0) {
                throw new DatabaseOperationException(
                        ResponseCode.SYSTEM_DB_ERROR, "批量更新用户状态失败"
                );
            }
        }catch (DataAccessException e) {
            throw handleDatabaseException(e, "批量更新用户状态");
        }

        return true;
    }


    // 将某一课程下的所有老师和学生账号状态批量修改
    @Override
    @Transactional
    public boolean updateUserStatusInCourse(Long courseId, String newStatus) {
        boolean exist = courseMapper.existsByCourseId(courseId);
        if (!exist) {
            throw new DataNotFoundException("课程不存在");
        }
        byte status;
        try {
            // 将状态转换为枚举并验证
            UserStatus statusEnum = UserStatus.fromCode(newStatus);
            status = Byte.parseByte(statusEnum.getCode());
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException(e.getMessage());
        }
        try{
            if (userMapper.updateStatusByCourseId(courseId, status) <= 0) {
                throw new DatabaseOperationException(
                        ResponseCode.SYSTEM_DB_ERROR, "批量更新用户状态失败"
                );
            }
        }catch (DataAccessException e) {
            throw handleDatabaseException(e, "批量更新课程中用户状态");
        }

        return true;
    }

    private String uploadAvatar(Long userIndex, MultipartFile avatarFile) {
        Set<String> allowedImageTypes = Set.of(
                "image/jpeg",
                "image/png",
                "image/gif",
                "image/webp"
        );

        // 检查文件是否为空
        if (avatarFile == null || avatarFile.isEmpty()) {
            throw new DataEmptyException("上传文件不能为空");
        }

        // 检查文件类型
        String contentType = avatarFile.getContentType();
        if (contentType == null || !allowedImageTypes.contains(contentType.toLowerCase())) {
            throw new InvalidParameterException("不支持的文件类型，仅支持: JPG, PNG, GIF, WEBP");
        }

        // 检查文件大小（例如：限制为2MB）
        long maxSize = 2 * 1024 * 1024; // 2MB in bytes
        if (avatarFile.getSize() > maxSize) {
            throw new InvalidParameterException("文件大小超过限制，最大允许2MB");
        }

        // 检查文件名长度
        String originalFilename = avatarFile.getOriginalFilename();
        if (originalFilename != null) {
            if (originalFilename.length() > 100) {
                throw new InvalidParameterException("文件名过长，最大允许100个字符");
            }
            // 添加检查中文字符的逻辑
            if (containsChineseCharacters(originalFilename)) {
                throw new InvalidParameterException("文件名不能包含中文字符");
            }
        }

        log.info("开始上传头像，文件类型: {}, 大小: {} bytes", contentType, avatarFile.getSize());

        // 获取用户当前头像
        String oldAvatar = userMapper.getUserAvatar(userIndex);

        // 删除旧头像
        if (oldAvatar != null && !oldAvatar.isEmpty()) {
            try {
                cosService.deleteFile(oldAvatar);
                log.info("成功删除旧头像: {}", oldAvatar);
            } catch (Exception e) {
                log.warn("删除旧头像失败: {}", e.getMessage());
                throw new SystemException(ResponseCode.SYSTEM_FILE_DELETE_ERROR, "删除旧头像失败");
            }
        }

        // 上传新头像
        String newAvatarUrl = cosService.uploadFile(avatarFile);
        if (newAvatarUrl == null || newAvatarUrl.isEmpty()) {
            throw new FileUploadException("头像上传失败");
        }

        log.info("头像上传成功，新URL: {}", newAvatarUrl);
        return newAvatarUrl;
    }

    private boolean containsChineseCharacters(String str) {
        return str.codePoints().anyMatch(codepoint ->
                Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.HAN);
    }

    private DatabaseOperationException handleDatabaseException(
            DataAccessException e, String operation
    ) {
        log.error("数据库操作异常[{}] {}",
                operation,
                e.getMessage());

        return new DatabaseOperationException(
                ResponseCode.SYSTEM_DB_ERROR,
                operation + "失败"
        );
    }
}