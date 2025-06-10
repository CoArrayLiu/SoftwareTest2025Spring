package com.backend.vse.mapper;

import com.backend.vse.dto.ExperimentBriefInfo;
import com.backend.vse.dto.ExperimentScoreDto;
import com.backend.vse.entity.CourseExperiment;
import com.backend.vse.entity.Experiment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author 2050865 黄彦铭
 * @Date 2023/4/8
 * @JDKVersion 17.0.4
 */
@Mapper
public interface ExperimentMapper extends BaseMapper<Experiment> {
    /**
     * 查找所有实验的所有信息
     */
    @Select("SELECT * FROM experiment")
    List<Experiment> selectAllExperiments();
    /**
     * 根据实验id查找该实验的所有信息
     */
    @Select("SELECT * FROM experiment WHERE experiment_id=#{experimentId}")
    Experiment selectExperimentById(@Param("experimentId") Long experimentId);
    /**
     * 根据课程id查找该课程下所有实验的所有信息
     */
    @Select("SELECT * FROM course_experiment WHERE course_id=${courseId}")
    List<CourseExperiment> selectExperimentsByCourseId(@Param("courseId") Long courseId);
    /**
     * 根据用户index和课程id查找该用户在该课程下所有实验的成绩列表
     * 该接口有问题待修改 *
     */
    @Select("SELECT experiment_name,score,content FROM experiment_submit JOIN experiment USING(experiment_id) " +
            "JOIN experiment_review USING(report_id) " +
            "WHERE experiment_submit.index=#{index} AND experiment_submit.course_id=#{courseId}")
            //"WHERE experiment_submit.index=#{index} AND experiment.course_id=#{courseId}")
    @Results({
            @Result(column = "experiment_name", property = "experimentName"),
            @Result(column = "score", property = "score"),
            @Result(column = "content", property = "report")
    })
    List<ExperimentScoreDto> selectExperimentScoreByIndexAndCourseId(@Param("index") Long index,
                                                                     @Param("courseId") Long courseId);
    /**
     * 通过课程id和实验id向课程-实验表中插入某一课程与某一实验的对应关系
     */
    @Insert("INSERT INTO course_experiment (course_id, experiment_id, start_time, end_time, score, instructor, template) VALUES (${courseId}, ${experimentId}, null, null, 0, null, null)")
    int insertExperimentInCourse(@Param("courseId") Long courseId, @Param("experimentId") Long experimentId);
    /**
     * 通过课程id和实验id在课程-实验表中搜索到某一课程的某一实验，并在该实验表中更新该实验的开始时间，结束时间和分数
     * 注意：该接口需要传递的参数是整个courseExperiment类
     */
    @Update("UPDATE course_experiment SET start_time=#{courseExperiment.startTime}, end_time=#{courseExperiment.endTime}, score=#{courseExperiment.score} WHERE course_id=#{courseExperiment.courseId} AND experiment_id=${courseExperiment.experimentId}")
    int updateExperimentInCourse(@Param("courseExperiment") CourseExperiment courseExperiment);
    /**
     * 通过课程id和实验id在课程-实验表中删除某一课程与某一实验的对应关系
     */
    @Delete("DELETE FROM course_experiment WHERE course_id=${courseId} AND experiment_id=${experimentId}")
    int deleteExperimentInCourse(@Param("courseId") Long courseId, @Param("experimentId") Long experimentId);
    /**
     * 通过课程id和实验id在课程-实验表中搜索到某一课程的某一实验，并在该实验表中更新指导书的信息
     */
    @Update("UPDATE course_experiment SET instructor='${instructor}' WHERE course_id=${courseId} AND experiment_id=${experimentId}")
    int updateExperimentInstructor(@Param("courseId") Long courseId, @Param("experimentId") Long experimentId, @Param("instructor") String instructor);
    /**
     * 通过课程id和实验id在课程-实验表中搜索到某一课程的某一实验，并在该实验表中更新实验报告模版的信息
     */
    @Update("UPDATE course_experiment SET template='${template}' WHERE course_id=${courseId} AND experiment_id=${experimentId}")
    int updateExperimentTemplate(@Param("courseId") Long courseId, @Param("experimentId") Long experimentId, @Param("template") String template);
}
