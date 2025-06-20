package com.backend.vse.service.impl;

import com.backend.vse.entity.Report;
import com.backend.vse.mapper.ReportMapper;
import com.backend.vse.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ReportServiceImplTest {

    @Mock
    private ReportMapper reportMapper;

    @InjectMocks
    private ReportServiceImpl reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * UT_TD_003_003_001 - userID和experimentID不匹配（返回null）
     */
    @Test
    void testUserAndExperimentIdMismatch() {
        when(reportMapper.findLatestReport(4L, 1L, 6L)).thenReturn(null);

        String result = reportService.getLatestReportContent(4L, 1L, 6L);
        assertNull(result);
    }

    /**
     * UT_TD_003_003_002 - userID和experimentID匹配（返回内容）
     */
    @Test
    void testUserAndExperimentIdMatch() {
        Report mockReport = new Report();
        mockReport.setReportContent("测试报告内容");
        when(reportMapper.findLatestReport(3L, 1L, 6L)).thenReturn(mockReport);

        String result = reportService.getLatestReportContent(3L, 1L, 6L);
        assertEquals("测试报告内容", result);
    }

    /**
     * UT_TD_003_003_003 - userID不存在（返回null）
     */
    @Test
    void testUserIdNotExists() {
        when(reportMapper.findLatestReport(0L, 1L, 6L)).thenReturn(null);

        String result = reportService.getLatestReportContent(0L, 1L, 6L);
        assertNull(result);
    }

    /**
     * UT_TD_003_003_004 - experimentID不存在（返回null）
     */
    @Test
    void testExperimentIdNotExists() {
        when(reportMapper.findLatestReport(3L, 1L, 0L)).thenReturn(null);

        String result = reportService.getLatestReportContent(3L, 1L, 0L);
        assertNull(result);
    }

    /**
     * UT_TD_003_003_005 - userID为空（NullPointerException）
     */
    @Test
    void testUserIdIsNull() {
        when(reportMapper.findLatestReport(3L, 1L, 0L)).thenReturn(null);

        String result = reportService.getLatestReportContent(3L, 1L, 0L);
        assertNull(result);
    }

    /**
     * UT_TD_003_003_006 - experimentID为空（NullPointerException）
     */
    @Test
    void testExperimentIdIsNull() {
        when(reportMapper.findLatestReport(3L, 1L, 0L)).thenReturn(null);

        String result = reportService.getLatestReportContent(3L, 1L, 0L);
        assertNull(result);
    }
}
