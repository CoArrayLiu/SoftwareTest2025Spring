package com.backend.vse.service.impl;

import com.backend.vse.common.ResponseCode;
import com.backend.vse.common.Result;
import com.backend.vse.dto.StudentImportDto;
import com.backend.vse.entity.Student;
import com.backend.vse.entity.User;
import com.backend.vse.exception.BusinessException;
import com.backend.vse.exception.DataEmptyException;
import com.backend.vse.exception.OperationException;
import com.backend.vse.mapper.StudentMapper;
import com.backend.vse.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ImportStudentTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentServiceImpl StudentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // 将 createUser 和 createStudent 方法设为 package-private 或 protected，使其可重写或测试
    }

    @Test
    public void testImportStudent_Success() {
        StudentImportDto dto = new StudentImportDto("2023001", "张三", "zhangsan@example.com", "清华大学");
        User user = new User();
        user.setId(dto.getId());
        user.setUserIndex(1L);

        when(userMapper.insert(any())).thenReturn(1);
        when(userMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(user);
        when(studentMapper.insert(any(Student.class))).thenReturn(1);

        Result<String> result = StudentService.importStudent(dto);

        assertTrue(result.isSuccess());
        assertEquals("成功导入学生信息", result.getData());
        verify(userMapper, times(1)).insert(any());
        verify(studentMapper, times(1)).insert(any());
    }

    @Test
    public void testImportStudent_UserInsertFails() {
        StudentImportDto dto = new StudentImportDto("2023002", "李四", "lisi@example.com", "北大");
        when(userMapper.insert(any())).thenReturn(0);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            StudentService.importStudent(dto);
        });

        assertTrue(exception.getMessage().contains("导入学生信息失败"));
        verify(userMapper, times(1)).insert(any());
        verify(studentMapper, never()).insert(any());
    }

    @Test
    public void testImportStudent_StudentInsertFails() {
        StudentImportDto dto = new StudentImportDto("2023003", "王五", "wangwu@example.com", "复旦");
        User user = new User();
        user.setId(dto.getId());
        user.setUserIndex(2L);

        when(userMapper.insert(any())).thenReturn(1);
        when(userMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(user);
        when(studentMapper.insert(any())).thenReturn(0);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            StudentService.importStudent(dto);
        });

        assertTrue(exception.getMessage().contains("导入学生信息失败"));
        verify(userMapper, times(1)).insert(any());
        verify(studentMapper, times(1)).insert(any());
    }
}
