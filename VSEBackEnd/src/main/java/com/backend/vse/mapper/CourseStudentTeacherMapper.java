package com.backend.vse.mapper;

import com.backend.vse.entity.TeacherStudentCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CourseStudentTeacherMapper extends BaseMapper<TeacherStudentCourse> {
    /**
     * 获取某个学生的所有教师和课程信息
     * 根据学生的索引（studentIndex），查询该学生参与的所有课程和对应的教师信息
     * @param studentIndex 学生的索引
     * @return 返回学生参与的课程和对应教师的详细信息（TeacherStudentCourse实体）
     */
    @Select("""
            SELECT tsc.*
            FROM teacher_student_course tsc
            WHERE tsc.student_index = #{studentIndex}
            """)
    TeacherStudentCourse getTeacherAndCourseOfAStudent(@Param("studentIndex") long studentIndex);
    /**
     * 获取某个学生在特定课程中的教师索引
     * 根据学生的索引（studentIndex）和课程ID（courseId），查询该学生在指定课程中的教师索引
     * @param studentIndex 学生的索引
     * @param courseId 课程的ID
     * @return 返回该学生在指定课程中的教师索引（teacher_index）
     */
    @Select("""
            SELECT tsc.teacher_index
            FROM teacher_student_course tsc
            WHERE tsc.student_index = #{studentIndex}
            AND tsc.course_id = #{courseId}
            """)
    Long getTeacherOfAStudentInCourse(@Param("studentIndex") long studentIndex,
                                      @Param("courseId") long courseId);
}