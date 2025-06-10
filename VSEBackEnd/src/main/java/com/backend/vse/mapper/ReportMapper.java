package com.backend.vse.mapper;

import com.backend.vse.dto.StudentReportRecordDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.backend.vse.entity.Report;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface ReportMapper extends BaseMapper<Report> {
    /**
     * 更新报告分数
     */
    @Update("UPDATE Report SET report_score = #{reportScore}, status = 'graded' WHERE report_id = #{reportId}")
    int updateReportScore(@Param("reportId") Long reportId, @Param("reportScore") Float reportScore);

    /**
     * 根据学生的学号查询该学生的报告记录
     * 该方法根据学生的学号 (`studentIndex`) 查询该学生所有的报告记录。通过与 `Timer` 表的连接，查询结果不仅包括报告的基本信息，如报告ID、内容、评论、评分和状态，还包括报告的提交时间 (`submit_time`)。
     * 该查询结果封装在 `StudentReportRecordDto` 对象中，以便进一步处理和展示。
     *
     * @param studentIndex 学生的学号，用于查询该学生的所有报告记录
     * @return 返回该学生的所有报告记录列表，每个记录包含报告的基本信息和提交时间
     */
    @Select("SELECT r.report_id, r.report_content, r.report_comment, r.report_score, r.status, t.submit_time " +
            "FROM Report r " +
            "JOIN Timer t ON r.timer_id = t.timer_id " +
            "WHERE r.student_index = #{studentIndex}")
    List<StudentReportRecordDto> findReportsByStudentIndex(@Param("studentIndex") Long studentIndex);

    /**
     * 根据报告ID查询报告的详细信息
     * 该方法根据报告的唯一标识符 (`reportId`) 查询指定报告的所有详细信息，并返回一个 `Report` 对象。此对象包含了报告的完整数据，例如报告的ID、内容、评分、评论等。
     *
     * @param reportId 报告的唯一标识符，用于查询该报告的详细信息
     * @return 返回查询到的报告对象，包含该报告的所有详细信息
     */
    @Select("SELECT * FROM Report WHERE report_id = #{reportId}")
    Report selectReportById(@Param("reportId") Long reportId);

    /**
     * 更新指定报告的评论字段
     * 该方法根据报告ID更新指定报告的评论内容。使用传入的报告ID定位到目标报告，并将传入的评论内容更新到报告的 `report_comment` 字段。如果更新操作成功，返回受影响的行数；否则返回0，表示更新失败。
     *
     * @param reportId 要更新评论的报告ID
     * @param comment 要添加到报告的评论内容
     * @return 返回受影响的行数，如果更新成功，返回1，否则返回0
     */
    @Update("UPDATE Report SET report_comment = #{comment} WHERE report_id = #{reportId}")
    int updateReportComment(@Param("reportId") Long reportId, @Param("comment") String comment);

    /**
     * 查询最新提交的实验报告
     * @param studentIndex 学生index
     * @param courseId 课程ID
     * @param experimentId 实验ID
     * @return 最新提交的实验报告
     */
    @Select("SELECT r.* " +
            "FROM experiment_submit es " +
            "LEFT JOIN Report r ON es.report_id = r.report_id " +
            "WHERE es.student_index = #{studentIndex} " +
            "AND es.course_id = #{courseId} " +
            "AND es.experiment_id = #{experimentId} ")
    Report findLatestReport(@Param("studentIndex") Long studentIndex,
                            @Param("courseId") Long courseId,
                            @Param("experimentId") Long experimentId);
}