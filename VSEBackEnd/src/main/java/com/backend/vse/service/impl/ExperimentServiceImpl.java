package com.backend.vse.service.impl;

import com.backend.vse.dto.*;
import com.backend.vse.entity.Experiment;
import com.backend.vse.entity.TeacherAssignExperiment;
import com.backend.vse.exception.DataNotFoundException;
import com.backend.vse.exception.DatabaseOperationException;
import com.backend.vse.mapper.ExperimentMapper;
import com.backend.vse.service.ExperimentService;
import com.backend.vse.common.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // Lombok 自动生成构造函数
@Slf4j
public class ExperimentServiceImpl implements ExperimentService {
    private final ExperimentMapper experimentMapper;

    //=================== 基础CRUD操作 ===================//
    @Override
    @Transactional
    public void addExperiment(Experiment experiment) {
        try {
            int affectedRows = experimentMapper.insert(experiment);
            if (affectedRows <= 0) {
                throw new DatabaseOperationException(
                        ResponseCode.SYSTEM_DB_ERROR,
                        "实验创建失败，可能该实验已存在"
                );
            }
        } catch (DataAccessException e) {
            throw handleDatabaseException(e, "创建实验");
        }
    }

    @Override
    @Transactional
    public void deleteExperiment(Long experimentId) {
        try {
            int affectedRows = experimentMapper.deleteById(experimentId);
            if (affectedRows <= 0) {
                throw new DataNotFoundException("实验不存在: " + experimentId);
            }
        } catch (DataAccessException e) {
            throw handleDatabaseException(e, "删除实验");
        }
    }


    @Override
    @Transactional
    public void updateExperimentBook(UploadExperimentFileDto uploadExperimentFileDto) {
        try {
            int affectedRows = experimentMapper.updateExperimentBook(
                    uploadExperimentFileDto.getCourseId(),
                    uploadExperimentFileDto.getExperimentId(),
                    uploadExperimentFileDto.getTeacherIndex(),
                    uploadExperimentFileDto.getFile());
            if (affectedRows <= 0) {
                throw new DataNotFoundException("课程实验配置不存在");
            }
        } catch (DataAccessException e) {
            throw handleDatabaseException(e, "更新课程实验指导书");
        }
    }

    @Override
    @Transactional
    public void updateExperimentReportTemplate(UploadExperimentFileDto uploadExperimentFileDto) {
        try {
            int affectedRows = experimentMapper.updateReportTemplate(
                    uploadExperimentFileDto.getCourseId(),
                    uploadExperimentFileDto.getExperimentId(),
                    uploadExperimentFileDto.getTeacherIndex(),
                    uploadExperimentFileDto.getFile());
            if (affectedRows <= 0) {
                throw new DataNotFoundException("课程实验配置不存在");
            }
        } catch (DataAccessException e) {
            throw handleDatabaseException(e, "更新课程实验报告模板");
        }
    }

    //=================== 课程关联操作 ===================//
    @Override
    @Transactional
    public void insertExperimentInCourse(Long courseId, Long experimentId, Long teacherIndex) {
        try {
            int affectedRows = experimentMapper.insertExperimentInCourse(courseId, experimentId, teacherIndex);
            if (affectedRows <= 0) {
                throw new DatabaseOperationException(
                        ResponseCode.SYSTEM_DB_ERROR,
                        "课程关联实验失败"
                );
            }
        } catch (DataAccessException e) {
            throw handleDatabaseException(e, "关联实验到课程");
        }
    }

    @Override
    @Transactional
    public void modifyExperimentInCourse(ModifyExperimentDto modifyExperimentDto) {
        try {
            int affectedRows = experimentMapper.updateExperimentInCourse(modifyExperimentDto);
            if (affectedRows <= 0) {
                throw new DataNotFoundException("课程实验配置不存在");
            }
        } catch (DataAccessException e) {
            throw handleDatabaseException(e, "更新课程实验配置");
        }
    }

    @Override
    @Transactional
    public void deleteExperimentInCourse(Long courseId, Long experimentId) {
        try {
            int affectedRows = experimentMapper.deleteExperimentInCourse(courseId, experimentId);
            if (affectedRows <= 0) {
                throw new DataNotFoundException("课程实验不存在");
            }
        } catch (DataAccessException e) {
            throw handleDatabaseException(e, "删除课程与实验的关联");
        }
    }

