package com.backend.vse.mapper;

import com.backend.vse.entity.ExperimentSubmit;
import com.backend.vse.entity.Report;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

public interface SubmissionMapper {
    /**
     * 根据学生Index查询其所有提交记录
     */

    @Select("SELECT * FROM Report WHERE student_index = #{studentIndex}")
    List<Report> getSubmissionByStudentIndex(@Param("studentIndex") Long studentIndex);

    /**
     * 查询指定时间范围内的提交记录
     */
    @Select("""
                SELECT * FROM Report r
                JOIN Timer t ON r.timer_id = t.timer_id
                WHERE t.submit_time BETWEEN #{startTime} AND #{endTime}
            """)
    List<Report> getSubmissionsByTimeRange(@Param("startTime") Timestamp startTime, @Param("endTime") Timestamp endTime);

    /**
     * 更新学生提交的实验报告ID
     * 根据学生索引（studentIndex）、实验ID（experimentId）和课程ID（courseId），更新指定实验报告的ID
     * @param reportId 新的实验报告ID
     * @param studentIndex 学生的索引
     * @param experimentId 实验的ID
     * @param courseId 课程的ID
     * @return 返回更新操作的影响行数
     */
    @Update("UPDATE experiment_submit SET report_id = #{reportId} WHERE student_index = #{studentIndex} " +
            "AND experiment_id = #{experimentId} AND course_id = #{courseId}")
    int updateNewestReport(Long reportId, Long studentIndex, Long experimentId, Long courseId);

    /**
     * 插入学生提交的实验报告记录
     * 插入一条学生提交实验报告的记录，自动生成报告ID
     * @param experimentSubmit 实验提交记录对象，包括学生索引、实验ID、课程ID和报告ID
     * @return 返回插入操作的影响行数
     */
    @Insert("INSERT INTO experiment_submit (student_index, experiment_id, course_id, report_id) " +
            "VALUES (#{studentIndex}, #{experimentId}, #{courseId}, #{reportId})")
    @Options(useGeneratedKeys = true, keyProperty = "reportId", keyColumn = "reportId") // 自动生成 reportId
    int insertExperimentReportSubmit(ExperimentSubmit experimentSubmit);
}