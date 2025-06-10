package com.backend.vse.mapper;

import com.backend.vse.entity.CourseExperiment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

/**
 * 根据课程id和实验id双主键，从课程-实验表中搜索两者是否匹配
 * 若匹配，则返回该表中所有信息，否则返回空
 * LIMIT 1 确保只返回一条结果
 */
@Mapper
public interface CourseExperimentMapper extends BaseMapper<CourseExperiment> {
    @Select("SELECT * FROM course_experiment " +
            "WHERE course_id = #{courseId} AND experiment_id = #{experimentId} LIMIT 1")
    CourseExperiment getOneCourseExperiment(@Param("courseId") Long courseId, @Param("experimentId") Long experimentId);
}
