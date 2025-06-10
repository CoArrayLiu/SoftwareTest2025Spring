package com.backend.vse.mapper;

import com.backend.vse.dto.SingleExperimentMaxScoreDto;
import com.backend.vse.dto.SingleExperimentScoreDto;
import com.backend.vse.dto.StudentScoreDto;
import com.backend.vse.entity.Score;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ScoreMapper extends BaseMapper<Score> {
    /**
     * 根据课程id查询该课程所有学生以及其实验的总成绩
     */
    @Select("SELECT * FROM score RIGHT OUTER JOIN student_attend_course USING(course_id) JOIN `user` WHERE course_id=#{courseId} AND `user`.`index`=student_attend_course.`index`")
    @Results(
            {
                    //column为数据库字段名，property为实体类字段名
                    @Result(column = "index", property = "index"),
                    @Result(column = "id",property = "studentId"),
                    @Result(column = "name", property = "studentName"),
                    @Result(column = "score", property = "totalScore")
            }
    )
    List<StudentScoreDto> selectScoreListByCourseId(@Param("courseId") Long courseId);
    /**
     * 根据课程id和用户index查询该用户该课程的成绩
     */
    @Select("SELECT * FROM experiment_submit JOIN experiment_review USING(report_id) JOIN experiment USING(experiment_id) WHERE experiment_submit.`index`=#{index} AND course_id=#{courseId}")
    @Results(
            {
                    //column为数据库字段名，property为实体类字段名
                    @Result(column = "experiment_name", property = "experimentName"),
                    @Result(column = "score", property = "score")
            }
    )
    List<SingleExperimentScoreDto> selectSingleExperimentScoreDto(@Param("index") Long index, @Param("courseId") Long courseId);
    /**
     * 该接口还未实现
     */
    @Select("SELECT * FROM experiment JOIN course_experiment WHERE experiment_id=#{experimentId}")
    @Results(
            {
                    //column为数据库字段名，property为实体类字段名
                    @Result(column = "experiment_id", property = "experimenId"),
                    @Result(column = "score", property = "maxScore")
            }
    )
    SingleExperimentMaxScoreDto selectSingleExperimentMaxScore(@Param("experimentId") Long experimentId);
    /**
     * 根据课程id和实验id查询该课程下某一次实验的成绩
     * 似乎未实现获取最高成绩的功能
     */
    @Select("SELECT score FROM course_experiment WHERE course_id=#{courseId} AND experiment_id=#{experimentId}")
    Integer selectMaxScore(@Param("courseId") Long courseId, @Param("experimentId") Long experimentId);
    /**
     * 根据课程id查询该课程下所有实验的id列表
     */
    @Select("SELECT experiment_id FROM course_experiment WHERE course_id=#{courseId}")
    List<Long> selectExperimentIdsByCourseId(@Param("courseId") Long courseId);
    /**
     * 根据课程id和用户index更新该用户该课程的最新成绩
     */
    @Update("UPDATE score SET score=#{score} " +
            "WHERE `index`=#{index} AND course_id=#{courseId}")
    int updateScore(@Param("index") Long index,@Param("courseId") Long courseId, @Param("score") Float score);
}
