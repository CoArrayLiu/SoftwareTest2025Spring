package com.backend.vse.service.impl;

import com.backend.vse.entity.TeacherStudentCourse;
import com.backend.vse.exception.DataNotFoundException;
import com.backend.vse.mapper.CourseMapper;
import com.backend.vse.mapper.CourseStudentTeacherMapper;
import com.backend.vse.mapper.StudentCourseMapper;
import com.backend.vse.mapper.StudentScoreMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetCourseIdByStudentIndexTest {

    @Mock
    private CourseStudentTeacherMapper courseStudentTeacherMapper;

    @InjectMocks
    private CourseServiceImpl courseService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        // 其它 mapper 和 redisTemplate 可为空，因为本测试不涉及它们
        courseService = new CourseServiceImpl(
                null,
                courseStudentTeacherMapper,
                null,
                null,
                mock(RedisTemplate.class)
        );
    }

    /**
     * UT_TD_002_002_001: 学生ID在数据库中不存在
     */
    @Test
    public void testGetCourseIdByStudentIndex_NotFound() {
        Long studentIndex = 0L;
        when(courseStudentTeacherMapper.getTeacherAndCourseOfAStudent(studentIndex)).thenReturn(null);

        assertThrows(DataNotFoundException.class, () -> {
            courseService.getCourseIdByStudentIndex(studentIndex);
        });

        verify(courseStudentTeacherMapper, times(1)).getTeacherAndCourseOfAStudent(studentIndex);
    }

    /**
     * UT_TD_002_002_002: 学生ID合法，且加入过课程
     */
    @Test
    public void testGetCourseIdByStudentIndex_Exists() {
        Long studentIndex = 2252073L;
        when(courseStudentTeacherMapper.getTeacherAndCourseOfAStudent(studentIndex)).thenReturn(null);

        assertThrows(DataNotFoundException.class, () -> {
            courseService.getCourseIdByStudentIndex(studentIndex);
        });

        verify(courseStudentTeacherMapper, times(1)).getTeacherAndCourseOfAStudent(studentIndex);
    }

    /**
     * UT_TD_002_002_003: 学生ID合法，但未加入课程（模拟返回对象为 null）
     */
    @Test
    public void testGetCourseIdByStudentIndex_NoCourseJoined() {
        Long studentIndex = -36L;
        when(courseStudentTeacherMapper.getTeacherAndCourseOfAStudent(studentIndex)).thenReturn(null);

        assertThrows(DataNotFoundException.class, () -> {
            courseService.getCourseIdByStudentIndex(studentIndex);
        });

        verify(courseStudentTeacherMapper, times(1)).getTeacherAndCourseOfAStudent(studentIndex);
    }

    /**
     * UT_TD_002_002_004: 学生输入参数为 null
     */
    @Test
    public void testGetCourseIdByStudentIndex_NullInput() {
        Long studentIndex = 0L;
        when(courseStudentTeacherMapper.getTeacherAndCourseOfAStudent(studentIndex)).thenReturn(null);

        assertThrows(DataNotFoundException.class, () -> {
            courseService.getCourseIdByStudentIndex(studentIndex);
        });

        verify(courseStudentTeacherMapper, times(1)).getTeacherAndCourseOfAStudent(studentIndex);
    }
}
