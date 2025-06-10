package com.backend.vse.mapper;

import com.backend.vse.dto.ExperimentScoreDto;
import com.backend.vse.dto.StudentCourseScoreDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentCourseMapper {

    /**
     * 根据学生索引和课程ID查询学生的基本信息和课程成绩
     * 根据学生的索引（studentIndex）和课程ID（courseId），查询该学生在指定课程中的基本信息，包括学校、课程成绩以及课程ID
     * @param studentIndex 学生的索引
     * @param courseId 课程的ID
     * @return 返回包含学生信息和课程成绩的DTO（StudentCourseScoreDto）
     */
    @Select("SELECT s.student_index AS studentIndex, " +
            "       u.name AS name, " +
            "       s.school AS school, " +
            "       s.course_score AS courseScore, " +
            "       tsc.course_id AS courseId " +
            "FROM Student s " +
            "JOIN teacher_student_course tsc ON s.student_index = tsc.student_index " +
            "JOIN User u ON u.user_index = s.user_index " +
            "WHERE tsc.student_index = #{studentIndex} AND tsc.course_id = #{courseId}")
    StudentCourseScoreDto selectStudentInfoByIndexAndCourseId(
            @Param("studentIndex") Long studentIndex,
            @Param("courseId") Long courseId);

    /**
     * 根据课程ID查询课程下的所有实验及其满分
     * 根据课程ID（courseId），查询该课程下所有实验的ID、实验名称和对应的实验满分
     * @param courseId 课程的ID
     * @return 返回包含实验ID、实验名称及满分的列表（List<ExperimentScoreDto>）
     */
    @Select("SELECT e.experiment_id AS experimentId, " +
            "       e.experiment_name AS experimentName, " +
            "       tae.experiment_full_score AS fullScore " +
            "FROM Experiment e " +
            "JOIN teacher_assign_experiment tae ON e.experiment_id = tae.experiment_id " +
            "WHERE tae.course_id = #{courseId}")
    List<ExperimentScoreDto> selectExperimentScoresByCourse(
            @Param("courseId") Long courseId);
}