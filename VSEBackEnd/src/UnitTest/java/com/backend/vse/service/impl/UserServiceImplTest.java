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

    private User empty,testUser,inExiseted,falsePassword,testUserStudentInactivated,testUserTeacherInactivated,testUserStudent,testUserTeacher;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        //实例化
        empty = new User();
        testUser = new User();
        inExiseted = new User();
        falsePassword = new User();
        testUserStudentInactivated = new User();
        testUserTeacherInactivated = new User();
        testUserStudent = new User();
        testUserTeacher = new User();

        // 初始化测试数据
        inExiseted.setEmail("inExiseted@gmail.com");
        inExiseted.setPassword("inExisetedpassword");
        falsePassword.setEmail("2252073@tongji.edu.cn");
        falsePassword.setPassword("falsepassword");

        testUserStudentInactivated.setEmail("2253106@tongji.edu.cn");
        testUserStudentInactivated.setPassword("111111");
        testUserTeacherInactivated.setEmail("111111@tongji.edu.cn");
        testUserTeacherInactivated.setPassword("111111");
        testUserStudent.setEmail("2252073@tongji.edu.cn");
        testUserStudent.setPassword("111111");
        testUserTeacher.setEmail("huangjie@tongji.edu.cn");
        testUserTeacher.setPassword("111111");
    }

    @Test
    public void testEmpty(){
        // 调用相关方法
        boolean result = userService.findUser(empty.getEmail(),empty.getPassword());

        //验证结果
        assertFalse(result);
    }

    @Test
    public void testInExiseted(){
        // 调用相关方法
        boolean result = userService.findUser(inExiseted.getEmail(),inExiseted.getPassword());

        //验证结果
        assertFalse(result);
    }

    @Test
    public void testFalsePassword(){
        // 调用相关方法
        boolean result = userService.findUser(falsePassword.getEmail(),falsePassword.getPassword());

        //验证结果
        assertFalse(result);
    }

    @Test
    public void testStudentInactivated(){
        // 调用相关方法
        boolean result = userService.findUser(testUserStudentInactivated.getEmail(),testUserStudentInactivated.getPassword());

        //验证结果
        assertFalse(result);
    }

    @Test
    public void testTeacherInactivated(){
        // 调用相关方法
        boolean result = userService.findUser(testUserTeacherInactivated.getEmail(),testUserTeacherInactivated.getPassword());

        //验证结果
        assertFalse(result);
    }

    @Test
    public void testStudent(){
        // 调用相关方法
        boolean result = userService.findUser(testUserStudent.getEmail(),testUserStudent.getPassword());

        //验证结果
        assertFalse(result);
    }

    @Test
    public void testTeacher(){
        // 调用相关方法
        boolean result = userService.findUser(testUserTeacher.getEmail(),testUserTeacher.getPassword());

        //验证结果
        assertFalse(result);
    }
}
