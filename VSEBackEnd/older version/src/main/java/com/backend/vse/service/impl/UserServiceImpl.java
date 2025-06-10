package com.backend.vse.service.impl;

import com.backend.vse.dto.ExperimentScoreDto;
import com.backend.vse.dto.StudentCourseInfoDto;
import com.backend.vse.entity.User;
import com.backend.vse.mapper.ExperimentMapper;
import com.backend.vse.mapper.UserMapper;
import com.backend.vse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 2051196 刘一飞
 * @Date 2023/3/30
 * @JDKVersion 17.0.4
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ExperimentMapper experimentMapper;
    /**
     * 根据用户email和学校名查找对应用户的所有信息
     */
    @Override
    public User findUserByIdAndSchool(String email, String school) {
        return userMapper.selectByIDAndSchool(email, school);
    }
    /**
     * 根据用户index（数据库id）查找对应用户的所有信息
     */
    @Override
    public User findUserByIndex(Long index) {
        return userMapper.selectByIndex(index);
    }
    /**
     * 根据用户id（学号）查找对应用户的所有信息
     */
    @Override
    public User findUserById(Long id) {
        return userMapper.selectById(id);
    }
    /**
     * 更新用户密码的同时也更新用户的账户状态
     * 用于初次注册时
     */
    @Override
    public Integer activateUserAccount(String email, String password, Byte status) {
        return userMapper.updatePassword(email, password) & userMapper.updateStatus(email, status);
    }
    /**
     * 根据用户id查找到对应用户，并修改其头像
     */
    @Override
    public void updateAvatar(String url, Long Id) {
        userMapper.updateUserAvatar(url,Id);
    }

    /**
     * 根据学生index与课程id，返回该学生的基本个人信息，以及在该课程中的所有实验分数
     * 该接口待讨论 对于获取个人信息的接口 *
     */
    @Override
    public StudentCourseInfoDto selectStudentCourseInfoByIndexAndCourseId(Long index, Long courseId) {
        StudentCourseInfoDto studentCourseInfoDto = userMapper.selectStudentCourseInfoByIndexAndCourseId(index,courseId);
        List<ExperimentScoreDto> experimentScoreDtoList = experimentMapper.selectExperimentScoreByIndexAndCourseId(index,courseId);
        studentCourseInfoDto.setExperimentList(experimentScoreDtoList);
        return studentCourseInfoDto;
    }
    /**
     * 根据用户邮箱查找到对应用户，并修改其密码
     */
    @Override
    public Integer updatePassword(String email, String password) {
        return userMapper.updatePassword(email, password);
    }
}
