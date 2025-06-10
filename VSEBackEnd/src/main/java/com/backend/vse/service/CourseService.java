package com.backend.vse.service;

import com.backend.vse.dto.CourseBasicInfoDto;
import com.backend.vse.dto.StudentCourseScoreDto;
import com.backend.vse.entity.Course;
import com.backend.vse.entity.TeacherStudentCourse;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface CourseService {
    /**
     * 获取所有课程的基本信息
     * 该方法返回系统中所有课程的基本信息列表
     * @return 返回课程基本信息的列表（List<CourseBasicInfoDto> 类型）
     */
    List<CourseBasicInfoDto> getAllCourse();

    /**
     * 插入一门新的课程
     * 该方法将新创建的课程插入到数据库中
     * @param course 要插入的课程对象（Course 类型）
     * @return 返回插入操作的结果，成功返回 1，失败返回 0
     */
    int insertOneCourse(Course course);

    /**
     * 根据教师的索引获取该教师所教授的所有课程
     * 该方法返回指定教师所教授的所有课程的基本信息
     * @param teacherIndex 教师的索引（Long 类型）
     * @return 返回该教师教授课程的基本信息列表（List<CourseBasicInfoDto> 类型）
     */
    List<CourseBasicInfoDto> getCoursesByTeacher(Long teacherIndex);

    /**
     * 插入一条课程-学生-教师的关联记录
     * 该方法用于插入一条记录，表示某个学生选修了某个课程，并由某位教师教授
     * @param teacherStudentCourse 课程-学生-教师关联信息（TeacherStudentCourse 类型）
     * @return 返回插入操作的结果，成功返回 1，失败返回 0
     */
    int insertCourseStudentTeacher(TeacherStudentCourse teacherStudentCourse);

    /**
     * 获取课程是否开启了报价
     * 该方法检查指定课程是否允许学生进行报价
     * @param courseId 课程ID（Long 类型）
     * @return 返回布尔值，true表示该课程允许报价，false表示不允许
     */
    boolean getQuote(Long courseId);

    /**
     * 切换课程的报价状态
     * 该方法用于切换指定课程的报价状态（从开启到关闭或从关闭到开启）
     * @param courseId 课程ID（Long 类型）
     * @return 返回更新操作的结果，成功返回 1，失败返回 0
     */
    int changeQuote(Long courseId);

    /**
     * 根据学生索引获取该学生所在课程的ID
     * 该方法根据学生的索引查询其所选的课程ID
     * @param studentIndex 学生的索引（Long 类型）
     * @return 返回学生所选课程的ID（Long 类型）
     */
    long getCourseIdByStudentIndex(Long studentIndex);

    /**
     * 根据课程ID获取课程的所有信息
     * 该方法根据课程ID查询并返回该课程的完整信息
     * @param courseId 课程ID（Long 类型）
     * @return 返回课程的所有信息（Course 类型）
     */
    Course getCourseAllById(Long courseId);

    /**
     * 获取学生在某门课程中的基本信息
     * 该方法返回学生在指定课程中的信息，包括课程成绩等
     * @param studentIndex 学生索引（Long 类型）
     * @param courseId 课程ID（Long 类型）
     * @return 返回学生在该课程中的信息（StudentCourseScoreDto 类型）
     */
    StudentCourseScoreDto getStudentCourseInfo(Long studentIndex, Long courseId);
}