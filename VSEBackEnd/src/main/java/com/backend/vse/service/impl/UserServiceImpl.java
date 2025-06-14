package com.backend.vse.service.impl;

import com.backend.vse.common.ResponseCode;
import com.backend.vse.entity.User;
import com.backend.vse.exception.DataNotFoundException;
import com.backend.vse.exception.DataProcessingException;
import com.backend.vse.exception.DatabaseOperationException;
import com.backend.vse.mapper.UserMapper;
import com.backend.vse.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor // Lombok 自动生成构造函数
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public boolean findUser(String email, String password){
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
        if(user == null||user.getStatus()==0||!user.getPassword().equals(password)){ return false;}
        if(user.getPassword().equals(password)){return true;}
        return true;
    }

    /**
     * 根据用户email和学校名查找对应用户的所有信息
     */
    @Override
    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return Optional.ofNullable(userMapper.selectOne(new LambdaQueryWrapper<User>()
                        .eq(User::getEmail, email)))
                .orElseThrow(() -> new DataNotFoundException("未找到该用户邮箱"));
    }
    /**
     * 根据用户index（数据库id）查找对应用户的所有信息
     */
    @Override
    @Transactional(readOnly = true)
    public User findUserByIndex(Long index) {
        return Optional.ofNullable(userMapper.selectOne(
                        new LambdaQueryWrapper<User>().eq(User::getUserIndex,index)))
                .orElseThrow(()->new DataNotFoundException("该用户未找到"));
    }
    /**
     * 根据用户id（学号）查找对应用户的所有信息
     */
    @Override
    @Transactional(readOnly = true)
    public User findUserById(String id) {
        return Optional.ofNullable(userMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getId,id)))
                .orElseThrow(()->new DataNotFoundException("该用户未找到"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findUsersByIds(Collection<String> ids) {
        // 只查询存在的用户的id
        List<User> users = Optional.ofNullable(userMapper.selectList(
                new LambdaQueryWrapper<User>()
                        .select(User::getId)  // 只查询id字段
                        .in(User::getId, ids)))
                .orElseThrow(()->new DataNotFoundException("未找到用户信息"));
        try{
            return users.stream()
                    .map(User::getId)
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new DataProcessingException("用户数据类型转换错误");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findUserListByIds(Collection<String> ids) {
        // 查询完整的用户信息
        return Optional.ofNullable(userMapper.selectList(
                new LambdaQueryWrapper<User>()
                        .in(User::getId, ids)))
                .orElseThrow(()->new DataNotFoundException("未找到用户信息"));
    }
    /**
     * 更新用户密码的同时也更新用户的账户状态
     * 用于初次注册时
     */
    @Override
    @Transactional
    public void activateUserAccount(String email, String password, Byte status) {
        int result = userMapper.updatePassword(email, password) & userMapper.updateStatus(email, status);
        if (result <= 0) {
            throw new DatabaseOperationException(
                    ResponseCode.SYSTEM_DB_ERROR, "更新账户信息失败"
            );
        }
    }
    /**
     * 根据用户id查找到对应用户，并修改其头像
     */
    @Override
    @Transactional
    public void updateAvatar(String url, Long Id) {
        /*此处应该添加删除旧有头像url的图片资源*/
        userMapper.updateUserAvatar(url,Id);
    }

//    /**
//     * 根据学生index与课程id，返回该学生的基本个人信息，以及在该课程中的所有实验分数
//     * 该接口待讨论 对于获取个人信息的接口 *
//     */
//    @Override
//    public StudentCourseInfoDto selectStudentCourseInfoByIndexAndCourseId(Long index, Long courseId) {
//        StudentCourseInfoDto studentCourseInfoDto = userMapper.selectStudentCourseInfoByIndexAndCourseId(index,courseId);
//        List<ExperimentScoreDto> experimentScoreDtoList = experimentMapper.selectExperimentScoreByIndexAndCourseId(index,courseId);
//        studentCourseInfoDto.setExperimentList(experimentScoreDtoList);
//        return studentCourseInfoDto;
//    }
    /**
     * 根据用户邮箱查找到对应用户，并修改其密码
     */
    @Override
    @Transactional
    public void updatePassword(String email, String password) {
        int result = userMapper.updatePassword(email, password);
        if (result <= 0) {
            throw new DatabaseOperationException(
                    ResponseCode.SYSTEM_DB_ERROR, "用户修改密码失败"
            );
        }
    }
}