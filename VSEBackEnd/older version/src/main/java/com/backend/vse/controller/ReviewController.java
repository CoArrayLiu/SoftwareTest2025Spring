package com.backend.vse.controller;

import com.backend.vse.common.Result;
import com.backend.vse.dto.Account;
import com.backend.vse.dto.CourseExperimentDto;
import com.backend.vse.dto.GiveReportScoreDto;
import com.backend.vse.dto.StudentSubmitDto;
import com.backend.vse.entity.Course;
import com.backend.vse.interceptor.JwtInterceptor;
import com.backend.vse.service.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @author 赵帅涛
 * @date 2023/04/14
 */
@Api(tags = "Review")
@RestController
@RequestMapping("review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @ApiOperation("获取当前课程的所有实验")
    @GetMapping("experimentlist")
    public Result<ArrayList<CourseExperimentDto>> getCourseExperimentList(@RequestParam("courseId") String courseId) {
        long cId;
        try {
            // 字符串转long
            cId = Long.parseUnsignedLong(courseId);
        } catch (Exception e) {
            return Result.fail(400, "参数解析错误");
        }
        ArrayList<CourseExperimentDto> result;
        try {
            //根据课程id查找该课程下所有实验的所有信息
            //不同之处在于可以同时从experiment和course_experiment表中获取到报告的开始结束时间和成绩
            result = reviewService.getCourseExperimentList(cId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(500, "数据库连接错误");
        }
        return Result.success(result);
    }

    @ApiOperation("获取当前实验的学生提交的实验报告")
    @GetMapping("reportlist")
    public Result<ArrayList<StudentSubmitDto>> getReportList(@RequestParam("courseId") String courseId, @RequestParam("experimentId") String experimentId) {
        long cId, eId;
        try {
            cId = Long.parseUnsignedLong(courseId);
            eId = Long.parseUnsignedLong(experimentId);
        } catch (Exception e) {
            return Result.fail(400, "参数解析错误");
        }
        ArrayList<StudentSubmitDto> result;

        try {
            // 返回格式是StudentSubmitDto的列表
            result = reviewService.getStudentSubmitLogList(cId, eId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(500, "数据库连接错误");
        }
        return Result.success(result);
    }

    @ApiOperation("给某实验报告打分")
    @PostMapping("score")
    public Result<Boolean> giveScore(@RequestBody GiveReportScoreDto scoreDto) {
        boolean res = false;
        Long userId = JwtInterceptor.getLoginUser();
        // userId = 199L;
        if (userId == null) {
            return Result.fail(400, "用户未登录");
        }
        scoreDto.setReviewerId(userId);
        // 默认是老师
        scoreDto.setReviewerRole(true);
        try {
            res = reviewService.giveScoreOfReport(scoreDto.getReportId(), scoreDto.getScore(), scoreDto.getReviewerId(), scoreDto.getReviewerRole());
        } catch (Exception e) {
            return Result.fail(500, "数据库连接错误");
        }
        if (!res) {
            return Result.fail(400, "无批阅权限");
        }
        return Result.success(res);
    }

    @ApiOperation("获取课程的详细信息")
    @GetMapping("course-info")
    public Result<Course> getCourseInfo(@RequestParam("courseId") String courseId) {
        long cId;
        try {
            cId = Long.parseUnsignedLong(courseId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(400, "参数解析错误");
        }
        try {
            //根据课程id查找该课程的所有信息
            return Result.success(reviewService.getCourseInfo(cId));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(400, "错误");
        }
    }

    @ApiOperation("获取课程的所有学生")
    @GetMapping("student")
    public Result<ArrayList<Account>> getStudentList(@RequestParam("courseId") String courseId) {
        long cId;
        try {
            cId = Long.parseUnsignedLong(courseId);
        } catch (Exception e) {
            return Result.fail(400, "数据解析错误");
        }
        ArrayList<Account> res;
        try {
            // 根据课程id查找该课程下所有学生的信息
            res = reviewService.getStudentList(cId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(400, "错误");
        }
        return Result.success(res);
    }

    @ApiOperation("模糊搜索学校名称")
    @GetMapping("school-name")
    public Result<ArrayList<String>> getSchoolNameList(@RequestParam("key") String key) {
        ArrayList<String> res = reviewService.getSchoolNameList(key);
        return Result.success(res);
    }

    @ApiOperation("修改用户信息")
    @PutMapping("change-userinfo")
    // 参数为Account类型，包括name、gender、email、age
    public Result<Boolean> changeUserBaseInfo(@RequestBody Account account) {
        Long userId;
        try {
            userId = JwtInterceptor.getLoginUser();
        } catch (Exception e) {
            // userId = 199L;
            return Result.fail(4001,"确实用户id");
        }
        if(userId == null){
            // userId = 199L;
            return Result.fail(4001,"确实用户id");
        }
        try {
            Boolean res = reviewService.changeUserBaseInfo(account, userId);
            return Result.success(res);
        } catch (Exception e) {
            return Result.fail(400, "数据库连接错误");
        }
    }
}
