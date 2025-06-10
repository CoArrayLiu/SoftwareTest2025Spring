package com.backend.vse.service.impl;

import com.backend.vse.dto.*;
import com.backend.vse.entity.CourseExperiment;
import com.backend.vse.entity.Experiment;
import com.backend.vse.mapper.ExperimentMapper;
import com.backend.vse.mapper.StudentAttendCourseMapper;
import com.backend.vse.service.ExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ExperimentServiceImpl implements ExperimentService {
    @Autowired
    ExperimentMapper experimentMapper;

    @Autowired
    StudentAttendCourseMapper studentAttendCourseMapper;

    @Override
    public List<Experiment> selectAllExperiments() {
        return experimentMapper.selectAllExperiments();
    }

    @Override
    public List<ExperimentDto> selectExperimentByCourseId(Long courseId) {
        List<CourseExperiment> courseExperimentList = experimentMapper.selectExperimentsByCourseId(courseId);
        ArrayList<ExperimentDto> res = new ArrayList<>();
        if (courseExperimentList == null || courseExperimentList.isEmpty()) {
            return res;
        }
        for (CourseExperiment cs : courseExperimentList) {
            Experiment experiment = experimentMapper.selectExperimentById(cs.getExperimentId());
            ExperimentDto experimentDto = new ExperimentDto(experiment.getExperimentId(), experiment.getExperimentName(), cs.getInstructor(), cs.getTemplate(), cs.getScore(), cs.getStartTime(), cs.getEndTime());
            res.add(experimentDto);
        }
        return res;
    }

    @Override
    public List<Experiment> selectExceptExperimentByCourseId(Long courseId) {
        //查找所有实验的信息
        List<Experiment> allExperiments = experimentMapper.selectAllExperiments();
        //根据课程id查找该课程下所有实验的信息
        List<CourseExperiment> courseExperimentList = experimentMapper.selectExperimentsByCourseId(courseId);
        ArrayList<Experiment> res = new ArrayList<>();
        //在allExperiments中搜索不在courseExperimentList中的课程，添加到res中
        for (Experiment ex: allExperiments) {
            Long experimentId = ex.getExperimentId();
            boolean flag = true;
            for (CourseExperiment cs: courseExperimentList) {
                if (Objects.equals(cs.getExperimentId(), experimentId)) {
                    flag = false;
                    break;
                }
            }
            if (flag) res.add(ex);
        }
        return res;
    }

    @Override
    public ExperimentContentDto selectExperimentContentById(Long experimentId) {
        //根据实验id查找该实验的所有信息
        Experiment experiment = experimentMapper.selectExperimentById(experimentId);
        if (experiment == null)
            return null;
        return new ExperimentContentDto(experiment);
    }

    @Override
    public ExperimentTemplateDto selectExperimentTemplateById(Long experimentId) {
        //根据实验id查找该实验的所有信息
        Experiment experiment = experimentMapper.selectExperimentById(experimentId);
        if (experiment == null)
            return null;
        return new ExperimentTemplateDto(experiment);
    }

    @Override
    public int insertExperimentInCourse(Long courseId, Long experimentId) {
        return experimentMapper.insertExperimentInCourse(courseId, experimentId);
    }

    @Override
    public int modifyExperimentInCourse(CourseExperiment courseExperiment) {
        return experimentMapper.updateExperimentInCourse(courseExperiment);
    }

    @Override
    public int deleteExperimentInCourse(Long courseId, Long experimentId) {
        return experimentMapper.deleteExperimentInCourse(courseId, experimentId);
    }

    @Override
    public int updateExperimentInstructor(Long courseId, Long experimentId, String instructor) {
        return experimentMapper.updateExperimentInstructor(courseId, experimentId, instructor);
    }

    @Override
    public int updateExperimentTemplate(Long courseId, Long experimentId, String template) {
        return experimentMapper.updateExperimentTemplate(courseId, experimentId, template);
    }

    @Override
    public String findInstructorByIndexAndExperiment(Long index, Long experimentId) {
        //建议将变量名file改为instructor
        String file="";
        //根据学生index搜索该学生参加的所有实验
        ArrayList<CourseExperiment> course_experiments= studentAttendCourseMapper.getExperimentByIndex(index);
        //遍历这些实验
        for (CourseExperiment courseExperiment: course_experiments) {
            //通过实验id获取实验完整信息
            Experiment experiment=experimentMapper.selectExperimentById(courseExperiment.getExperimentId());
            //如果查找到想搜索的实验的id
            //建议把下述代码放在for循环开始部分，可以优化性能 *
            if(Objects.equals(experimentId, experiment.getExperimentId())) {
                file = courseExperiment.getInstructor();
            }
        }
        return file;
    }
}
