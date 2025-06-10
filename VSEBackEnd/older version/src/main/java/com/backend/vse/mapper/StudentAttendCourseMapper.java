package com.backend.vse.mapper;

import com.backend.vse.dto.CourseBasicInfoDto;
import com.backend.vse.dto.StudentInfoDto;
import com.backend.vse.dto.StudentSimpleDto;
import com.backend.vse.entity.CourseExperiment;
import com.backend.vse.entity.StudentAttendCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface StudentAttendCourseMapper extends BaseMapper<StudentAttendCourse> {
    /**
     * 根据课程id和用户index（数据库id）向student_attend_course表中插入学生信息
     */
    @Insert("INSERT INTO student_attend_course(`index`,`course_id`) VALUES (#{index},#{courseId})")
    int insert(@Param("index") Long index, @Param("courseId") Long courseId);
    /**
     * 根据课程id和用户index（数据库id）在student_attend_course表中删除学生信息
     */
    @Insert("DELETE FROM student_attend_course WHERE `index` = #{index} AND course_id = #{courseId}")
    int delete(@Param("index") Long index, @Param("courseId") Long courseId);
    /**
     * 根据课程id获取该课程所有学生的信息
     * 该接口可能重复 *
     */
    @Select("SELECT user.id AS stuId, user.name AS name, user.school, user.gender, user.email " +
            "FROM user " +
            "JOIN student_attend_course sac ON user.`index` = sac.`index` " +
            "WHERE user.role = 0 AND sac.course_id = #{courseId};")
    ArrayList<StudentInfoDto> getCourseStudents(@Param("courseId") Long courseId);
    /**
     * 根据课程id查找不在该课程下的所有学生的信息，即该课程的可选课学生名单
     */
    @Select("SELECT u.id stuId, u.name AS name, u.school , u.gender , u.email " +
            "FROM vse.user u " +
            "WHERE NOT EXISTS ( " +
            "  SELECT * " +
            "  FROM vse.student_attend_course " +
            "  WHERE u.index = vse.student_attend_course.index " +
            "  AND course_id = #{courseId} " +
            ");")
    ArrayList<StudentInfoDto> getCourseFreeStudents(long courseId);
    /**
     * 根据用户index（数据库id）查找该用户所有实验的信息
     */
    @Select("SELECT * " +
            "FROM student_attend_course " +
            "INNER JOIN course_experiment " +
            "ON student_attend_course.course_id = course_experiment.course_id " +
            "WHERE student_attend_course.index = #{index};")
    ArrayList<CourseExperiment> getExperimentByIndex(long index);
    /**
     * 根据用户index（数据库id）查找该用户所有课程的id
     * select参数可以直接改成course_id *
     */
    @Select("SELECT * " +
            "FROM student_attend_course " +
            "WHERE student_attend_course.index = #{index};")
    ArrayList<StudentAttendCourse> getCourseByIndex(long index);
    /**
     * 根据用户index（数据库id）查找该用户所有课程的具体信息
     */
    @Select("SELECT course.course_id, course.course_name, course.semester, course.year " +
            "FROM student_attend_course " +
            "JOIN course ON student_attend_course.course_id = course.course_id " +
            "WHERE student_attend_course.index = #{index} " +
            "LIMIT 1;")
    CourseBasicInfoDto getCourseInfoByIndex(long index);
}
