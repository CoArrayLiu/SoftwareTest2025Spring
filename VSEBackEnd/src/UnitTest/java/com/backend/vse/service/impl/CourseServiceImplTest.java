package com.backend.vse.service.impl;

import com.backend.vse.dto.CourseBasicInfoDto;
import com.backend.vse.entity.Course;
import com.backend.vse.exception.DataNotFoundException;
import com.backend.vse.mapper.CourseMapper;
import com.backend.vse.mapper.CourseStudentTeacherMapper;
import com.backend.vse.mapper.StudentCourseMapper;
import com.backend.vse.mapper.StudentScoreMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseServiceImplTest {

    private CourseServiceImpl courseService;
    private CourseMapper courseMapper;
    private RedisTemplate<String, Object> redisTemplate;
    private ValueOperations<String, Object> valueOperations;

    @BeforeEach
    void setUp() {
        courseMapper = mock(CourseMapper.class);
        CourseStudentTeacherMapper courseStudentTeacherMapper = mock(CourseStudentTeacherMapper.class);
        StudentScoreMapper studentScoreMapper = mock(StudentScoreMapper.class);
        StudentCourseMapper studentCourseMapper = mock(StudentCourseMapper.class);
        redisTemplate = mock(RedisTemplate.class);
        valueOperations = mock(ValueOperations.class);

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        courseService = new CourseServiceImpl(
                courseMapper,
                courseStudentTeacherMapper,
                studentScoreMapper,
                studentCourseMapper,
                redisTemplate
        );
    }

    @Test
    @DisplayName("UT_TC_002_001_001 - 输入教师ID不存在")
    void testTeacherIdNotExist() {
        when(courseMapper.getTeachingCourseList(0L)).thenReturn(null);

        assertThrows(DataNotFoundException.class,
                () -> courseService.getCoursesByTeacher(0L));
    }

    @Test
    @DisplayName("UT_TC_002_001_002 - 教师ID为空")
    void testTeacherIdNull() {
        assertThrows(NullPointerException.class,
                () -> courseService.getCoursesByTeacher(null));
    }

    @Test
    @DisplayName("UT_TC_002_001_003 - 教师ID存在，返回非空列表")
    void testTeacherIdValidWithData() {
        Course course = new Course();
        course.setCourseId(13L);
        course.setCourseName("经济学原理");

        when(courseMapper.getTeachingCourseList(13L)).thenReturn(List.of(course));

        List<CourseBasicInfoDto> result = courseService.getCoursesByTeacher(13L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("经济学原理", result.get(0).getCourseName());
    }

    @Test
    void testTeacherIdValidButEmptyList() {
        // 模拟 Mapper 返回空列表
        when(courseMapper.getTeachingCourseList(anyLong())).thenReturn(Collections.emptyList());

        // 调用业务方法，拿到返回的DTO列表
        List<CourseBasicInfoDto> courses = courseService.getCoursesByTeacher(1L);

        // 断言返回的列表为空
        assertTrue(courses.isEmpty(), "返回列表应该是空的");
    }
}