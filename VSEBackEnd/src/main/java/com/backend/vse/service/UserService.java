package com.backend.vse.service;

import com.backend.vse.entity.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface UserService {
    /**
     * 根据电子邮件查找用户
     * 该方法根据给定的电子邮件地址查找用户信息。
     *
     * @param email 用户的电子邮件地址
     * @return 返回找到的用户对象（User 类型）
     */
    User findUserByEmail(String email);

    /**
     * 根据用户索引查找用户
     * 该方法根据给定的用户索引查找用户信息。
     *
     * @param index 用户的索引
     * @return 返回找到的用户对象（User 类型）
     */
    User findUserByIndex(Long index);

    /**
     * 根据用户ID查找用户
     * 该方法根据给定的用户ID查找用户信息。
     *
     * @param id 用户的唯一标识符
     * @return 返回找到的用户对象（User 类型）
     */
    User findUserById(String id);

    /**
     * 根据用户ID集合查找用户ID列表
     * 该方法根据一组用户ID查找对应的用户ID列表。
     *
     * @param ids 用户ID集合
     * @return 返回包含用户ID的列表（List<String> 类型）
     */
    List<String> findUsersByIds(Collection<String> ids);

    /**
     * 根据用户ID集合查找用户列表
     * 该方法根据一组用户ID查找对应的用户信息列表。
     *
     * @param ids 用户ID集合
     * @return 返回包含用户信息的列表（List<User> 类型）
     */
    List<User> findUserListByIds(Collection<String> ids);

    /**
     * 激活用户账户
     * 该方法用于激活用户账户，通常在用户注册成功后，激活账户时使用。
     *
     * @param email    用户的电子邮件地址
     * @param password 用户的密码
     * @param status   用户账户的状态（通常 1 为激活，0 为未激活）
     */
    void activateUserAccount(String email, String password, Byte status);

    /**
     * 更新用户头像
     * 该方法用于更新用户的头像，通过提供头像的 URL 地址来更新。
     *
     * @param url 头像的 URL 地址
     * @param Id 用户的唯一标识符
     */
    void updateAvatar(String url, Long Id);

    /**
     * 更新用户密码
     * 该方法用于更新用户的密码。
     *
     * @param email    用户的电子邮件地址
     * @param password 新密码
     */
    void updatePassword(String email, String password);
}