package com.backend.vse.service.impl;

import com.backend.vse.dto.CourseBasicInfoDto;
import com.backend.vse.entity.Course;
import com.backend.vse.entity.StudentAttendCourse;
import com.backend.vse.entity.TeacherTeachCourse;
import com.backend.vse.entity.User;
import com.backend.vse.mapper.CourseMapper;
import com.backend.vse.mapper.StudentAttendCourseMapper;
import com.backend.vse.mapper.TeacherTeachCourseMapper;
import com.backend.vse.mapper.UserMapper;
import com.backend.vse.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TeacherTeachCourseMapper teacherTeachCourseMapper;
    @Autowired
    StudentAttendCourseMapper studentAttendCourseMapper;
    @Autowired
    UserMapper userMapper;


    @Override
    public int insertOneCourse(Course course) {
        // courseMapper中疑似没有定义insert函数，可能直接默认为朴素的数据库insert方法
        return courseMapper.insert(course);
    }

    @Override
    public int insertOneTeach(TeacherTeachCourse teacherTeachCourse) {
        if(teacherTeachCourse == null){
            return 0;
        }
        //如果没有传角色，就在这里填补角色
        if(teacherTeachCourse.getRole() == null){
            Long index = teacherTeachCourse.getIndex();
            User user = userMapper.selectByIndex(index);
            Byte role = user.getRole();
            teacherTeachCourse.setRole(role);
        }
        // 根据教师index、课程id和用户role在teacher_teach_course表中插入教师所授课程的对应关系
        return teacherTeachCourseMapper.insert(teacherTeachCourse.getIndex(),teacherTeachCourse.getCourseId(), teacherTeachCourse.getRole());
    }

    @Override
    public int insertOneAttend(StudentAttendCourse studentAttendCourse) {
        if(studentAttendCourse == null){
            return 0;
        }
        // 根据课程id和用户index（数据库id）向student_attend_course表中插入学生信息
        return studentAttendCourseMapper.insert(studentAttendCourse.getIndex(),studentAttendCourse.getCourseId());
    }

    @Override
    public List<CourseBasicInfoDto> getCoursesByTeacher(Long index) {
        List<Course> courseList = courseMapper.getCoursesBySemester(index);
        return courseList.stream()
                .map(CourseBasicInfoDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public StudentAttendCourse getCourseByIndex(Long index) {
        //根据学生index（数据库id）查找该用户所有课程的id
        ArrayList<StudentAttendCourse> courses=studentAttendCourseMapper.getCourseByIndex(index);
        //只获取搜索到的第一门课程的id
        return courses.get(0);
    }

    @Override
    public CourseBasicInfoDto getCourseInfoByIndex(Long index) {
        return studentAttendCourseMapper.getCourseInfoByIndex(index);
    }
}
