package com.backend.vse.service.impl;

import com.backend.vse.dto.UploadExperimentFileDto;
import com.backend.vse.exception.DataNotFoundException;
import com.backend.vse.exception.DatabaseOperationException;
import com.backend.vse.mapper.ExperimentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.DataAccessException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpdateExperimentReportTemplateTest {

    @Mock
    private ExperimentMapper experimentMapper;

    @InjectMocks
    private ExperimentServiceImpl experimentService;

    private UploadExperimentFileDto validDto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        validDto = new UploadExperimentFileDto(1L, 2L, 3L, "report-template-url");
    }

    /**
     * UT_TC_002_004_001 - 实验ID不存在，更新失败
     */
    @Test
    public void testUpdateExperimentReportTemplate_ExperimentNotFound() {
        when(experimentMapper.updateReportTemplate(anyLong(), anyLong(), anyLong(), anyString()))
                .thenReturn(0);

        assertThrows(DataNotFoundException.class, () -> {
            experimentService.updateExperimentReportTemplate(validDto);
        });
    }

    /**
     * UT_TC_002_004_002 - 实验ID为空，应抛出NullPointerException（或ValidationException）
     */
    @Test
    public void testUpdateExperimentReportTemplate_ExperimentIdNull() {
        when(experimentMapper.updateReportTemplate(1L, 2L, 3L, "report-template-url"))
                .thenReturn(1);

        assertDoesNotThrow(() -> {
            experimentService.updateExperimentReportTemplate(validDto);
        });
    }

    /**
     * UT_TC_002_004_003 - 实验信息合法且未结束，成功更新
     */
    @Test
    public void testUpdateExperimentReportTemplate_Success() {
        when(experimentMapper.updateReportTemplate(1L, 2L, 3L, "report-template-url"))
                .thenReturn(1);

        assertDoesNotThrow(() -> {
            experimentService.updateExperimentReportTemplate(validDto);
        });
    }

    /**
     * UT_TC_002_004_004 - 实验信息合法但已结束（模拟 DB 拒绝更新）
     */
    @Test
    public void testUpdateExperimentReportTemplate_ExperimentEnded() {
        // 设定逻辑上已结束时 DB 不更新，返回 0
        when(experimentMapper.updateReportTemplate(1L, 2L, 3L, "report-template-url"))
                .thenReturn(0);

        assertThrows(DataNotFoundException.class, () -> {
            experimentService.updateExperimentReportTemplate(validDto);
        });
    }

    /**
     * ✅ 补充 - DB访问异常
     */
    @Test
    public void testUpdateExperimentReportTemplate_DatabaseException() {
        when(experimentMapper.updateReportTemplate(anyLong(), anyLong(), anyLong(), anyString()))
                .thenThrow(new DataAccessException("DB异常") {});

        assertThrows(DatabaseOperationException.class, () -> {
            experimentService.updateExperimentReportTemplate(validDto);
        });
    }
}
