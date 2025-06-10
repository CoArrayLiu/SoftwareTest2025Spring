package com.backend.vse.service.impl;

import com.backend.vse.common.CacheConstants;
import com.backend.vse.common.ResponseCode;
import com.backend.vse.dto.CourseBasicInfoDto;
import com.backend.vse.dto.ExperimentScoreDto;
import com.backend.vse.dto.StudentCourseScoreDto;
import com.backend.vse.dto.StudentExperimentScoreDto;
import com.backend.vse.entity.Course;
import com.backend.vse.entity.TeacherStudentCourse;
import com.backend.vse.exception.BusinessException;
import com.backend.vse.exception.DataNotFoundException;
import com.backend.vse.exception.DataProcessingException;
import com.backend.vse.exception.DatabaseOperationException;
import com.backend.vse.mapper.CourseMapper;
import com.backend.vse.mapper.CourseStudentTeacherMapper;
import com.backend.vse.mapper.StudentCourseMapper;
import com.backend.vse.mapper.StudentScoreMapper;
import com.backend.vse.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor // Lombok 自动生成构造函数
public class CourseServiceImpl implements CourseService {
    private final CourseMapper courseMapper;
    private final CourseStudentTeacherMapper courseStudentTeacherMapper;
    private final StudentScoreMapper studentScoreMapper;
    private final StudentCourseMapper studentCourseMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<CourseBasicInfoDto> getAllCourse() {
        String cacheKey = "course::all";
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();

        Object cached = ops.get(cacheKey);
        if (cached != null) {
            log.info("从 Redis 获取全部课程信息");
            return objectMapper.convertValue(cached, new TypeReference<>() {
            });
        }

        List<Course> courseList = Optional.ofNullable(courseMapper.selectAllCourses())
                .orElseThrow(()->new DataNotFoundException("未搜索到课程"));

        try {
            List<CourseBasicInfoDto> result = courseList.stream()
                    .map(CourseBasicInfoDto::new)
                    .collect(Collectors.toList());

            ops.set(cacheKey, result, CacheConstants.CACHE_TTL, TimeUnit.MINUTES);
            return result;
        }catch (Exception e){
            throw new BusinessException(ResponseCode.BUSINESS_DATA_FORMAT_CONVERSION_ERROR, "课程数据转换异常");
        }
    }

    @Override
    @Transactional
    public int insertOneCourse(Course course) {
        int result = courseMapper.insert(course);
        if (result > 0) {
            redisTemplate.delete("course::all"); // 插入新课程后清除缓存
            log.info("插入课程后清除 course::all 缓存");
        }else{
            throw new DatabaseOperationException(
                    ResponseCode.SYSTEM_DB_ERROR,
                    "实验创建失败，可能该课程已存在"
            );
        }
        return result;
    }

    @Override
    public List<CourseBasicInfoDto> getCoursesByTeacher(Long teacherIndex) {
        String cacheKey = "course::teacher:" + teacherIndex;
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();

        Object cached = ops.get(cacheKey);
        if (cached != null) {
            log.info("从 Redis 获取教师 {} 的课程信息", teacherIndex);
            return objectMapper.convertValue(cached, new TypeReference<>() {
            });
        }

        List<Course> courseList = Optional.ofNullable(courseMapper.getTeachingCourseList(teacherIndex))
                .orElseThrow(()->new DataNotFoundException("未找到该教师的课程信息"));

        try {
            List<CourseBasicInfoDto> result = courseList.stream()
                    .map(CourseBasicInfoDto::new)
                    .collect(Collectors.toList());

            ops.set(cacheKey, result, CacheConstants.CACHE_TTL, TimeUnit.MINUTES);
            return result;
        }catch (Exception e){
            throw new BusinessException(ResponseCode.BUSINESS_DATA_FORMAT_CONVERSION_ERROR, "课程数据转换异常");
        }
    }

    @Override
    public int insertCourseStudentTeacher(TeacherStudentCourse teacherStudentCourse) {
        int result = courseStudentTeacherMapper.insert(teacherStudentCourse);
        if (result > 0) {
            String cacheKeyStudent = "course::student:" + teacherStudentCourse.getStudentIndex() + ":" + teacherStudentCourse.getCourseId();
            String cacheKeyTeacher = "course::teacher:" + teacherStudentCourse.getTeacherIndex();
            redisTemplate.delete(cacheKeyStudent); // 插入课程学生后清除缓存
            redisTemplate.delete(cacheKeyTeacher); // 插入课程教师后清除缓存
            log.info("插入课程学生后清除 student-course 缓存：{}", cacheKeyStudent);
            log.info("插入课程学生后清除 teacher-course 缓存：{}", cacheKeyTeacher);
        }else{
            throw new DatabaseOperationException(
                    ResponseCode.SYSTEM_DB_ERROR,
                    "实验创建失败，可能该学生-课程-教师关系已存在"
            );
        }
        return result;
    }

