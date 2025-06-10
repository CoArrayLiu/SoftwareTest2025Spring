package com.backend.vse.mapper;

import com.backend.vse.dto.StudentExperimentScoreDto;
import com.backend.vse.dto.StudentTotalScoreDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentScoreMapper {

    /**
     * 获取课程中所有学生的总成绩
     *
     * @param courseId 课程 ID
     * @return 学生总成绩列表
     */
    @Select("SELECT sc.student_index, SUM(r.report_score) AS total_score " +
            "FROM teacher_student_course sc " +
            "JOIN experiment_submit es ON sc.student_index = es.student_index AND sc.course_id = es.course_id " +
            "JOIN Report r ON es.report_id = r.report_id " +
            "WHERE sc.course_id = #{courseId} " +
            "GROUP BY sc.student_index")
    List<StudentTotalScoreDto> getTotalScoresByCourseId(@Param("courseId") Long courseId);

    /**
     * 获取课程中所有学生的所有实验成绩
     *
     * @param courseId 课程 ID
     * @return 学生实验成绩列表
     */
    @Select("SELECT sc.student_index, es.experiment_id, r.report_score " +
            "FROM teacher_student_course sc " +
            "JOIN experiment_submit es ON sc.student_index = es.student_index AND sc.course_id = es.course_id " +
            "JOIN Report r ON es.report_id = r.report_id " +
            "WHERE sc.course_id = #{courseId}")
    List<StudentExperimentScoreDto> getExperimentScoresByCourseId(@Param("courseId") Long courseId);

    /**
     * 获取课程中单个学生的所有实验成绩
     *
     * @param courseId 课程 ID
     * @param studentIndex 学生 Index
     * @return 学生实验成绩列表
     */
    @Select("SELECT es.experiment_id, r.report_score " +
            "FROM experiment_submit es " +
            "JOIN Report r ON es.report_id = r.report_id " +
            "WHERE es.course_id = #{courseId} " +
            "AND es.student_index = #{studentIndex}")
    List<StudentExperimentScoreDto> getExperimentScoresByCourseIdAndStudentIndex(
            @Param("courseId") Long courseId,@Param("studentIndex") Long studentIndex);
}