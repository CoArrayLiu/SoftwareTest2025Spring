package com.backend.vse.service.impl;

import com.backend.vse.common.CacheConstants;
import com.backend.vse.dto.*;
import com.backend.vse.entity.Student;
import com.backend.vse.entity.TeacherStudentCourse;
import com.backend.vse.entity.User;
import com.backend.vse.exception.*;
import com.backend.vse.mapper.CourseStudentTeacherMapper;
import com.backend.vse.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.*;

import com.backend.vse.service.StudentService;
import com.backend.vse.mapper.StudentMapper;
import com.backend.vse.common.Result;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.TimeUnit;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.backend.vse.dto.StudentInfoDto;
import org.springframework.transaction.annotation.Transactional;
import com.backend.vse.common.ResponseCode;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor // Lombok 自动生成构造函数
public class StudentServiceImpl implements StudentService {
    private final StudentMapper studentMapper;
    private final UserMapper userMapper;
    private final CourseStudentTeacherMapper courseStudentTeacherMapper;
    private final UserServiceImpl userService;
    private final RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Transactional(readOnly = true)
    public List<StudentInfoDto> selectByCourseId(long courseId) {
        String cacheKey = "students::course:" + courseId;
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();

        // 先尝试从 Redis 获取数据
        Object cachedData = ops.get(cacheKey);
        if (cachedData != null) {
            log.info("从 Redis 缓存获取学生列表，课程 ID: {}", courseId);
            return objectMapper.convertValue(cachedData, new TypeReference<>() {
            });
        }

        // 缓存未命中，从数据库查询
        List<StudentInfoDto> students = Optional
                .ofNullable(studentMapper.findStudentsByCourseId(courseId))
                .orElseThrow(() -> new DataNotFoundException("未找到该课程下的学生"));

        // 查询到数据后存入 Redis，并设置过期时间（如 30 分钟）
        if (!students.isEmpty()) {
            ops.set(cacheKey, students, CacheConstants.CACHE_TTL, TimeUnit.MINUTES);
            log.info("数据库查询学生列表后存入 Redis 缓存，课程 ID: {}", courseId);
        }
        return students;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentInfoDto> selectFreeByCourseId(long courseId) {
        String cacheKey = "students::free_course:" + courseId;
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();

        Object cachedData = ops.get(cacheKey);
        if (cachedData != null) {
            log.info("从 Redis 缓存获取可选课程学生列表，课程 ID: {}", courseId);
            return objectMapper.convertValue(cachedData, new TypeReference<>() {
            });
        }

        // 缓存未命中，从数据库查询
        List<StudentInfoDto> students = Optional
                .ofNullable(studentMapper.findFreeStudentsByCourseId(courseId))
                .orElseThrow(() -> new DataNotFoundException("未找到可选课的学生"));

        // 查询到数据后存入 Redis
        if (!students.isEmpty()) {
            ops.set(cacheKey, students, CacheConstants.CACHE_TTL, TimeUnit.MINUTES);
            log.info("数据库查询可选课程学生列表后存入 Redis 缓存，课程 ID: {}", courseId);
        }
        return students;
    }

    @Override
    @Transactional(readOnly = true)
    public Long selectTeacherIndexByStudentIndex(long studentIndex) {
        return Optional.ofNullable(courseStudentTeacherMapper.getTeacherAndCourseOfAStudent(studentIndex))
                .orElseThrow(()->new DataNotFoundException("未找到该学生关联的课程和教师信息"))
                .getTeacherIndex();
    }

    @Override
    @Transactional
    public void addExistStudent(Long courseId, Long teacherIndex, Long studentId) {
        // 先根据用户id和学校名寻找到该用户的详细信息
        User user = Optional.ofNullable(userMapper.selectByUserId(String.valueOf(studentId)))
                .orElseThrow(() -> new DataNotFoundException("用户不存在: " + studentId));

        Student student = Optional
                .ofNullable(studentMapper.selectStudentByUserIndex(user.getUserIndex()))
                .orElseThrow(() -> new DataNotFoundException("未找到该用户对应的学生信息"));

        try{
            // 然后根据课程id将该用户添加到该课程中
            int affectedRows = courseStudentTeacherMapper.insert(
                    new TeacherStudentCourse(student.getStudentIndex(), teacherIndex, courseId)
            );

            if (affectedRows <= 0) {
                throw new OperationException(
                        ResponseCode.BUSINESS_OPERATION_FAILED,
                        "学生已在该课程中"
                );
            }else{
                String cacheKeyCourse = "students::course:" + courseId;
                String cacheKeyFreeCourse = "students::free_course:" + courseId;
                redisTemplate.delete(cacheKeyCourse);
                redisTemplate.delete(cacheKeyFreeCourse);
                log.info("新增学生 {}，清除课程 {} 的学生缓存", studentId, courseId);
            }
        }catch (DuplicateKeyException e) {
            throw new OperationException(
                    ResponseCode.BUSINESS_OPERATION_FAILED,
                    "禁止重复添加学生"
            );
        } catch (DataAccessException e) {
            throw new DatabaseOperationException(
                    ResponseCode.SYSTEM_DB_ERROR,
                    "学生添加失败"
            );
        }
    }

    @Override
    @Transactional
    public void deleteExistStudent(Long courseId, Long teacherIndex, Long studentId) {
        // 先根据用户id和学校名寻找到该用户的详细信息
        User user = Optional.ofNullable(userMapper.selectByUserId(String.valueOf(studentId)))
                .orElseThrow(() -> new DataNotFoundException("用户不存在: " + studentId));

        Student student = Optional
                .ofNullable(studentMapper.selectStudentByUserIndex(user.getUserIndex()))
                .orElseThrow(() -> new DataNotFoundException("未找到该用户对应的学生信息"));

        // 创建删除条件的包装器
        QueryWrapper<TeacherStudentCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("student_index", student.getStudentIndex())
                .eq("teacher_index", teacherIndex)
                .eq("course_id", courseId);

        // 根据条件删除对应的记录
        try{
            int affectedRows = courseStudentTeacherMapper.delete(wrapper);
            if (affectedRows <= 0) {
                throw new OperationException(
                        ResponseCode.BUSINESS_OPERATION_FAILED,
                        "删除失败，课程中没有该学生"
                );
            } else{
                String cacheKeyCourse = "students::course:" + courseId;
                String cacheKeyFreeCourse = "students::free_course:" + courseId;
                redisTemplate.delete(cacheKeyCourse);
                redisTemplate.delete(cacheKeyFreeCourse);
                log.info("删除学生 {}，清除课程 {} 的学生缓存", studentId, courseId);
            }
        }catch (DataAccessException e) {
            throw new DatabaseOperationException(
                    ResponseCode.SYSTEM_DB_ERROR,
                    "学生删除失败"
            );
        }
    }

    @Transactional
    public Result<String> importStudent(StudentImportDto studentImportDto) {
        try {
            // 参数校验
            validateStudent(studentImportDto);
            // 创建并保存用户信息
            User user = createUser(studentImportDto);

            int affectedRows = userMapper.insert(user);
            if (affectedRows <= 0) {
                throw new OperationException(
                        ResponseCode.BUSINESS_OPERATION_FAILED,
                        "插入用户失败，可能是重复的插入"
                );
            }
            // 获取保存后的用户信息（包含生成的userIndex）
            User savedUser =Optional.ofNullable(userMapper.selectOne(
                            new LambdaQueryWrapper<User>().eq(User::getId,user.getId())))
                    .orElseThrow(()->new DataEmptyException("学生信息插入用户表失败"));
            log.info("成功将学生信息导入到user表，学号：{}", studentImportDto.getId());

            // 创建并保存学生信息
            Student student = createStudent(studentImportDto, savedUser.getUserIndex());
            affectedRows = studentMapper.insert(student);
            if (affectedRows <= 0) {
                throw new OperationException(
                        ResponseCode.BUSINESS_OPERATION_FAILED,
                        "插入学生失败，可能是重复的插入"
                );
            }

            log.info("成功将学生信息导入到student表，学号：{}", studentImportDto.getId());
            return Result.success("成功导入学生信息");
        } catch (Exception e) {
            log.error("导入学生信息失败，学号：{}，原因：{}", studentImportDto.getId(), e.getMessage());
            throw new BusinessException("导入学生信息失败：" + e.getMessage());
        }
    }

    @Transactional
    public Result<BatchImportResult> batchImportStudents(List<StudentImportDto> studentDtos) {
        BatchImportResult result = new BatchImportResult();
        result.setTotalCount(studentDtos.size());
        result.setFailureRecords(new ArrayList<>());

        // 1. 批量校验数据
        Map<String, StudentImportDto> validStudents = new HashMap<>();
        for (StudentImportDto dto : studentDtos) {
            try {
                validateStudent(dto);
                validStudents.put(dto.getId(), dto);
            } catch (BusinessException e) {
                log.error("批量校验数据失败");
                recordFailure(result, dto, e.getMessage());
            }
        }

        // 2. 批量检查学号是否存在
        List<String> existingIds = userService.findUsersByIds(validStudents.keySet());
        for (String existingId : existingIds) {
            StudentImportDto dto = validStudents.remove(existingId);
            log.error("学号{}已存在", dto.getId());
            recordFailure(result, dto, "该学号已存在");
        }

        // 3. 批量插入有效数据
        if (!validStudents.isEmpty()) {
            try {
                // 批量插入用户信息
                List<User> users = validStudents.values().stream()
                        .map(this::createUser)
                        .collect(Collectors.toList());
                int affectedRows = userMapper.batchInsert(users);
                if (affectedRows <= 0) {
                    throw new OperationException(
                            ResponseCode.BUSINESS_OPERATION_FAILED,
                            "插入用户失败，可能是重复的插入"
                    );
                }

                // 获取插入后的用户信息（包含生成的userIndex）
                List<User> savedUsers = userService.findUserListByIds(
                        users.stream().map(User::getId).collect(Collectors.toList())
                );
                Map<String, Long> userIndexMap = savedUsers.stream()
                        .collect(Collectors.toMap(User::getId, User::getUserIndex));

                // 批量插入学生信息
                List<Student> students = validStudents.values().stream()
                        .map(dto -> createStudent(dto, userIndexMap.get(dto.getId())))
                        .collect(Collectors.toList());

                affectedRows = studentMapper.batchInsert(students);
                if (affectedRows <= 0) {
                    throw new OperationException(
                            ResponseCode.BUSINESS_OPERATION_FAILED,
                            "插入学生失败，可能是重复的插入"
                    );
                }

                log.info("批量插入{}个学生信息成功", students.size());
                result.setSuccessCount(validStudents.size());
            } catch (Exception e) {
                log.error("批量导入学生信息失败", e);
                throw new BusinessException("批量导入学生信息失败：" + e.getMessage());
            }
        }

        result.setFailureCount(result.getFailureRecords().size());
        return Result.success(result);
    }

    /**
     * 创建用户对象
     * @param dto 导入数据DTO
     * @return 用户对象
     */

    private User createUser(StudentImportDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setPassword(dto.getId()); // 建议使用加密后的密码
        user.setEmail(dto.getEmail());
        user.setStatus((byte)0);
        user.setRole("student");
        return user;
    }

    /**
     * 创建学生对象
     * @param dto 导入数据DTO
     * @param userIndex 用户索引
     * @return 学生对象
     */
    private Student createStudent(StudentImportDto dto, Long userIndex) {
        Student student = new Student();
        student.setStudentIndex(userIndex);
        student.setSchool(dto.getSchool());
        student.setCourseScore((float)0);
        student.setUserIndex(userIndex);
        return student;
    }

    private void validateStudent(StudentImportDto dto) {
        if (dto == null) {
            throw new DataEmptyException("导入数据不能为空");
        }
        if (StringUtils.isBlank(dto.getId())) {
            throw new InvalidParameterException("学号不能为空");
        }
        if (StringUtils.isBlank(dto.getName())) {
            throw new InvalidParameterException("姓名不能为空");
        }
        if (StringUtils.isBlank(dto.getEmail())) {
            throw new InvalidParameterException("邮箱不能为空");
        }
        if (StringUtils.isBlank(dto.getSchool())) {
            throw new InvalidParameterException("学院不能为空");
        }
    }

    private void recordFailure(BatchImportResult result, StudentImportDto dto, String reason) {
        BatchImportResult.FailureRecord failureRecord = new BatchImportResult.FailureRecord();
        failureRecord.setId(dto.getId());
        failureRecord.setName(dto.getName());
        failureRecord.setReason(reason);
        result.getFailureRecords().add(failureRecord);
    }

    public List<StudentManageInfoDto> fetchStudentInfo(){
        return Optional.ofNullable(studentMapper.fetchStudentInfo())
                .orElseThrow(() -> new DataNotFoundException("未找到学生信息"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStudent(StudentManageInfoDto studentDto) {
        // 参数校验
        if (StringUtils.isBlank(studentDto.getStudentId())) {
            throw new InvalidParameterException("学号不能为空");
        }
        // 2. 检查状态值是否合法
        if (studentDto.getStatus() != null &&
                !Arrays.asList((byte)0, (byte)1, (byte)2).contains(studentDto.getStatus())) {
            throw new InvalidParameterException("非法的状态值");
        }

        // 3. 先查询用户信息
        User existUser =Optional.ofNullable(userMapper.selectOne(
                        new LambdaQueryWrapper<User>().eq(User::getUserIndex,studentDto.getUserIndex())))
                .orElseThrow(()->new DataEmptyException("更新学生信息：用户表中不存在"));

        // 4. 检查学生是否存在
        Student existStudent =Optional.ofNullable(studentMapper.selectOne(
                        new LambdaQueryWrapper<Student>().eq(Student::getUserIndex, studentDto.getUserIndex())))
                .orElseThrow(()->new DataEmptyException("更新学生信息：学生表中不存在"));

        // 如果成绩不为空，验证成绩格式
        if (studentDto.getCourseScore()!=null) {
            try {
                float score = studentDto.getCourseScore();
                if (score < 0 || score > 100) {
                    throw new InvalidParameterException("成绩必须在0-100之间");
                }
            } catch (NumberFormatException e) {
                throw new InvalidParameterException("成绩格式不正确");
            }
        }
        // 6. 更新User表信息
        try{
            User updateUser = new User();
            updateUser.setUserIndex(existUser.getUserIndex());
            updateUser.setName(studentDto.getStudentName());
            updateUser.setEmail(studentDto.getEmail());
            updateUser.setStatus(studentDto.getStatus());
            int userResult = userMapper.updateUser(updateUser);
            if (userResult <=0) {
                throw new BusinessException("更新user表信息失败");
            }
        } catch (DataAccessException e) {
            throw handleDatabaseException(e, "更新学生信息：更新user表失败");
        }

        // 7. 更新Student表信息
        try{
            Student updateStudent = new Student();
            updateStudent.setStudentIndex(existStudent.getStudentIndex());
            updateStudent.setSchool(studentDto.getSchool());
            updateStudent.setCourseScore(studentDto.getCourseScore()
            );
            int studentResult = studentMapper.updateStudent(updateStudent);
            if (studentResult != 1) {
                throw new OperationException(
                        ResponseCode.BUSINESS_OPERATION_FAILED,
                        "更新student表失败"
                );
            }
        } catch (DataAccessException e) {
            throw handleDatabaseException(e, "更新学生信息：更新student表失败");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Student selectStudentByUserIndex(Long userIndex) {
        return Optional.ofNullable(studentMapper.selectStudentByUserIndex(userIndex))
                .orElseThrow(() -> new DataNotFoundException("未找到学生信息"));
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