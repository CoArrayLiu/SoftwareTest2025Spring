package com.backend.vse.service.impl;

import com.backend.vse.entity.Report;
import com.backend.vse.exception.DataNotFoundException;
import com.backend.vse.mapper.ReportMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReportServiceImpl_GetReportScoreTest {

    @Mock
    private ReportMapper reportMapper;

    @InjectMocks
    private ReportServiceImpl reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * UT_TC_003_001_001 - 输入报告ID不存在
     */
    @Test
    void testReportIdNotExists() {
        when(reportMapper.selectReportById(999L)).thenReturn(null);

        assertThrows(DataNotFoundException.class, () -> {
            reportService.getReportScore(999L);
        });
    }

    /**
     * UT_TC_003_001_002 - 输入报告ID为空（NullPointerException）
     */
    @Test
    void testReportIdIsNull() {
        Report report = new Report();
        report.setReportScore(0f);
        when(reportMapper.selectReportById(100L)).thenReturn(report);

        Float score = reportService.getReportScore(100L);
        assertEquals(0f, score);
    }

    /**
     * UT_TC_003_001_003 - 输入报告ID合法，报告URL有效（有分数）
     */
    @Test
    void testValidReportIdWithScore() {
        Report report = new Report();
        report.setReportScore(87.5f);
        when(reportMapper.selectReportById(100L)).thenReturn(report);

        Float score = reportService.getReportScore(100L);
        assertEquals(87.5f, score);
    }

    /**
     * UT_TC_003_001_004 - 输入报告ID合法，但报告URL无效（分数为 null）
     */
    @Test
    void testValidReportIdButScoreIsNull() {
        Report report = new Report();
        report.setReportScore(null);
        when(reportMapper.selectReportById(101L)).thenReturn(report);

        assertThrows(DataNotFoundException.class, () -> {
            reportService.getReportScore(101L);
        });
    }
}
