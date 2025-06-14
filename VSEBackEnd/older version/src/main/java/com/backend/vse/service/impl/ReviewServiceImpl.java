package com.backend.vse.service.impl;

import com.backend.vse.dto.Account;
import com.backend.vse.dto.CourseExperimentDto;
import com.backend.vse.dto.StudentSimpleDto;
import com.backend.vse.dto.StudentSubmitDto;
import com.backend.vse.entity.Course;
import com.backend.vse.entity.ExperimentReport;
import com.backend.vse.entity.User;
import com.backend.vse.mapper.ReviewMapper;
import com.backend.vse.service.ReviewService;
import com.backend.vse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author 赵帅涛
 * @date 2023/04/14
 */
@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewMapper reviewMapper;

    @Autowired
    UserService userService;

    @Override
    public ArrayList<StudentSubmitDto> getStudentSubmitLogList(Long courseId, Long experimentId) {
        // 获取学生列表
        ArrayList<StudentSimpleDto> studentList = reviewMapper.getCourseStudents(courseId);
        // 按学生id排序
        studentList.sort(Comparator.comparing(StudentSimpleDto::getStuId));

        // 获取提交记录
        ArrayList<ExperimentReport> submitList = reviewMapper.getStudentSubmitList(courseId, experimentId);
        // 按学生id排序
        submitList.sort(Comparator.comparing(ExperimentReport::getStuId));

        ArrayList<StudentSubmitDto> result = new ArrayList<>();
        int lastSubmitIndex = 0;
        // 每个学生只能对应一条提交记录
        // 不是所有学生都会提交
        // 该段代码或许还可再优化
        for (int i = 0; i < studentList.size(); i++) {
            StudentSubmitDto temp = new StudentSubmitDto();

            StudentSimpleDto student = studentList.get(i);
            temp.setStuId(student.getStuId());
            temp.setStuName(student.getStuName());
            temp.setSchoolNumber(student.getSchoolNumber());

            for (int j = lastSubmitIndex; j <= i && j < submitList.size(); j++) {
                ExperimentReport submit = submitList.get(j);
                if (student.getStuId().equals(submit.getStuId())) {
                    temp.setReportId(submit.getReportId());
                    temp.setReportUrl(submit.getReportUrl());
                    temp.setSubmitTime(submit.getSubmitTime());
                    temp.setScore(submit.getScore());
                    temp.setReviewTime(submit.getReviewTime());
                    lastSubmitIndex = j + 1;
                    break;
                }
            }
            result.add(temp);
        }
        return result;
    }

    @Override
    public boolean giveScoreOfReport(Long reportId, Float score, Long reviewerId, Boolean reviewerRole) {
        User currentUser = userService.findUserByIndex(reviewerId);
        // 验证角色，只有教师才能给分
        // 个人感觉该段代码不严谨
        if ((currentUser.getRole() == 1) == reviewerRole)
            return reviewMapper.setReportScore(reportId, reviewerId, reviewerRole, score);
        else return false;
    }

    @Override
    public ArrayList<CourseExperimentDto> getCourseExperimentList(Long experimentId) {
        return reviewMapper.getCourseExperimentList(experimentId);
    }

    @Override
    public Course getCourseInfo(Long courseId) {
        return reviewMapper.getCourseInfo(courseId);
    }

    @Override
    public ArrayList<Account> getStudentList(Long courseId) {
        return reviewMapper.getStudentList(courseId);
    }

    @Override
    public ArrayList<String> getSchoolNameList(String key) {
        return reviewMapper.searchSchoolNameList(key);
    }

    //该接口可优化
    public Boolean changeUserBaseInfo(Account account, Long userId) {
        if (account == null) {
            return false;
        }
        if (account.getName() == null || account.getGender() == null || account.getEmail() == null || account.getAge() == null) {
            return false;
        }
        return reviewMapper.changeUserAccount(account.getName(), account.getGender(), account.getEmail(), account.getAge(), userId);
    }
}