    @Override
    public boolean getQuote(Long courseId) {
        String cacheKey = "course::quote:" + courseId;
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();

        Object cached = ops.get(cacheKey);
        if (cached != null) {
            log.info("从 Redis 获取课程报价状态：courseId={}", courseId);
            return (boolean) cached;
        }
        boolean result = Optional.ofNullable(courseMapper.quoteIsOpenByCourseId(courseId))
                .orElseThrow(() -> new DataNotFoundException("找不到对应课程供给与需求实验的投票是否开启"));

        ops.set(cacheKey, result, CacheConstants.CACHE_TTL, TimeUnit.MINUTES);
        return result;
    }

    @Override
    public int changeQuote(Long courseId) {
        int result = courseMapper.changeQuote(courseId);
        if (result > 0) {
            redisTemplate.delete("course::quote:" + courseId);
            log.info("修改报价状态后清除缓存：courseId={}", courseId);
            log.info("切换课程 {} 的投票开启状态成功", courseId);
        } else
            throw new DatabaseOperationException(
                    ResponseCode.SYSTEM_DB_ERROR,
                    "切换课程的投票开启状态失败"
            );
        return result;
    }

    @Override
    public long getCourseIdByStudentIndex(Long studentIndex) {
        return Optional.ofNullable(courseStudentTeacherMapper.getTeacherAndCourseOfAStudent(studentIndex))
                .orElseThrow(()->new DataNotFoundException("未找到该学生关联的课程和教师信息"))
                .getCourseId();
    }

    @Override
    @Transactional(readOnly = true)
    public Course getCourseAllById(Long courseId) {
        String cacheKey = "course::full:" + courseId;
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();

        Object cached = ops.get(cacheKey);
        if (cached != null) {
            log.info("从 Redis 获取完整课程信息：courseId={}", courseId);
            return objectMapper.convertValue(cached, Course.class);
        }

        Course course = Optional.ofNullable(courseMapper.selectAllByCourseId(courseId))
                .orElseThrow(()->new DataNotFoundException("未找到该课程信息"));

        ops.set(cacheKey, course, CacheConstants.CACHE_TTL, TimeUnit.MINUTES);
        return course;
    }

    @Override
    public StudentCourseScoreDto getStudentCourseInfo(Long studentIndex, Long courseId) {
        // String cacheKey = "course::student:" + studentIndex + ":" + courseId;
        // ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        //
        // Object cached = ops.get(cacheKey);
        // if (cached != null) {
        //     log.info("从 Redis 获取学生课程信息：studentIndex={}, courseId={}", studentIndex, courseId);
        //     return objectMapper.convertValue(cached, StudentCourseScoreDto.class);
        // }

        StudentCourseScoreDto studentInfo = Optional
                .ofNullable(studentCourseMapper.selectStudentInfoByIndexAndCourseId(studentIndex, courseId))
                .orElseThrow(()->new DataNotFoundException("未查找到学生信息"));

        List<ExperimentScoreDto> experimentScores = Optional
                .ofNullable(studentCourseMapper.selectExperimentScoresByCourse(courseId))
                .orElseThrow(()->new DataNotFoundException("未查找到该课程下的实验"));

        List<StudentExperimentScoreDto> experimentScoresByCourseId = Optional
                .ofNullable(studentScoreMapper.getExperimentScoresByCourseIdAndStudentIndex(courseId,studentIndex))
                .orElseThrow(()->new DataNotFoundException("未查找到该学生的实验成绩列表"));

        // 1. 将 studentScores 转换为 experimentId 为 key 的 Map（源数据）
        Map<Long, Float> studentScoreMap;
        try{
            studentScoreMap = experimentScoresByCourseId.stream()
                    .filter(Objects::nonNull) // 过滤列表中的 null 元素
                    .filter(dto -> dto.getExperimentId() != null) // 确保 experimentId 不为 null
                    .filter(dto -> dto.getReportScore() != null) // 确保 reportScore 不为 null
                    .collect(Collectors.toMap(
                            StudentExperimentScoreDto::getExperimentId,
                            StudentExperimentScoreDto::getReportScore,
                            (oldVal, newVal) -> oldVal // 如果有重复实验ID，保留第一个分数
                    ));
        }catch (BusinessException e){
            throw new BusinessException(ResponseCode.BUSINESS_DATA_FORMAT_CONVERSION_ERROR, "成绩数据类型转换错误");
        }

        // 2. 遍历目标列表 experimentScores 设置分数
        try{
            for (ExperimentScoreDto experiment : experimentScores) {
                if (experiment == null) continue;

                Long expId = experiment.getExperimentId();
                if (expId != null) {
                    Float reportScore = studentScoreMap.get(expId);
                    if (reportScore != null) {
                        experiment.setScore(reportScore); // 正确方向：学生成绩 -> 课程实验
                    }
                }
            }
            studentInfo.setExperimentScores(experimentScores);
            // ops.set(cacheKey, studentInfo, CacheConstants.CACHE_TTL, TimeUnit.MINUTES);
            return studentInfo;
        }catch (BusinessException e){
            throw new DataProcessingException("遍历成绩列表时发生错误");
        }
    }
}
