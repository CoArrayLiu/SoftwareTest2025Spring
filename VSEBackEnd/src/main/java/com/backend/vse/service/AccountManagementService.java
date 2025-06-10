package com.backend.vse.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

/**
 * 用户服务接口
 */
public interface AccountManagementService {

    /**
     * 更新用户名
     *
     * @param userIndex 用户索引
     * @param newUsername 新用户名
     * @return 更新是否成功
     * @throws RuntimeException 当用户不存在时抛出
     */
    boolean updateUsername(Long userIndex, String newUsername) throws Exception;

    /**
     * 更新用户头像
     *
     * @param userIndex 用户索引
     * @param avatarFile 头像文件
     * @return 更新是否成功
     * @throws IOException 文件处理异常
     * @throws IllegalArgumentException 文件格式不正确时抛出
     * @throws RuntimeException 当用户不存在时抛出
     */
    String updateAvatar(Long userIndex, MultipartFile avatarFile) throws Exception;

    /**
     * 更新用户邮箱
     *
     * @param userIndex 用户索引
     * @param newEmail 新邮箱地址
     * @return 更新是否成功
     * @throws RuntimeException 当用户不存在时抛出
     */
    boolean updateEmail(Long userIndex, String newEmail) throws Exception;


    /**
     * 批量更新用户角色
     *
     * @param userIndices 用户索引列表
     * @param newRole 新角色
     * @return 更新是否成功
     * @throws IllegalAccessException 操作员权限不足时抛出
     * @throws RuntimeException 当用户不存在或更新失败时抛出
     */
    boolean batchUpdateRole(List<Long> userIndices, String newRole) throws Exception;

//    /**
//     * 批量注销用户
//     *
//     * @param operatorIndex 操作员索引
//     * @param userIndices 用户索引列表
//     * @return 注销是否成功
//     * @throws IllegalAccessException 操作员权限不足时抛出
//     * @throws RuntimeException 当用户不存在或删除失败时抛出
//     */
//    boolean batchDeleteUsers(Long operatorIndex, List<Long> userIndices) throws Exception;

    /**
     * 批量更新用户状态
     *
     * @param userIndices 用户索引列表
     * @param newStatus 新状态
     * @return 更新是否成功
     * @throws IllegalAccessException 操作员权限不足时抛出
     * @throws RuntimeException 当用户不存在或更新失败时抛出
     */
    boolean batchUpdateStatus(List<Long> userIndices, String newStatus) throws Exception;

    /**
     * 更新指定课程下所有用户的状态
     *
     * @param courseId 课程ID
     * @param newStatus 新状态
     * @return 更新是否成功
     * @throws IllegalAccessException 操作员权限不足时抛出
     * @throws RuntimeException 当课程下没有用户或更新失败时抛出
     */
    boolean updateUserStatusInCourse(Long courseId, String newStatus) throws Exception;
}