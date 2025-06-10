package com.backend.vse.mapper;

import java.util.List;

import com.backend.vse.dto.StudentManageInfoDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import com.backend.vse.dto.StudentInfoDto;
import com.backend.vse.entity.Student;

public interface StudentMapper extends BaseMapper<Student> {
    /**
     * 根据课程ID查询学生信息
     */
    @Select("""
            SELECT
                s.student_index AS studentIndex,
                u.name AS studentName,
                u.id AS studentId,
                s.school,
                u.email,
                u.role,
                u.status
            FROM Student s
            JOIN User u ON s.user_index = u.user_index
            WHERE s.student_index IN (
                SELECT tsc.student_index
                FROM teacher_student_course tsc
                WHERE tsc.course_id = #{courseId}
            )
            """)
    List<StudentInfoDto> findStudentsByCourseId(@Param("courseId") long courseId);

    /**
     * 查找课程的可选课学生名单
     */
    @Select("""
            SELECT
                s.student_index AS studentIndex,
                u.name AS studentName,
                u.id AS studentId,
                s.school,
                u.email,
                u.status
            FROM Student s
            JOIN User u ON s.user_index = u.user_index
            WHERE s.student_index NOT IN (
                SELECT tsc.student_index
                FROM teacher_student_course tsc
                WHERE tsc.course_id = #{courseId}
            )
            """)
    List<StudentInfoDto> findFreeStudentsByCourseId(@Param("courseId") long courseId);

    /**
     * 根据用户索引查询学生
     */
    @Select("SELECT * FROM Student WHERE user_index = #{userIndex}")
    Student selectStudentByUserIndex(@Param("userIndex") Long userIndex);

    /**
     * 批量插入学生
     */
    int batchInsert(@Param("students") List<Student> students);

    /**
     * 获取所有学生信息
     */
    @Select("SELECT " +
            "u.user_index as user_index, s.school, s.course_score, " +
            "u.name as student_name, u.id as student_id , u.status as status, u.email as email " +
            "FROM Student s " +
            "LEFT JOIN User u ON s.user_index = u.user_index")
    List<StudentManageInfoDto> fetchStudentInfo();

    /**
     * 动态更新学生信息
     */
    int updateStudent(Student student);
}