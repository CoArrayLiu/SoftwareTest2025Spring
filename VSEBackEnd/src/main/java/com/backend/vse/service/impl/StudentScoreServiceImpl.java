package com.backend.vse.service.impl;

import com.backend.vse.dto.StudentExperimentScoreDto;
import com.backend.vse.dto.StudentTotalScoreDto;
import com.backend.vse.exception.BusinessException;
import com.backend.vse.exception.DataNotFoundException;
import com.backend.vse.exception.DataProcessingException;
import com.backend.vse.mapper.StudentScoreMapper;
import com.backend.vse.service.StudentScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor // Lombok 自动生成构造函数
public class StudentScoreServiceImpl implements StudentScoreService {
    private final StudentScoreMapper studentScoreMapper;

    @Override
    public List<StudentTotalScoreDto> getTotalScoresByCourseId(Long courseId) {
        return studentScoreMapper.getTotalScoresByCourseId(courseId);
    }

    @Override
    public List<StudentExperimentScoreDto> getExperimentScoresByCourseId(Long courseId) {
        List<StudentExperimentScoreDto> scores = Optional
                .ofNullable(studentScoreMapper.getExperimentScoresByCourseId(courseId))
                .orElseThrow(()->new DataNotFoundException("未查询到实验成绩"));

        try{
            // 排序：先按 student_index 排序，再按 experiment_id 排序
            scores.sort(Comparator.comparing(StudentExperimentScoreDto::getStudentIndex)
                    .thenComparing(StudentExperimentScoreDto::getExperimentId));
            return scores;
        }catch (BusinessException e){
            throw new DataProcessingException("实验成绩排序异常");
        }
    }
    /**
     * 计算某一课程中所有学生的平均总成绩
     * @param courseId 课程ID
     * @return 该课程中所有学生的平均总成绩
     */
    public Float calculateAverageScore(Long courseId) {
        // 获取课程中所有学生的实验成绩列表
        List<StudentExperimentScoreDto> scores;
        try{
            scores = getExperimentScoresByCourseId(courseId);
        }catch (BusinessException e){
            throw new DataProcessingException("实验成绩排序异常");
        }

        // 用于存储每个学生的总成绩
        Map<Long, Float> studentTotalScores = new HashMap<>();

        // 计算所有学生的总成绩和总人数
        try{
            Float totalSum = 0f;
            // 计算每个学生的总成绩
            for (StudentExperimentScoreDto score : scores) {
                Long studentIndex = score.getStudentIndex();
                Float reportScore = score.getReportScore();

                // 累加成绩，存储学生的总成绩
                if(reportScore!=null)
                    studentTotalScores.put(studentIndex, studentTotalScores.getOrDefault(studentIndex, 0f) + reportScore);
            }

            // 累加每个学生的总成绩
            for (Float totalScore : studentTotalScores.values()) {
                totalSum += totalScore;
            }

            int studentCount = studentTotalScores.size();
            // 计算课程的所有学生的平均成绩
            return studentCount > 0 ? totalSum / studentCount : 0f;
        }catch (DataProcessingException e){
            throw new DataProcessingException("学生成绩处理异常");
        }
    }
}