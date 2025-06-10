package com.backend.vse.mapper;

import com.backend.vse.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;



@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 根据教师的id搜索该教师所教授的所有课程的信息
     */
    @Select("SELECT c.* " +
            "FROM course c " +
            "JOIN teacher_teach_course tt ON c.course_id = tt.course_id " +
            "WHERE tt.index = #{teacher_id}")
    List<Course> getTeachingCourseList(@Param("teacher_id") long teacherId);

    /**
     * 该接口可能出错 接口名称与实际内容不符 *
     */
    @Select("SELECT c.* " +
            "FROM course c " +
            "JOIN teacher_teach_course tt USING(course_id) " +
            "WHERE tt.index = #{index}")
    List<Course> getCoursesBySemester(@Param("index") Long index);
}

