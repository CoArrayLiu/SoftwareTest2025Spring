package com.backend.vse.service.impl;

import com.backend.vse.dto.TeacherImportDto;
import com.backend.vse.entity.User;
import com.backend.vse.mapper.TeacherMapper;
import com.backend.vse.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherServiceImplTest {

    private TeacherServiceImpl teacherService;

    @BeforeEach
    void setUp() {
        TeacherMapper teacherMapper = Mockito.mock(TeacherMapper.class);
        UserMapper userMapper = Mockito.mock(UserMapper.class);
        UserServiceImpl userService = Mockito.mock(UserServiceImpl.class);
        teacherService = new TeacherServiceImpl(teacherMapper, userMapper, userService);
    }

    private TeacherImportDto buildDto(String id, String name, String email) {
        TeacherImportDto dto = new TeacherImportDto();
        dto.setId(id);
        dto.setName(name);
        dto.setEmail(email);
        return dto;
    }

    @Test
    @DisplayName("Test createUser with all nulls")
    void testAllNulls() {
        TeacherImportDto dto = buildDto(null, null, null);
        User user = teacherService.createUser(dto);
        assertNull(user.getId());
        assertNull(user.getName());
        assertNull(user.getEmail());
        assertEquals((byte) 0, user.getStatus());
        assertEquals("teacher", user.getRole());
    }

    @Test
    @DisplayName("Test with valid name only")
    void testValidNameOnly() {
        TeacherImportDto dto = buildDto(null, "张三", null);
        User user = teacherService.createUser(dto);
        assertEquals("张三", user.getName());
    }

    @Test
    @DisplayName("Test with invalid email")
    void testInvalidEmail() {
        TeacherImportDto dto = buildDto("123", "张三", "not-an-email");
        User user = teacherService.createUser(dto);
        assertEquals("not-an-email", user.getEmail());
    }

    @Test
    @DisplayName("Test with all valid fields")
    void testAllValidFields() {
        TeacherImportDto dto = buildDto("123", "张三", "teacher@example.com");
        User user = teacherService.createUser(dto);
        assertEquals("123", user.getId());
        assertEquals("张三", user.getName());
        assertEquals("teacher@example.com", user.getEmail());
        assertEquals("teacher", user.getRole());
        assertEquals("123", user.getPassword());
    }

    @Test
    @DisplayName("Test with valid name, invalid email")
    void testValidNameInvalidEmail() {
        TeacherImportDto dto = buildDto("abc", "李四", "invalid@");
        User user = teacherService.createUser(dto);
        assertEquals("李四", user.getName());
        assertEquals("invalid@", user.getEmail());
    }

    @Test
    @DisplayName("Test with id only")
    void testIdOnly() {
        TeacherImportDto dto = buildDto("321", null, null);
        User user = teacherService.createUser(dto);
        assertEquals("321", user.getId());
        assertEquals("321", user.getPassword());
    }

    @Test
    @DisplayName("Test with valid id and email")
    void testValidIdEmail() {
        TeacherImportDto dto = buildDto("456", null, "test@domain.com");
        User user = teacherService.createUser(dto);
        assertEquals("456", user.getId());
        assertEquals("test@domain.com", user.getEmail());
    }

    @Test
    @DisplayName("Test with name and valid email")
    void testNameEmail() {
        TeacherImportDto dto = buildDto(null, "王五", "king@school.com");
        User user = teacherService.createUser(dto);
        assertEquals("王五", user.getName());
        assertEquals("king@school.com", user.getEmail());
    }

    @Test
    @DisplayName("Test with all valid except missing email")
    void testAllValidExceptEmail() {
        TeacherImportDto dto = buildDto("789", "赵六", null);
        User user = teacherService.createUser(dto);
        assertEquals("789", user.getId());
        assertEquals("赵六", user.getName());
        assertNull(user.getEmail());
    }
}
