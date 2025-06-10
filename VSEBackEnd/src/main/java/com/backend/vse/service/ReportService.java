package com.backend.vse.service;

import com.backend.vse.dto.StudentReportRecordDto;
import com.backend.vse.entity.Report;

import java.util.List;

public interface ReportService {
    void setReportScore(Long reportId, Float score);

    Report getReportById(Long reportId);
    String getReportStatus(Long reportId);
    void submitReport(Report report);
    void updateReport(Long reportId, Long studentIndex, Long experimentId, Long courseId);
    /**
     * 获取指定学生的所有报告记录
     * 该方法根据学生的学号 (studentIndex) 查询并返回该学生所有的报告记录。
     * 返回的报告记录包含报告的基本信息，如报告ID、内容、评论、评分等，封装在 StudentReportRecordDto 对象中。
     * 通过调用 reportMapper.findReportsByStudentIndex 方法实现数据库查询。
     *
     * @param studentIndex 学生的学号，用于查询该学生的报告记录
     * @return 返回学生的所有报告记录列表
     */
    List<StudentReportRecordDto> getStudentReportRecords(Long studentIndex);

    /**
     * 向指定报告添加评论
     * 该方法根据报告ID更新指定报告的评论内容。首先检查报告是否存在，若不存在则抛出异常。
     * 若报告存在，则使用 updateReportComment 方法将新的评论添加到报告中。
     * 如果数据库更新失败，则抛出异常提示更新失败。
     *
     * @param reportId 要添加评论的报告ID
     * @param comment 要添加的评论内容
     * @throws RuntimeException 抛出异常，如果报告不存在或更新失败
     */
    void addCommentToReport(Long reportId, String comment);

    /**
     * 根据学生index、课程ID和实验ID查询学生的最新实验报告
     * 该方法根据学生的学号 (studentIndex)、课程ID (courseId) 和实验ID (experimentId) 查询该学生在指定课程和实验中的最新实验报告。
     * 返回一个 PendingGradedReportDTO 对象，包含最新的实验报告详情，如报告的提交时间、报告内容等信息。
     *
     * @param studentIndex 学生的学号，用于标识学生
     * @param courseId 课程的唯一标识符，用于查询该课程的实验报告
     * @param experimentId 实验的唯一标识符，用于查询该实验的报告
     * @return 返回最新提交的实验报告（PendingGradedReportDTO 对象）
     */
    String getLatestReportContent(Long studentIndex, Long courseId, Long experimentId);

    /**
     * 获取学生在某一实验中最新提交的报告 ID
     * 该方法用于获取指定学生在指定课程和实验中的最新提交报告 ID。通常用于查找学生最新的实验报告。
     *
     * @param studentIndex 学生的索引（Long 类型）
     * @param courseId 课程 ID（Long 类型）
     * @param experimentId 实验 ID（Long 类型）
     * @return 返回学生的最新实验报告 ID（Long 类型）
     */
    Long getLatestReportId(Long studentIndex, Long courseId, Long experimentId);

    /**
     * 获取实验报告的评分
     * 该方法用于获取指定报告的评分。通常用于显示或计算学生实验报告的得分。
     *
     * @param reportId 实验报告 ID（Long 类型）
     * @return 返回实验报告的评分（Float 类型）
     */
    Float getReportScore(Long reportId);
}