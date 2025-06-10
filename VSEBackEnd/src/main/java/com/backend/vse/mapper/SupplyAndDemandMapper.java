package com.backend.vse.mapper;

import com.backend.vse.entity.SupplyAndDemand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SupplyAndDemandMapper extends BaseMapper<SupplyAndDemand> {
    /**
     * 获取学生在某课程的报价金额
     * 根据课程ID（courseId）和学生索引（studentIndex），查询该学生在该课程中的报价金额
     * @param courseId 课程ID
     * @param studentIndex 学生索引
     * @return 返回学生的报价金额
     */
    @Select("SELECT money FROM SupplyAndDemand WHERE course_id = #{courseId} AND student_index = #{studentIndex}")
    Integer getQuoteMoneyOfStudent(@Param("courseId") Long courseId,
                               @Param("studentIndex") Long studentIndex);
    /**
     * 获取某课程的所有报价记录
     * 根据课程ID（courseId），查询该课程下的所有报价记录
     * @param courseId 课程ID
     * @return 返回该课程的所有报价记录列表
     */
    @Select("SELECT * FROM SupplyAndDemand WHERE course_id = #{courseId}")
    List<SupplyAndDemand> getQuoteAllByCourseId(@Param("courseId") Long courseId);

    /**
     * 删除某一课程的所有报价
     * 根据课程ID（courseId），删除该课程下的所有报价记录
     * @param courseId 课程ID
     * @return 返回删除操作的影响行数
     */
    @Delete("DELETE FROM SupplyAndDemand WHERE course_id = #{courseId}")
    int deleteQuoteByCourseId(@Param("courseId") Long courseId);
}