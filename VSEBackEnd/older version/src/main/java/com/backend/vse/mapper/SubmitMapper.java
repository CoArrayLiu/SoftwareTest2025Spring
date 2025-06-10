package com.backend.vse.mapper;

import com.backend.vse.entity.ExperimentSubmit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;

@Mapper
public interface SubmitMapper extends BaseMapper<ExperimentSubmit> {
    /**
     * 根据用户index和实验id查找用户的某一已提交的实验的所有提交信息
     */
    @Select("SELECT * FROM experiment_submit WHERE `index`=#{index} AND experiment_id=#{experimentId}")
    ExperimentSubmit selectByIndexAndExperimentId(@Param("index") Long index, @Param("experimentId") Long experimentId);
    /**
     * 在experiment_submit表中插入所提交的实验内容
     * 参数：index, experiment_id, course_id, content, time
     */
    @Insert("INSERT INTO experiment_submit(`index`, experiment_id, course_id, content, time)" +
            "VALUES (#{index},#{experimentId},#{courseId},#{url},#{time})")
    boolean firstInsertSubmit(@Param("index") Long index, @Param("experimentId") Long experimentId,
                              @Param("courseId") Long courseId, @Param("url") String content, @Param("time") Timestamp submitTime);
    /**
     * 获取最近一次提交的报告的id
     */
    @Select("SELECT LAST_INSERT_ID() from experiment_submit LIMIT 1")
    Long getLastReportId();
    /**
     * 根据用户index、实验id和课程id查找用户在某一课程下某一已提交的实验的提交信息
     * LIMIT 1限制返回结果为1条
     */
    @Select("SELECT * FROM experiment_submit WHERE `index`=#{index} AND experiment_id=#{experimentId} AND course_id=#{courseId} LIMIT 1")
    ExperimentSubmit selectByIndexExpIdCourseId(@Param("index") Long index, @Param("experimentId") Long experimentId, @Param("courseId") Long courseId);
    /**
     * 根据报告id查找对应的提交报告，并更改该报告的信息
     */
    @Update("UPDATE experiment_submit " +
            "SET content = #{content} , time = #{time} " +
            "WHERE report_id = #{report_id} ")
    void updateReport(@Param("report_id") Long reportId, @Param("time") Timestamp timeStamp, @Param("content") String url);
}