    //=================== 查询操作 ===================//
    @Override
    @Transactional(readOnly = true)
    public List<Experiment> selectAllExperiments() {
        List<Experiment> experimentList = Optional.ofNullable(experimentMapper.selectAllExperiments())
                .orElseThrow(() -> new DataNotFoundException("未找到所有实验数据"));
        if(experimentList.isEmpty()){
            throw new DataNotFoundException("实验数据为空");
        }
        return experimentList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExperimentDto> selectExperimentByCourseId(Long courseId) {
        List<TeacherAssignExperiment> courseExperimentList = Optional
                .ofNullable(experimentMapper.selectExperimentsByCourseId(courseId))
                .orElseThrow(()->new DataNotFoundException("未找到该课程的实验数据"));

        return courseExperimentList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Experiment> selectExceptExperimentByCourseId(Long courseId) {
        // 查找所有实验的信息
        List<Experiment> allExperiments = Optional
                .ofNullable(experimentMapper.selectAllExperiments())
                .orElseThrow(()->new DataNotFoundException("未找到所有实验数据"));

        // 根据课程id查找该课程下所有实验的信息
        List<TeacherAssignExperiment> teacherAssignExperiments = Optional
                .ofNullable(experimentMapper.selectExperimentsByCourseId(courseId))
                .orElseThrow(()->new DataNotFoundException("未找到该课程的实验数据"));

        // 使用Set存储课程下的实验ID，避免重复查找
        Set<Long> assignedExperimentIds = teacherAssignExperiments.stream()
                .map(TeacherAssignExperiment::getExperimentId)
                .collect(Collectors.toSet());

        // 过滤不在assignedExperimentIds中的实验并返回结果
        return allExperiments.stream()
                .filter(ex -> !assignedExperimentIds.contains(ex.getExperimentId()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ExperimentContentDto selectExperimentContentByExperimentIdAndCourseId(Long experimentId,Long courseId) {
        //根据实验id查找该实验的所有信息
        Experiment experiment = Optional
                .ofNullable(experimentMapper.selectExperimentById(experimentId))
                .orElseThrow(()->new DataNotFoundException("未找到该课程数据"));

        TeacherAssignExperiment teacherAssignExperiment = Optional
                .ofNullable(experimentMapper.getExperimentContentByExperimentIdAndCourseId(experimentId,courseId))
                .orElseThrow(()->new DataNotFoundException("该实验未在该课程中"));

        return new ExperimentContentDto(experiment.getExperimentName(),teacherAssignExperiment.getReportTemplate(),teacherAssignExperiment.getExperimentBook());
    }

    @Override
    @Transactional(readOnly = true)
    public String getExperimentBook(Long courseId, Long experimentId, Long teacherIndex) {
        return getExperimentAllContent(courseId,experimentId,teacherIndex).getExperimentBook();
    }

    @Override
    @Transactional(readOnly = true)
    public String getExperimentReportTemplate(Long courseId, Long experimentId, Long teacherIndex) {
        return getExperimentAllContent(courseId,experimentId,teacherIndex).getReportTemplate();
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherAssignExperiment getExperimentAllContent(Long courseId, Long experimentId, Long teacherIndex) {
        return Optional.ofNullable(
                experimentMapper.getExperimentByCouIdExpIdTeaIndex(courseId,experimentId,teacherIndex))
                .orElseThrow(()->new DataNotFoundException("未找到该实验相关信息"));
    }

    //=================== 辅助方法 ===================//
    private ExperimentDto convertToDto(TeacherAssignExperiment assignment) {
        Experiment experiment = Optional
                .ofNullable(experimentMapper.selectExperimentById(assignment.getExperimentId()))
                .orElseThrow(() -> new DataNotFoundException(
                        "实验不存在: " + assignment.getExperimentId()
                ));

        return new ExperimentDto(
                experiment.getExperimentId(),
                experiment.getKind(),
                experiment.getExperimentName(),
                assignment.getExperimentBook(),
                assignment.getReportTemplate(),
                assignment.getExperimentFullScore(),
                assignment.getStartTime(),
                assignment.getEndTime()
        );
    }

    private DatabaseOperationException handleDatabaseException(
            DataAccessException e, String operation
    ) {
        log.error("数据库操作异常[{}] {}",
                operation,
                e.getMessage());

        return new DatabaseOperationException(
                ResponseCode.SYSTEM_DB_ERROR,
                operation + "失败"
        );
    }
}
