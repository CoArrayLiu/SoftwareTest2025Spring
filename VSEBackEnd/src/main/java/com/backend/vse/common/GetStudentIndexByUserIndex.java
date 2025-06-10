package com.backend.vse.common;

import com.backend.vse.entity.Student;
import com.backend.vse.exception.BusinessException;
import com.backend.vse.interceptor.JwtInterceptor;
import com.backend.vse.mapper.StudentMapper;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * 工具类：根据当前登录用户的 userIndex 获取对应学生的 studentIndex。
 * 用于统一封装该功能逻辑，方便在其他业务逻辑中复用。
 */
@Data
public class GetStudentIndexByUserIndex {
    // 日志记录器
    private static final Logger logger = LoggerFactory.getLogger(GetStudentIndexByUserIndex.class);

    private StudentMapper studentMapper;

    /**
     * 构造函数：通过依赖注入方式传入 studentMapper。
     * @param studentMapper 学生信息操作的 Mapper
     */
    public GetStudentIndexByUserIndex(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    /**
     * 根据当前登录用户的 userIndex 查询其对应的学生 studentIndex。
     * 使用 JwtInterceptor 获取当前登录用户信息。
     * @return 返回包含 studentIndex 的 Result 对象，如果查询失败则返回失败信息。
     */
    public Long get() {
        Long loginUserIndex = JwtInterceptor.getLoginUser();
        logger.info("尝试获取当前登录用户的 studentIndex，userIndex: {}", loginUserIndex);
        Student student = Optional.ofNullable(studentMapper.selectStudentByUserIndex(loginUserIndex))
                .orElseThrow(()-> {
                    logger.warn("未找到对应的学生信息，userIndex: {}", loginUserIndex);
                    return new BusinessException(ResponseCode.BUSINESS_DATA_NOT_FOUND,"学生不存在");
                });

        logger.info("成功获取 studentIndex: {}，对应 userIndex: {}", student.getStudentIndex(), loginUserIndex);
        return student.getStudentIndex();
    }
}