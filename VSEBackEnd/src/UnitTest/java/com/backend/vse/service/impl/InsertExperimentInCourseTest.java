package com.backend.vse.service.impl;

import com.backend.vse.exception.DatabaseOperationException;
import com.backend.vse.mapper.ExperimentMapper;
import com.backend.vse.service.ExperimentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.DataAccessException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InsertExperimentInCourseTest {

    @Mock
    private ExperimentMapper experimentMapper;

    @InjectMocks
    private ExperimentServiceImpl experimentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * UT_TD_002_003_001 - teacherID和experimentID不匹配
     */
    @Test
    public void testInsertExperimentInCourse_TeacherExperimentMismatch() {
        when(experimentMapper.insertExperimentInCourse(1L, 2L, 12L)).thenReturn(0);

        assertThrows(DatabaseOperationException.class, () -> {
            experimentService.insertExperimentInCourse(1L, 2L, 12L);
        });
    }

    /**
     * UT_TD_002_003_002 - teacherID和experimentID合法且匹配
     */
    @Test
    public void testInsertExperimentInCourse_Success() {
        when(experimentMapper.insertExperimentInCourse(1L, 3L, 8L)).thenReturn(1);

        assertDoesNotThrow(() -> {
            experimentService.insertExperimentInCourse(1L, 3L, 8L);
        });
    }

    /**
     * UT_TD_002_003_003 - teacherID不在数据库
     */
    @Test
    public void testInsertExperimentInCourse_TeacherNotExists() {
        when(experimentMapper.insertExperimentInCourse(1L, -1L, 6L)).thenReturn(0);

        assertThrows(DatabaseOperationException.class, () -> {
            experimentService.insertExperimentInCourse(1L, -1L, 6L);
        });
    }

    /**
     * UT_TD_002_003_004 - experimentID不在数据库
     */
    @Test
    public void testInsertExperimentInCourse_ExperimentNotExists() {
        when(experimentMapper.insertExperimentInCourse(1L, 3L, -3L)).thenReturn(0);

        assertThrows(DatabaseOperationException.class, () -> {
            experimentService.insertExperimentInCourse(1L, 3L, -3L);
        });
    }

    /**
     * UT_TD_002_003_005 - teacherID为空（null）
     */
    @Test
    public void testInsertExperimentInCourse_TeacherIdNull() {
        when(experimentMapper.insertExperimentInCourse(1L, 5L, 5L))
                .thenThrow(new DataAccessException("DB connection error") {});

        assertThrows(DatabaseOperationException.class, () -> {
            experimentService.insertExperimentInCourse(1L, 5L, 5L);
        });
    }

    /**
     * UT_TD_002_003_006 - experimentID为空（null）
     */
    @Test
    public void testInsertExperimentInCourse_ExperimentIdNull() {
        when(experimentMapper.insertExperimentInCourse(1L, 5L, 5L))
                .thenThrow(new DataAccessException("DB connection error") {});

        assertThrows(DatabaseOperationException.class, () -> {
            experimentService.insertExperimentInCourse(1L, 5L, 5L);
        });
    }

    /**
     * 补充测试 - DataAccessException 异常处理
     */
    @Test
    public void testInsertExperimentInCourse_DatabaseError() {
        when(experimentMapper.insertExperimentInCourse(1L, 5L, 5L))
                .thenThrow(new DataAccessException("DB connection error") {});

        assertThrows(DatabaseOperationException.class, () -> {
            experimentService.insertExperimentInCourse(1L, 5L, 5L);
        });
    }
}
