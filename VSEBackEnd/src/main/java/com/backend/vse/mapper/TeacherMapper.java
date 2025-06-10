package com.backend.vse.mapper;

import com.backend.vse.dto.TeacherManageInfoDto;
import com.backend.vse.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TeacherMapper extends BaseMapper<Teacher> {
    /**
     * 根据用户索引查询老师信息
     * 根据用户索引（userIndex），查询该用户对应的老师信息
     * @param userIndex 用户索引
     * @return 返回对应的老师信息对象
     */
    @Select("SELECT * FROM Teacher WHERE user_index = #{userIndex}")
    Teacher selectTeacherByUserIndex(@Param("userIndex") Long userIndex);

    /**
     * 获取所有教师的管理信息
     * 查询所有教师的基本信息，包括用户索引、部门、姓名、教师ID、邮箱和状态
     * @return 返回教师的管理信息列表
     */
    @Select("SELECT " +
            "u.user_index as user_index, t.department, " +
            "u.name as name, u.id as teacher_id, u.email as email, u.status as status " +
            "FROM Teacher t " +
            "LEFT JOIN User u ON t.user_index = u.user_index")
    List<TeacherManageInfoDto> fetchTeacherInfo();
}