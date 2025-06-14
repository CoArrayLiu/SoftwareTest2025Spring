package com.backend.vse.mapper;

import com.backend.vse.entity.TeacherTeachCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TeacherTeachCourseMapper extends BaseMapper<TeacherTeachCourse> {
    /**
     * 根据用户index、课程id和用户role在teacher_teach_course表中插入教师所授课程的对应关系
     * 教师的role为1
     */
    @Insert("INSERT INTO teacher_teach_course(`index`,`course_id`,`role`) VALUES (#{index},#{courseId},#{role})")
    int insert(@Param("index") Long index, @Param("courseId") Long courseId, @Param("role") Byte role);
}
