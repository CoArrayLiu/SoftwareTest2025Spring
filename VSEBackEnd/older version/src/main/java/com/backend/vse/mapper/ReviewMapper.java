package com.backend.vse.mapper;

import com.backend.vse.dto.Account;
import com.backend.vse.dto.CourseExperimentDto;
import com.backend.vse.dto.StudentSimpleDto;
import com.backend.vse.entity.Course;
import com.backend.vse.entity.ExperimentReport;
import com.backend.vse.entity.ExperimentReview;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface ReviewMapper extends BaseMapper<ExperimentReview> {
    /**
     * 根据课程id查找参与该课程的学生的相关信息
     * user.role = 0代表该用户是学生
     */
    @Select("SELECT user.`index` stuId, user.id schoolNumber, user.name stuName, user.school " +
            "FROM user, student_attend_course sac " +
            "WHERE user.role = 0 AND sac.`index` = user.`index` AND sac.course_id = #{courseId};")
    ArrayList<StudentSimpleDto> getCourseStudents(@Param("courseId") Long courseId);
    /**
     * 根据课程id和实验id双主键查找该课程下该实验的所有信息
     * 不同之处在于可以同时从experiment_submit和experiment_review表中获取到用户对应的报告内容和成绩
     */
    @Select("SELECT es.report_id, " +
            "       es.`index` stuId, " +
            "       es.experiment_id, " +
            "       es.course_id, " +
            "       es.content reportUrl, " +
            "       es.time    submitTime, " +
            "       er.time    reviewTime, " +
            "       er.score " +
            "FROM experiment_submit es LEFT JOIN experiment_review er ON es.report_id = er.report_id " +
            " WHERE es.course_id = #{courseId} AND es.experiment_id = #{experimentId}")
    ArrayList<ExperimentReport> getStudentSubmitList(@Param("courseId") Long courseId, @Param("experimentId") Long experimentId);
    /**
     * 根据课程id查找该课程下所有实验的所有信息
     * 不同之处在于可以同时从experiment和course_experiment表中获取到报告的开始结束时间和成绩
     */
    @Select("SELECT e.experiment_name, ce.experiment_id, ce.start_time, ce.end_time, ce.score " +
            "from experiment e,course_experiment ce " +
            "WHERE e.experiment_id = ce.experiment_id AND ce.course_id = #{courseId};")
    ArrayList<CourseExperimentDto> getCourseExperimentList(@Param("courseId") Long courseId);
    /**
     * 在experiment_review表中插入某一报告对应的成绩和评论人
     * 参数为：report_id, reviewerId, role, score
     */
    @Insert("INSERT INTO experiment_review (report_id, `index`, role, time, score) " +
            "VALUES (#{reportId}, #{reviewerId}, #{role}, current_timestamp, #{score}) " +
            "ON DUPLICATE KEY UPDATE `index` = #{reviewerId}, " +
            "                        role    = #{role}, " +
            "                        time    = current_timestamp, " +
            "                        score   = #{score} ")
    Boolean setReportScore(@Param("reportId") Long reportId, @Param("reviewerId") Long reviewerId, @Param("role") Boolean reviewRole, @Param("score") Float score);
    /**
     * 根据课程id查找该课程的所有信息
     * 该接口可能重复 *
     */
    @Select("SELECT * FROM course WHERE course_id = #{id}")
    Course getCourseInfo(@Param("id") Long courseId);
    /**
     * 根据课程id查找该课程下所有学生的信息
     * 可能与getCourseStudents接口存在重复 *
     */
    @Select("SELECT name, age, gender, email, avatar " +
            "FROM user, student_attend_course s " +
            "WHERE user.`index` = s.`index` AND s.course_id = #{courseId};")
    ArrayList<Account> getStudentList(@Param("courseId") Long courseId);
    /**
     * 从 t_all_school 表中查找名称中包含指定关键字 key 的学校名称，并返回这些名称的列表
     */
    @Select("SELECT name FROM vse.t_all_school WHERE LOCATE(#{key}, name) > 0")
    ArrayList<String> searchSchoolNameList(@Param("key") String key);
    /**
     * 根据用户id（学号）修改用户信息，包括name、gender、email、age
     */
    @Update("UPDATE vse.user " +
            "SET name  = #{name}, " +
            "    gender= #{gender}, " +
            "    email= #{email}, " +
            "    age= #{age} " +
            "WHERE `index` = #{userId};")
    Boolean changeUserAccount(@Param("name") String name,@Param("gender") String gender,@Param("email") String email,@Param("age") Integer age, @Param("userId") Long userId);
}
