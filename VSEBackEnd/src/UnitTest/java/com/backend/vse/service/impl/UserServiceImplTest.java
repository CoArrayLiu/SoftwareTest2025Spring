package com.backend.vse.service.impl;

import com.backend.vse.entity.User;
import com.backend.vse.exception.DataNotFoundException;
import com.backend.vse.exception.DatabaseOperationException;
import com.backend.vse.mapper.UserMapper;
import com.backend.vse.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // 初始化测试数据
        testUser = new User();
        testUser.setUserIndex(71L);
        testUser.setEmail("2252073@tongji.edu.cn");
        testUser.setId("2252073");
    }

    @Test
    public void testFindUserByEmail_UserFound() {
        // 模拟 userMapper.selectOne 方法
        when(userMapper.selectOne(any())).thenReturn(testUser);

        // 调用 service 方法
        User result = userService.findUserByEmail("test@example.com");

        // 验证结果
        assertNotNull(result);
        assertEquals("2252073@tongji.edu.cn", result.getEmail());

        // 验证 mapper 方法是否被调用
        verify(userMapper, times(1)).selectOne(any());
    }

    @Test
    public void testFindUserByEmail_UserNotFound() {
        // 模拟 userMapper.selectOne 返回 null
        when(userMapper.selectOne(any())).thenReturn(null);

        // 调用 service 方法并验证异常
        assertThrows(DataNotFoundException.class, () -> userService.findUserByEmail("nonexistent@example.com"));
    }

    @Test
    public void testActivateUserAccount_Success() {
        // 模拟数据库更新成功，返回大于 0 的值
        when(userMapper.updatePassword(any(), any())).thenReturn(1);
        when(userMapper.updateStatus(any(), any())).thenReturn(1);

        // 执行服务方法
        userService.activateUserAccount("test@example.com", "newPassword", (byte) 1);

        // 验证是否调用了更新方法
        verify(userMapper, times(1)).updatePassword(any(), any());
        verify(userMapper, times(1)).updateStatus(any(), any());
    }

    @Test
    public void testActivateUserAccount_Failure() {
        // 模拟数据库更新失败，返回 0
        when(userMapper.updatePassword(any(), any())).thenReturn(0);
        when(userMapper.updateStatus(any(), any())).thenReturn(0);

        // 执行服务方法并验证异常
        assertThrows(DatabaseOperationException.class, () -> userService.activateUserAccount("test@example.com", "newPassword", (byte) 1));
    }

    @Test
    public void testUpdatePassword_Success() {
        // 模拟密码更新成功
        when(userMapper.updatePassword(any(), any())).thenReturn(1);

        // 调用服务方法
        userService.updatePassword("2252073", "222222");

        // 验证 updatePassword 是否被调用
        verify(userMapper, times(1)).updatePassword(any(), any());
    }

    @Test
    public void testUpdatePassword_Failure() {
        // 模拟密码更新失败
        when(userMapper.updatePassword(any(), any())).thenReturn(0);

        // 调用服务方法并验证异常
        assertThrows(DatabaseOperationException.class, () -> userService.updatePassword("test@example.com", "newPassword"));
    }

    @Test
    public void testFindUserById_UserFound() {
        // 模拟返回特定用户
        when(userMapper.selectOne(any())).thenReturn(testUser);

        // 调用方法
        User result = userService.findUserById("2252073");

        // 验证结果
        assertNotNull(result);
        assertEquals("2252073", result.getId());
    }

    @Test
    public void testFindUserById_UserNotFound() {
        // 模拟返回 null
        when(userMapper.selectOne(any())).thenReturn(null);

        // 调用方法并验证异常
        assertThrows(DataNotFoundException.class, () -> userService.findUserById("nonexistentId"));
    }
}
