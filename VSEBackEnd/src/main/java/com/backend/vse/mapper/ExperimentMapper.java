package com.backend.vse.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.backend.vse.dto.InstructorDto;
import com.backend.vse.dto.ModifyExperimentDto;
import com.backend.vse.entity.Experiment;
import com.backend.vse.entity.TeacherAssignExperiment;

public interface ExperimentMapper extends BaseMapper<Experiment> {
    /**
     * 查找所有实验的信息
     */
    @Select("SELECT * FROM Experiment")
    List<Experiment> selectAllExperiments();

    /**
     * 根据实验id查找实验信息
     */
    @Select("SELECT * FROM Experiment WHERE experiment_id = #{experimentId}")
    Experiment selectExperimentById(@Param("experimentId") Long experimentId);

    /**
     * 根据课程id查找课程下所有实验信息
     */
    @Select("SELECT * FROM teacher_assign_experiment WHERE course_id = #{courseId}")
    List<TeacherAssignExperiment> selectExperimentsByCourseId(@Param("courseId") Long courseId);

    /**
     * 向课程-实验表中插入对应关系
     */
    @Insert("""
            INSERT INTO teacher_assign_experiment
            (course_id, experiment_id, teacher_index, start_time, end_time, experiment_full_score, experiment_book, report_template)
            VALUES (#{courseId}, #{experimentId}, #{teacher_index}, null, null, 0, null, null)
            """)
    int insertExperimentInCourse(@Param("courseId") Long courseId, 
                                @Param("experimentId") Long experimentId,
                                @Param("teacher_index") Long teacherIndex);

    /**
     * 更新实验指导书
     */
    @Update("""
            UPDATE teacher_assign_experiment
            SET experiment_book = #{experimentBook}
            WHERE course_id = #{courseId}
            AND experiment_id = #{experimentId}
            AND teacher_index = #{teacherIndex}
            """)
    int updateExperimentBook(@Param("courseId") Long courseId,
                             @Param("experimentId") Long experimentId,
                             @Param("teacherIndex") Long teacherIndex,
                             @Param("experimentBook") String experimentBook);

    /**
     * 根据实验ID、课程ID和教师index获取实验内容
     */
    @Select("""
            SELECT *
            FROM teacher_assign_experiment
            WHERE course_id = #{courseId}
            AND experiment_id = #{experimentId}
            AND teacher_index = #{teacherIndex}
            """)
    TeacherAssignExperiment getExperimentByCouIdExpIdTeaIndex(@Param("courseId") Long courseId,
                                             @Param("experimentId") Long experimentId,
                                             @Param("teacherIndex") Long teacherIndex);

    /**
     * 上传实验报告模板
     */
    @Update("""
            UPDATE teacher_assign_experiment
            SET report_template = #{reportTemplate}
            WHERE course_id = #{courseId}
            AND experiment_id = #{experimentId}
            AND teacher_index = #{teacherIndex}
            """)
    int updateReportTemplate(@Param("courseId") Long courseId,
                             @Param("experimentId") Long experimentId,
                             @Param("teacherIndex") Long teacherIndex,
                             @Param("reportTemplate") String reportTemplate);

    /**
     * 删除课程-实验对应关系
     */
    @Delete("DELETE FROM teacher_assign_experiment WHERE course_id = #{courseId} AND experiment_id = #{experimentId}")
    int deleteExperimentInCourse(@Param("courseId") Long courseId, @Param("experimentId") Long experimentId);

    /**
     * 更新实验信息
     */
    @Update("""
            UPDATE teacher_assign_experiment
            SET start_time = #{modifyExperimentDto.startTime},
                end_time = #{modifyExperimentDto.endTime},
                experiment_full_score = #{modifyExperimentDto.score},
                experiment_content = #{modifyExperimentDto.experimentContent}
            WHERE course_id = #{modifyExperimentDto.courseId}
            AND experiment_id = #{modifyExperimentDto.experimentId}
            """)
    int updateExperimentInCourse(@Param("modifyExperimentDto") ModifyExperimentDto modifyExperimentDto);


    /**
     * 查找实验指导教师信息
     */
    @Select("""
            SELECT u.*, t.*
            FROM User u
            JOIN Teacher t ON u.user_index = t.teacher_index
            JOIN teacher_assign_experiment tae ON t.teacher_index = tae.teacher_index
            WHERE tae.teacher_index = #{teacherIndex}
            """)
    InstructorDto findInstructorByIndexAndExperiment(@Param("teacherIndex") Long teacherIndex, 
                                                    @Param("experimentId") Long experimentId);

    /**
     * 根据学生索引获取实验信息
     */
    @Select("""
            SELECT *
            FROM teacher_student_course
            INNER JOIN teacher_assign_experiment
            ON teacher_student_course.course_id = teacher_assign_experiment.course_id
            WHERE teacher_student_course.student_index = #{studentIndex}
            """)
    List<TeacherAssignExperiment> getExperimentByStudentIndex(@Param("studentIndex") long studentIndex);

    /**
     * 根据实验ID和课程ID获取实验内容
     */
    @Select("SELECT * FROM teacher_assign_experiment WHERE course_id = #{courseId} AND experiment_id = #{experimentId}")
    TeacherAssignExperiment getExperimentContentByExperimentIdAndCourseId(@Param("experimentId") Long experimentId,
                                                                         @Param("courseId") Long courseId);
}
