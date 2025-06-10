package com.backend.vse.service.impl;

import com.backend.vse.common.ResponseCode;
import com.backend.vse.dto.TeacherManageInfoDto;
import com.backend.vse.exception.*;
import com.backend.vse.service.TeacherService;
import com.backend.vse.common.Result;
import com.backend.vse.dto.TeacherImportDto;
import com.backend.vse.entity.Teacher;
import com.backend.vse.entity.User;
import com.backend.vse.mapper.TeacherMapper;
import com.backend.vse.mapper.UserMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor // Lombok 自动生成构造函数
public class TeacherServiceImpl implements TeacherService {
    private final TeacherMapper teacherMapper;
    private final UserMapper userMapper;
    private final UserServiceImpl userService;

    @Transactional
    public Result<String> importTeacher(TeacherImportDto teacherImportDto) {
        try {
            // 参数校验
            validateStudent(teacherImportDto);
            // 创建并保存用户信息
            User user = createUser(teacherImportDto);
            int affectedRows=userMapper.insert(user);
            if (affectedRows <= 0) {
                throw new DatabaseOperationException(
                        ResponseCode.SYSTEM_DB_ERROR,
                        "导入教师信息：插入user表失败"
                );
            }
            // 获取保存后的用户信息（包含生成的userIndex）
            User savedUser = Optional.ofNullable(
                    userService.findUserById(user.getId())
            ).orElseThrow(() -> new DataNotFoundException("用户信息保存失败"));

            // 创建并保存教师信息
            Teacher teacher = createTeacher(teacherImportDto,savedUser.getUserIndex());
            affectedRows=teacherMapper.insert(teacher);
            if (affectedRows <= 0) {
                throw new DatabaseOperationException(
                        ResponseCode.SYSTEM_DB_ERROR,
                        "导入教师信息：插入teacher表失败"
                );
            }
            log.info("成功导入教师信息，工号：{}", teacherImportDto.getId());
            return Result.success("成功导入教师信息");

        } catch (Exception e) {
            log.error("导入教师信息失败，工号：{}，原因：{}", teacherImportDto.getId(), e.getMessage());
            throw new BusinessException("导入教师信息失败：" + e.getMessage());
        }
    }
    /**
     * 创建用户对象
     * @param dto 导入数据DTO
     * @return 用户对象
     */
    private User createUser(TeacherImportDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setPassword(dto.getId()); // 建议使用加密后的密码
        user.setEmail(dto.getEmail());
        user.setStatus((byte)0);
        user.setRole("teacher");
        return user;
    }

    /**
     * 创建教师对象
     * @param dto 导入数据DTO
     * @param userIndex 用户索引
     * @return 教师对象
     */
    private Teacher createTeacher(TeacherImportDto dto, Long userIndex) {
        Teacher teacher = new Teacher();

        teacher.setDepartment(dto.getDepartment());
        teacher.setUserIndex(userIndex);
        return teacher;
    }

    private void validateStudent(TeacherImportDto dto) {
        if (dto == null) {
            throw new DataEmptyException("导入数据不能为空");
        }
        if (StringUtils.isBlank(dto.getId())) {
            throw new InvalidParameterException("工号不能为空");
        }
        if (StringUtils.isBlank(dto.getName())) {
            throw new InvalidParameterException("姓名不能为空");
        }
        if (StringUtils.isBlank(dto.getEmail())) {
            throw new InvalidParameterException("邮箱不能为空");
        }
        if (StringUtils.isBlank(dto.getDepartment())) {
            throw new InvalidParameterException("学院不能为空");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeacherManageInfoDto> fetchTeacherInfo(){
        return Optional.ofNullable(teacherMapper.fetchTeacherInfo())
                .orElseThrow(() -> new DataNotFoundException("未找到教师的信息"));
    }
}