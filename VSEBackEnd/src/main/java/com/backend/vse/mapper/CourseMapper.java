package com.backend.vse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.backend.vse.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

public interface CourseMapper extends BaseMapper<Course> {
    /**
     * 查找所有课程的信息
     */
    @Select("SELECT * FROM Course")
    List<Course> selectAllCourses();

    /**
     * 根据教师的id搜索该教师所教授的所有课程的信息
     */
    @Select("""
            SELECT c.*
            FROM Course c
            JOIN teacher_student_course tt ON c.course_id = tt.course_id
            WHERE tt.teacher_index = #{teacherIndex}
            """)
    List<Course> getTeachingCourseList(@Param("teacherIndex") long teacherIndex);

    /**
     * 检查课程是否存在
     * @param courseId 课程ID
     * @return 存在返回true，不存在返回false
     */
    @Select("SELECT COUNT(1) FROM Course WHERE course_id = #{courseId}")
    boolean existsByCourseId(@Param("courseId") Long courseId);

    /**
     * 根据课程ID查询课程是否开放报名
     * @param courseId 课程ID
     * @return 课程是否开放报名 true：开放 false：关闭
     */
    @Select("SELECT quoteIsOpen FROM Course WHERE course_id = #{courseId}")
    Boolean quoteIsOpenByCourseId(@Param("courseId") Long courseId);

    /**
     * 查询课程的所有信息
     * 根据课程ID查询课程的所有信息
     * @param courseId 课程ID
     * @return 返回课程的完整信息（Course实体）
     */
    @Select("SELECT * FROM Course WHERE course_id = #{courseId}")
    Course selectAllByCourseId(@Param("courseId") Long courseId);

    /**
     * 切换课程的quoteIsOpen状态
     * 根据课程ID切换课程的quoteIsOpen状态（启用/禁用报价功能）
     * @param courseId 课程ID
     * @return 更新受影响的记录数
     */
    @Update("UPDATE Course SET quoteIsOpen = !quoteIsOpen WHERE course_id = #{courseId}")
    int changeQuote(@Param("courseId") Long courseId);
}