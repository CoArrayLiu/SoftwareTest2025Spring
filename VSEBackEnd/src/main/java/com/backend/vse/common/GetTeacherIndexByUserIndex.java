package com.backend.vse.common;

import com.backend.vse.entity.Teacher;
import com.backend.vse.exception.BusinessException;
import com.backend.vse.interceptor.JwtInterceptor;
import com.backend.vse.mapper.TeacherMapper;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * 工具类：根据当前登录用户的 userIndex 获取对应教师的 teacherIndex。
 * 用于封装该通用功能逻辑，便于在项目中复用。
 */
@Data
public class GetTeacherIndexByUserIndex {
    private TeacherMapper teacherMapper;
    // 日志记录器
    private static final Logger logger = LoggerFactory.getLogger(GetTeacherIndexByUserIndex.class);

    /**
     * 构造方法：通过构造函数注入 teacherMapper。
     * @param teacherMapper 教师数据操作 Mapper
     */
    public GetTeacherIndexByUserIndex(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    /**
     * 根据当前登录用户的 userIndex 查询其对应的教师 teacherIndex。
     * @return 返回封装的 Result 对象，成功返回 teacherIndex，失败返回错误信息。
     */
    public Long get() {
        Long loginUserIndex = JwtInterceptor.getLoginUser();
        logger.info("尝试获取当前登录用户的 teacherIndex，userIndex: {}", loginUserIndex);
        Teacher teacher = Optional
                .ofNullable(teacherMapper.selectTeacherByUserIndex(loginUserIndex))
                .orElseThrow(()-> {
                    logger.warn("未找到对应的教师信息，userIndex: {}", loginUserIndex);
                    return new BusinessException(ResponseCode.BUSINESS_DATA_NOT_FOUND,"教师不存在");
                });

        logger.info("成功获取 teacherIndex: {}，对应 userIndex: {}", teacher.getTeacherIndex(), loginUserIndex);
        return teacher.getTeacherIndex();
    }
}