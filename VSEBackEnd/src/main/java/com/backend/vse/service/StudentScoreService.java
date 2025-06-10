package com.backend.vse.service;

import com.backend.vse.dto.StudentExperimentScoreDto;
import com.backend.vse.dto.StudentTotalScoreDto;

import java.util.List;

public interface StudentScoreService {

    /**
     * 获取课程中所有学生的总成绩
     *
     * @param courseId 课程 ID
     * @return 学生总成绩列表
     */
    List<StudentTotalScoreDto> getTotalScoresByCourseId(Long courseId);

    /**
     * 获取课程中学生的单个实验成绩
     *
     * @param courseId 课程 ID
     * @return 学生实验成绩列表
     */
    List<StudentExperimentScoreDto> getExperimentScoresByCourseId(Long courseId);
    Float calculateAverageScore(Long courseId);
}