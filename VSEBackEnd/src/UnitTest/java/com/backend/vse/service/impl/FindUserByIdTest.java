package com.backend.vse.service.impl;

import com.backend.vse.entity.User;
import com.backend.vse.exception.DataNotFoundException;
import com.backend.vse.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class FindUserByIdTest {

    private UserServiceImpl userService;
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = Mockito.mock(UserMapper.class);
        userService = new UserServiceImpl(userMapper);
    }

    @Test
    @DisplayName("UT_TC_001_003_001 - user_id 为空")
    void testFindUserById_NullId() {
        String userId = "";
        Mockito.when(userMapper.selectOne(Mockito.any())).thenReturn(null);

        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> userService.findUserById(userId));
        assertEquals("该用户未找到", exception.getMessage());
    }

    @Test
    @DisplayName("UT_TC_001_003_002 - user_id 不存在")
    void testFindUserById_IdNotExist() {
        String userId = "-1";
        Mockito.when(userMapper.selectOne(Mockito.any())).thenReturn(null);

        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> userService.findUserById(userId));
        assertEquals("该用户未找到", exception.getMessage());
    }

    @Test
    @DisplayName("UT_TC_001_003_003 - user_id 存在")
    void testFindUserById_Exists() {
        String userId = "1";
        User mockUser = new User();
        mockUser.setId("1");
        mockUser.setName("张三");
        mockUser.setEmail("zhangsan@example.com");

        Mockito.when(userMapper.selectOne(Mockito.any())).thenReturn(mockUser);

        User result = userService.findUserById(userId);
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("张三", result.getName());
        assertEquals("zhangsan@example.com", result.getEmail());
    }
}

