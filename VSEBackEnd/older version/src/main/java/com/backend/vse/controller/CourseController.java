package com.backend.vse.controller;

import com.backend.vse.common.Result;
import com.backend.vse.dto.CourseBasicInfoDto;
import com.backend.vse.dto.ImportedStudentDto;
import com.backend.vse.dto.NewCourseDto;
import com.backend.vse.entity.Course;
import com.backend.vse.entity.StudentAttendCourse;
import com.backend.vse.entity.TeacherTeachCourse;
import com.backend.vse.entity.User;
import com.backend.vse.interceptor.JwtInterceptor;
import com.backend.vse.mapper.UserMapper;
import com.backend.vse.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.jsqlparser.util.validation.metadata.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Api(tags = {"Course"})
@RestController
//@RequestMapping("course")
@Transactional
public class CourseController {
    @Autowired
    CourseService courseService;
    @Autowired
    UserMapper userMapper;

    /**
     * 参数：NewCourseDto类，前端提供
     */
    @ApiOperation("新增一门课程")
    @PostMapping("addcourse")
    public Result<String> postOneCourse(@RequestBody NewCourseDto newCourseDto)
    {
        //获取当前登录的教师的index，并将其添加到newCourseDto类中
        //表示该教师拥有该课程
        Long teacherIndex = JwtInterceptor.getLoginUser();
        List<Long> teacherList = new ArrayList<>();
        teacherList.add(teacherIndex);
        newCourseDto.setTeacherList(teacherList);
        //从newCourseDto类中获取变量值
        String courseName = newCourseDto.getCourseName();
        String semester = newCourseDto.getSemester();;
        List<ImportedStudentDto> studentList = newCourseDto.getStudentList();
        int year = newCourseDto.getYear();
        //涉及到多个CRUD，注意事务回滚！
        //1.先插入课程表
        //在course表中根据newCourseDto类的数据插入一个新课程
        Course course = new Course(courseName, semester, year);
        if(courseService.insertOneCourse(course) <= 0){
            //事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail(10001,"课程的courseName,semester,year中可能传错，请检查");
        }
        //拿到插入课程后，新生成的课程ID
        Long courseId = course.getCourseId();

        //2.补足学生用户表
        //newCourseDto类中会带入一个初始的studentList列表
        //如果用户表中无该学生信息，则添加
        studentList.forEach(item -> {
            Long id = item.getId();
            String school = item.getSchool();
            //根据学生id（学号）和学校名寻找到该学生的信息
            User stu = userMapper.selectByStuIdAndSchool(id, school);
            //如果没有这个学生，就现加这个学生
            if(stu == null){
                int res = userMapper.insertUser(0L,id,item.getName(),"111111",0,
                        item.getGender(),item.getEmail(),school, (byte) 0, (byte) 0,
                        "https://pic1.zhimg.com/v2-c0649aa7bd799ee4beefa8098ca7cf16_r.jpg?source=1940ef5c");
                stu = userMapper.selectByStuIdAndSchool(id, school);
            }
        });

        try {
            //3.插入教师参与课程的表
            //遍历newCourseDto类中的teacherList，根据教师index和课程id向teacher_teach_course表中添加教师与所授课程的对应关系
            teacherList.forEach(index -> {
                if (courseService.insertOneTeach(new TeacherTeachCourse(index, courseId)) <= 0) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    throw new DatabaseException("插入失败！");
                }
            });
            //4.插入学生参与课程的表
            studentList.forEach(item -> {
                //先通过学生id（学号）和学校名来查出学生index（数据库id）
                User stu = userMapper.selectByStuIdAndSchool(item.getId(), item.getSchool());
                Long index = stu.getIndex();

                //然后执行Insert
                //根据学生index和课程id向student_attend_course表中插入学生参加课程的对应关系
                if (courseService.insertOneAttend(new StudentAttendCourse(index, courseId)) <= 0) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    throw new DatabaseException("插入失败！");
                }
            });
        }
        catch (DatabaseException e){
            return Result.fail(10001,"教师或学生列表可能传错，请检查");
        }

        return Result.success("新增课程成功！");
    }

    /**
     * 该接口似乎有功能完全不同的同名重载方法，建议修改函数名
     */
    @ApiOperation("获取一名教师的所有课程")
    @GetMapping("courses")
    public Result<List<CourseBasicInfoDto>> postOneCourse(@ApiParam(name="index", value="教师index", required = true)
                                        @RequestParam("index") Long index)
    {
        //获取教师index
        index = JwtInterceptor.getLoginUser();
        //通过教师index获取其教授的所有课程，详见getCoursesByTeacher接口
        List<CourseBasicInfoDto> courseBasicInfoDtoList = courseService.getCoursesByTeacher(index);
        //按时间降序输出，dto类已实现Comparable接口
        List<CourseBasicInfoDto> reverseList = courseBasicInfoDtoList.stream()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());

        return Result.success(reverseList);
    }
}
