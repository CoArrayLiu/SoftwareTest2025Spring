package com.backend.vse.service.impl;

import com.backend.vse.common.ResponseCode;
import com.backend.vse.dto.StudentReportRecordDto;
import com.backend.vse.entity.ExperimentSubmit;
import com.backend.vse.entity.Report;
import com.backend.vse.exception.DataNotFoundException;
import com.backend.vse.exception.DatabaseOperationException;
import com.backend.vse.mapper.ReportMapper;
import com.backend.vse.mapper.SubmissionMapper;
import com.backend.vse.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // Lombok 自动生成构造函数
@Slf4j
public class ReportServiceImpl implements ReportService {
    private final ReportMapper reportMapper;
    private final SubmissionMapper submissionMapper;

    @Override
    @Transactional
    public void setReportScore(Long reportId, Float score) {
        try {
            if (reportMapper.updateReportScore(reportId, score) <= 0) {
                throw new DataNotFoundException("报告不存在: " + reportId);
            }
        } catch (DataAccessException e) {
            throw handleDatabaseException(e,"设置报告分数");
        }
    }

    /**
     * 根据报告ID获取报告详细信息
     *
     * @param reportId 报告ID
     * @return 报告对象
     */
    @Override
    @Transactional(readOnly = true)
    public Report getReportById(Long reportId) {
        return Optional.ofNullable(reportMapper.selectReportById(reportId))
                .orElseThrow(() -> new DataNotFoundException("报告不存在: " + reportId));
    }

    @Override
    @Transactional(readOnly = true)
    public String getReportStatus(Long reportId) {
        return getReportById(reportId).getStatus();
    }

    @Override
    @Transactional
    public void submitReport(Report report) {
        try {
            if (reportMapper.insert(report) <= 0) {
                throw new DatabaseOperationException(
                        ResponseCode.SYSTEM_DB_ERROR, "报告提交失败");
            }
        } catch (DataAccessException e) {
            throw handleDatabaseException(e, "提交报告");
        }
    }

    @Override
    @Transactional
    public void updateReport(Long reportId, Long studentIndex, Long experimentId, Long courseId) {
        // 检查是否存在符合条件的记录
        Report oldReport = reportMapper.findLatestReport(studentIndex, courseId, experimentId);
        try {
            int affectedRows = (oldReport != null)
                    ? submissionMapper.updateNewestReport(reportId, studentIndex, experimentId, courseId)
                    : submissionMapper.insertExperimentReportSubmit(new ExperimentSubmit(reportId, studentIndex, experimentId, courseId));

            if (affectedRows <= 0) {
                throw new DatabaseOperationException(
                        ResponseCode.SYSTEM_DB_ERROR, "更新报告关联失败");
            }
        } catch (DataAccessException e) {
            throw handleDatabaseException(e, "更新报告关联");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentReportRecordDto> getStudentReportRecords(Long studentIndex) {
        // 调用 reportMapper 查询该学生的报告记录，并返回报告记录列表
        // 该方法通过学生的学号 (studentIndex) 查询数据库中与学生相关的报告记录。
        // 返回的列表包含了多个学生的报告记录，使用了 Data Transfer Object (DTO) 传输数据，避免直接暴露数据库模型。
        try {
            return Optional
                    .ofNullable(reportMapper.findReportsByStudentIndex(studentIndex))
                    .orElseThrow(() -> new DataNotFoundException("未查询到报告记录"));
        } catch (DataAccessException e) {
            throw handleDatabaseException(e, "查询报告记录");
        }
    }

    @Override
    @Transactional
    public void addCommentToReport(Long reportId, String comment) {
        try {
            // 检查报告存在性
            getReportById(reportId);

            if (reportMapper.updateReportComment(reportId, comment) <= 0) {
                throw new DatabaseOperationException(
                        ResponseCode.SYSTEM_DB_ERROR, "评论添加失败");
            }
        } catch (DataAccessException e) {
            throw handleDatabaseException(e, "添加评论");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public String getLatestReportContent(Long studentIndex, Long courseId, Long experimentId) {
        // 查询并返回指定学生、课程和实验的最新报告
        // 通过调用 reportMapper 的 findLatestReport 方法，获取指定学号 (studentIndex)、课程 ID (courseId)、实验 ID (experimentId) 的最新报告。
        return Optional.ofNullable(reportMapper.findLatestReport(studentIndex, courseId, experimentId))
                .map(Report::getReportContent)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getLatestReportId(Long studentIndex, Long courseId, Long experimentId) {
        // 查询并返回指定学生、课程和实验的最新报告
        // 通过调用 reportMapper 的 findLatestReport 方法，获取指定学号 (studentIndex)、课程 ID (courseId)、实验 ID (experimentId) 的最新报告。
        // 返回一个包含最新报告信息的 DTO (PendingGradedReportDTO) 对象，DTO 包含了报告的详细信息。
        return Optional.ofNullable(reportMapper.findLatestReport(studentIndex, courseId, experimentId))
                .map(Report::getReportId)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Float getReportScore(Long reportId) {
        return Optional.ofNullable(getReportById(reportId).getReportScore())
                .orElseThrow(() -> new DataNotFoundException("该报告暂未设置分数: " + reportId));
    }

    //=================== 异常处理工具方法 ===================//
    private DatabaseOperationException handleDatabaseException(DataAccessException e, String operation) {
        String sqlState = (e.getCause() instanceof SQLException)
                ? ((SQLException) e.getCause()).getSQLState()
                : "UNKNOWN";

        log.error("数据库操作异常[{}] SQLSTATE: {} - {}",
                operation,
                sqlState,
                e.getMessage());

        return new DatabaseOperationException(
                ResponseCode.SYSTEM_DB_ERROR,
                operation + "失败"
        );
    }
}