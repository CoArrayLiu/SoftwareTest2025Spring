package com.backend.vse.service;

import com.backend.vse.dto.*;
import com.backend.vse.entity.Experiment;
import com.backend.vse.entity.TeacherAssignExperiment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExperimentService {
    /**
     * 添加一个实验记录
     * 该方法用于将新的实验信息插入到数据库中。
     *
     * @param experiment 包含实验信息的对象（Experiment 类型）
     */
    void addExperiment(Experiment experiment);

    /**
     * 删除指定实验的记录
     * 该方法用于根据实验 ID 删除指定的实验记录。
     *
     * @param experimentId 要删除的实验 ID（Long 类型）
     */
    void deleteExperiment(Long experimentId);

    /**
     * 获取所有实验记录
     * 该方法用于获取所有的实验记录，并返回一个包含所有实验的列表。
     *
     * @return 包含所有实验记录的列表（List<Experiment> 类型）
     */
    List<Experiment> selectAllExperiments();

    /**
     * 将实验插入到指定课程中
     * 该方法用于将一个实验与指定的课程和教师关联起来。
     *
     * @param courseId     课程 ID（Long 类型）
     * @param experimentId 实验 ID（Long 类型）
     * @param teacherIndex 教师索引（Long 类型）
     */
    void insertExperimentInCourse(Long courseId, Long experimentId, Long teacherIndex);

    /**
     * 从课程中删除指定实验
     * 该方法用于将一个实验从指定课程中删除。
     *
     * @param courseId     课程 ID（Long 类型）
     * @param experimentId 实验 ID（Long 类型）
     */
    void deleteExperimentInCourse(Long courseId, Long experimentId);

    /**
     * 修改课程中的实验信息
     * 该方法用于更新指定课程中实验的相关信息。
     *
     * @param modifyExperimentDto 修改实验的 DTO 对象（ModifyExperimentDto 类型）
     */
    void modifyExperimentInCourse(ModifyExperimentDto modifyExperimentDto);

    /**
     * 获取指定课程中的所有实验
     * 该方法用于根据课程 ID 获取该课程下所有的实验。
     *
     * @param courseId 课程 ID（Long 类型）
     * @return 包含所有实验信息的列表（List<ExperimentDto> 类型）
     */
    List<ExperimentDto> selectExperimentByCourseId(Long courseId);

    /**
     * 获取指定课程中未关联的实验
     * 该方法用于获取所有未与指定课程关联的实验。
     *
     * @param courseId 课程 ID（Long 类型）
     * @return 包含未关联实验的列表（List<Experiment> 类型）
     */
    List<Experiment> selectExceptExperimentByCourseId(Long courseId);

    /**
     * 获取指定实验的实验内容
     * 该方法用于获取指定实验在某课程下的实验内容。
     *
     * @param experimentId 实验 ID（Long 类型）
     * @param courseId 课程 ID（Long 类型）
     * @return 返回实验内容的 DTO 对象（ExperimentContentDto 类型）
     */
    ExperimentContentDto selectExperimentContentByExperimentIdAndCourseId(Long experimentId,Long courseId);

    /**
     * 更新实验书籍文件
     * 该方法用于更新实验书籍的文件。
     *
     * @param uploadExperimentFileDto 上传文件的 DTO 对象（UploadExperimentFileDto 类型）
     */
    void updateExperimentBook(UploadExperimentFileDto uploadExperimentFileDto);

    /**
     * 更新实验报告模板文件
     * 该方法用于更新实验报告模板文件。
     *
     * @param uploadExperimentFileDto 上传文件的 DTO 对象（UploadExperimentFileDto 类型）
     */
    void updateExperimentReportTemplate(UploadExperimentFileDto uploadExperimentFileDto);

    /**
     * 获取指定实验书籍的文件路径
     * 该方法用于获取指定课程和实验的实验书籍文件路径。
     *
     * @param courseId 课程 ID（Long 类型）
     * @param experimentId 实验 ID（Long 类型）
     * @param teacherIndex 教师索引（Long 类型）
     * @return 返回实验书籍的文件路径（String 类型）
     */
    String getExperimentBook(Long courseId,Long experimentId,Long teacherIndex);

    /**
     * 获取指定实验报告模板的文件路径
     * 该方法用于获取指定课程和实验的实验报告模板文件路径。
     *
     * @param courseId 课程 ID（Long 类型）
     * @param experimentId 实验 ID（Long 类型）
     * @param teacherIndex 教师索引（Long 类型）
     * @return 返回实验报告模板的文件路径（String 类型）
     */
    String getExperimentReportTemplate(Long courseId,Long experimentId,Long teacherIndex);

    /**
     * 获取指定实验的所有内容
     * 该方法用于获取指定课程和实验的所有内容，包括实验书籍、报告模板和实验内容。
     *
     * @param courseId 课程 ID（Long 类型）
     * @param experimentId 实验 ID（Long 类型）
     * @param teacherIndex 教师索引（Long 类型）
     * @return 返回实验的所有内容（TeacherAssignExperiment 类型）
     */
    TeacherAssignExperiment getExperimentAllContent(Long courseId, Long experimentId, Long teacherIndex);
}