package com.backend.vse.controller;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.alibaba.excel.EasyExcel;
import com.backend.vse.exception.*;
import com.backend.vse.common.GetStudentIndexByUserIndex;
import com.backend.vse.common.GetTeacherIndexByUserIndex;
import com.backend.vse.dto.*;
import com.backend.vse.entity.*;
import com.backend.vse.interceptor.RequirePermission;
import com.backend.vse.mapper.TeacherMapper;
import com.backend.vse.service.*;
import com.backend.vse.common.StudentImportListener;
import com.backend.vse.tools.Enums.PermissionType;
import com.backend.vse.tools.Enums.TeachingActivity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.backend.vse.mapper.UserMapper;
import com.backend.vse.mapper.StudentMapper;
import com.backend.vse.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import java.io.ByteArrayOutputStream;
import com.backend.vse.common.ResponseCode;

@Tag(name = "Course", description = "课程管理接口")
@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor // Lombok 自动生成构造函数
@Slf4j
public class CourseController {
    // 自动注入各类服务和Mapper
    private final CourseService courseService;
    private final DocumentService documentService;
    private final UserMapper userMapper;
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;
    private final UserService userService;
    private final StudentService studentService;
    private final StudentScoreService studentScoreService;
    // 声明工具类变量，但不直接初始化
    private GetStudentIndexByUserIndex getStudentIndexByUserIndex;
    private GetTeacherIndexByUserIndex getTeacherIndexByUserIndex;

    // 在依赖注入完成后初始化工具类
    @PostConstruct
    public void init() {
        this.getStudentIndexByUserIndex = new GetStudentIndexByUserIndex(studentMapper);
        this.getTeacherIndexByUserIndex = new GetTeacherIndexByUserIndex(teacherMapper);
    }

    /**
     * 获取所有课程
     * 请求方式：GET
     * 路径：/courses
     * @return 所有课程基本信息列表
     */
    @Operation(summary = "获取所有课程", description = "获取所有课程")
    @GetMapping()
    public Result<List<CourseBasicInfoDto>> getAllCourses()
    {
        log.info("请求获取所有课程");

        // 获取课程列表（异常自动抛出到全局处理器）
        List<CourseBasicInfoDto> courseBasicInfoDtoList = courseService.getAllCourse();
        log.info("成功获取课程列表，数量: {}", courseBasicInfoDtoList.size());

        // 去重排序（业务逻辑不应抛出异常，此处保留防御性处理）
        try {
            List<CourseBasicInfoDto> reverseList = courseBasicInfoDtoList.stream()
                    .distinct()
                    .sorted(Collections.reverseOrder())
                    .collect(Collectors.toList());
            log.info("课程列表排序完成");
            return Result.success(reverseList);
        } catch (Exception e) {
            log.error("课程列表排序时发生错误", e);
            throw new DataProcessingException("课程数据排序异常");
        }
    }

    // 使用EasyExcel解析Excel
    private List<StudentImportDto> parseStudentExcelWithEasyExcel(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            StudentImportListener listener = new StudentImportListener();
            EasyExcel.read(inputStream, StudentImportDto.class, listener)
                    .sheet()
                    .headRowNumber(1)
                    .doRead();

            List<StudentImportDto> studentList = listener.getStudentDtos();
            if (studentList.isEmpty()) {
                throw new ExcelParseException("Excel文件中没有有效的学生数据");
            }
            return studentList;
        } catch (IOException e) {
            throw new ExcelParseException("文件读取失败: " + e.getMessage());
        } catch (Exception e) {
            throw new SystemException(ResponseCode.SYSTEM_FILE_PROCESS_ERROR, "Excel解析异常", e);
        }
    }

    /**
     * 新增课程（包括课程基本信息、关联教师、导入学生信息等）
     * 请求方式：POST
     * 路径：/courses
     * @param newCourseDto 新课程的数据传输对象，包含课程信息和学生列表
     * @param file 传入的excel格式的学生列表
     * @return 添加结果，成功返回“新增课程成功”，失败返回错误信息
     */
    @Operation(summary = "新增课程", description = "添加一门新的课程")
    @RequirePermission(type = PermissionType.TEACHING, teaching = TeachingActivity.MANAGE_COURSE)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional(rollbackFor = Exception.class) // 使用注解声明式事务
    public Result<String> postOneCourse(
            @RequestParam("file") @NotNull MultipartFile file,
            @ModelAttribute NewCourseDto newCourseDto)
    {
        // 参数校验（使用校验注解更规范）
        validateParameters(file, newCourseDto);

        // 主业务流程
        Course course = buildCourseEntity(newCourseDto);
        processCourseCreation(course);
        processStudentData(file, newCourseDto);

        log.info("【postOneCourse】课程添加成功：{}", course.getCourseId());
        return Result.success("新增课程成功！");
    }

    //------------------------ 私有方法 ------------------------
    private void validateParameters(MultipartFile file, NewCourseDto dto) {
        if (file.isEmpty()) {
            throw new InvalidParameterException("请上传学生信息文件");
        }
        if (StringUtils.isBlank(dto.getCourseName())) {
            throw new InvalidParameterException("课程名称不能为空");
        }
        // 其他校验规则...
    }

    private Course buildCourseEntity(NewCourseDto dto) {
        try {
            return new Course(dto.getCourseId(), dto.getCourseName(), dto.getSemester(), dto.getYear());
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("创建课程失败");
        }
    }

    private void processCourseCreation(Course course) {
        if (courseService.insertOneCourse(course) <= 0) {
            log.error("课程插入失败");
            throw new DatabaseOperationException(ResponseCode.SYSTEM_DB_ERROR, "课程创建失败");
        }
    }

    private void processStudentData(MultipartFile file, NewCourseDto dto) {
        List<StudentImportDto> studentList = parseStudentExcelWithEasyExcel(file);
        log.info("开始处理学生列表，共 {} 人", studentList.size());

        studentList.forEach(item -> {
            User user = Optional.ofNullable(userMapper.selectByUserId(item.getId()))
                    .orElseThrow(() -> new DataNotFoundException("用户不存在: " + item.getId()));

            Student student = Optional.ofNullable(studentMapper.selectStudentByUserIndex(user.getUserIndex()))
                    .orElseThrow(() -> new DataNotFoundException("学生信息不存在"));

            dto.getTeacherList().forEach(teacherId -> {
                if (courseService.insertCourseStudentTeacher(
                        new TeacherStudentCourse(student.getStudentIndex(), teacherId, dto.getCourseId())) <= 0)
                {
                    throw new DatabaseOperationException(ResponseCode.SYSTEM_DB_ERROR, "课程关系插入失败");
                }
            });
        });
    }


    /**
     * 获取当前教师所教授的课程列表
     * 请求方式：GET
     * 路径：/courses/teaching
     * @return 当前教师的课程基本信息列表，按时间倒序排列
     */
    @Operation(summary = "获取教师课程列表", description = "获取指定教师的所有课程信息")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.TEACHER_COURSE_INFO)
    @GetMapping("/teaching")
    public Result<List<CourseBasicInfoDto>> getCoursesByTeacher()
    {
        log.info("【getCoursesByTeacher】开始查询教师课程");
        Long teacherIndex = getTeacherIndexByUserIndex.get();
        log.info("【getCoursesByTeacher】教师索引：{}", teacherIndex);

        // 获取课程列表
        List<CourseBasicInfoDto> courses = courseService.getCoursesByTeacher(teacherIndex);

        return Result.success(sortCourses(courses));
    }

    private List<CourseBasicInfoDto> sortCourses(List<CourseBasicInfoDto> courses) {
        if (courses.isEmpty()) {
            throw new DataNotFoundException("该教师暂无课程");
        }

        return courses.stream()
                .distinct()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());
    }

    /**
     * 获取当前学生所在的课程ID
     * 请求方式：GET
     * 路径：/courses/students/current
     * @return 当前登录学生的课程ID
     */
    @Operation(summary = "获取当前学生所在课程ID", description = "根据已登录学生的Index，获取其所在课程的ID")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.STUDENT_COURSE_INFO)
    @GetMapping("/students/current")
    public Result<Long> getCurrentStudentCourse() {
        log.info("调用接口：获取当前学生所在课程ID");

        Long studentIndex = getStudentIndexByUserIndex.get();
        log.debug("获取到学生索引 studentIndex={}", studentIndex);

        return Result.success(courseService.getCourseIdByStudentIndex(studentIndex));
    }
    /**
     * 获取指定学生所在的课程ID
     * 请求方式：GET
     * 路径：/courses/students/{studentId}
     * @param studentId 学生的学号
     * @return 对应学生的课程ID
     */
    @Operation(summary = "获取指定学生所在课程ID", description = "根据学生的学号，获取其所在课程的ID")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.TEACHER_COURSE_INFO)
    @GetMapping("/students/{studentId}")
    public Result<Long> getCourseIdByStudentID(
            @Parameter(description = "学生ID", required = true, example = "2251640")
            @PathVariable("studentId") @Min(1) @NotNull String studentId
    ) {
        log.info("调用接口：获取指定学生所在课程ID，studentId={}", studentId);
        Long userIndex = userService.findUserById(studentId).getUserIndex();
        Student student = studentService.selectStudentByUserIndex(userIndex);
        Long studentIndex = student.getStudentIndex();
        log.debug("获取学生索引 studentIndex={}", studentIndex);

        return Result.success(courseService.getCourseIdByStudentIndex(studentIndex));
    }

    /**
     * 获取指定课程的全部信息
     * 请求方式：GET
     * 路径：/courses/{courseId}
     * @param courseId 课程ID
     * @return 指定课程的完整信息
     */
    @Operation(summary = "获取某课程所有信息", description = "根据课程ID，获取某课程所有信息")
    @GetMapping("/{courseId}")
    public Result<Course> getCourseAllByID(
            @Parameter(description = "课程ID", required = true, example = "42041301")
            @PathVariable("courseId") @Min(1) @NotNull Long courseId
    ) {
        log.info("请求获取课程信息，课程ID: {}", courseId);
        return Result.success(courseService.getCourseAllById(courseId));
    }

    /**
     * 获取指定课程下的所有学生信息
     * 请求方式：GET
     * 路径：/courses/{courseId}/students
     * @param courseId 课程ID
     * @return 学生信息列表，若无学生或异常返回对应提示
     */
    @Operation(summary = "获取课程学生名单", description = "根据课程ID获取该课程的所有学生信息")
    @GetMapping(value = "/{courseId}/students")
    public Result<List<StudentInfoDto>> getCourseStudents(
            @Parameter(description = "课程ID", required = true, example = "42041301")
            @PathVariable("courseId") @Min(1) @NotNull Long courseId
    ) {
        log.info("请求获取课程学生列表，课程ID: {}", courseId);
        List<StudentInfoDto> students = studentService.selectByCourseId(courseId);
        return Result.success(students);
    }

    /**
     * 获取当前课程中尚未选择课程的可选学生信息
     * 请求方式：GET
     * 路径：/courses/{courseId}/available-students
     * @param courseId 课程ID
     * @return 可选学生信息列表，若无学生或异常返回对应提示
     */
    @Operation(summary = "获取可选课学生名单", description = "根据课程ID，获取该课程的可选课学生名单")
    @GetMapping("/{courseId}/available-students")
    public Result<List<StudentInfoDto>> getAvailableStudents(
            @Parameter(description = "课程ID", required = true, example = "42041301")
            @PathVariable("courseId") @Min(1) @NotNull Long courseId
    ) {
        log.info("请求获取可选学生列表，课程ID: {}", courseId);
        return Result.success(studentService.selectFreeByCourseId(courseId));
    }

    /**
     * 向某一课程中添加学生（非新增用户）
     * 请求方式：POST
     * 路径：/courses/{courseId}/students
     * @param courseId 课程ID
     * @param studentId 学生ID
     * @return 添加结果，成功返回“添加学生成功”，失败返回错误信息
     */
    @Operation(summary = "向某一课程中添加学生", description = "根据学生信息和课程id向某一课程中添加学生（非新增用户）")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.MANAGE_COURSE_STUDENT)
    @PostMapping("/{courseId}/students")
    public Result<String> addCourseStudent(
            @Parameter(description = "课程ID", required = true, example = "42041301")
            @PathVariable("courseId") @Min(1) @NotNull Long courseId,
            @Parameter(description = "学生ID", required = true, example = "2251640")
            @RequestParam("studentId") @Min(1) @NotNull Long studentId
    ) {
        Long teacherIndex = getTeacherIndexByUserIndex.get();
        log.info("添加学生到课程，课程ID: {}，教师Index: {}，学生: {}", courseId, teacherIndex, studentId);

        studentService.addExistStudent(
                courseId,
                teacherIndex,
                studentId
        );
        return Result.success("添加学生成功");
    }

    /**
     * 从某一课程中删除学生（非注销用户）
     * 请求方式：DELETE
     * 路径：/courses/{courseId}/students
     * @param courseId 课程ID
     * @param studentId 学生ID
     * @return 删除结果，成功返回“删除学生成功”，失败返回错误信息
     */
    @Operation(summary = "向某一课程中删除学生", description = "根据学生信息和课程id向某一课程中删除学生（非新增用户）")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.MANAGE_COURSE_STUDENT)
    @DeleteMapping("/{courseId}/students")
    public Result<String> deleteCourseStudent(
            @Parameter(description = "课程ID", required = true, example = "42041301")
            @PathVariable("courseId") @Min(1) @NotNull Long courseId,
            @Parameter(description = "学生ID", required = true, example = "2251640")
            @RequestParam("studentId") @Min(1) @NotNull Long studentId
    ) {
        Long teacherIndex = getTeacherIndexByUserIndex.get();
        log.info("删除课程中的学生，课程ID: {}，教师Index: {}，学生: {}", courseId, teacherIndex, studentId);

        studentService.deleteExistStudent(
                courseId,
                teacherIndex,
                studentId
        );
        return Result.success("删除学生成功");
    }

    /**
     * 获取当前学生在某一课程中的成绩单信息
     * 请求方式：GET
     * 路径：/courses/{courseId}/enrollment
     * @param courseId 课程ID
     * @return 成绩单信息，包括学生基本信息、实验成绩和课程总分，若无记录则返回提示
     */
    @Operation(summary = "获取当前学生的课程成绩单", description = "根据学生index与课程id，返回该学生的基本个人信息，以及在该课程中的所有实验分数和课程总分")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.STUDENT_COURSE_INFO)
    @GetMapping("/{courseId}/enrollment")
    public Result<StudentCourseScoreDto> getStudentCourseInfo(
            @Parameter(description = "课程ID", required = true, example = "42041301")
            @PathVariable("courseId") @Min(1) @NotNull Long courseId
    ) {
        log.info("查询学生课程成绩单，课程ID: {}", courseId);
        Long studentIndex = getStudentIndexByUserIndex.get();
        return Result.success(courseService.getStudentCourseInfo(studentIndex, courseId));
    }

    /**
     * 获取某一课程中所有学生的总成绩
     * 请求方式：GET
     * 路径：/courses/{courseId}/scores/total
     * @param courseId 课程ID
     * @return 所有学生的总成绩列表，若无成绩数据则返回提示
     */
    @Operation(summary = "获取课程中所有学生的总成绩", description = "根据课程的id，获取该课程中所有学生的总成绩列表")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.TEACHER_COURSE_INFO)
    @GetMapping("/{courseId}/scores/total")
    public Result<List<StudentTotalScoreDto>> getCourseTotalScores(
            @Parameter(description = "课程ID", required = true, example = "42041301")
            @PathVariable("courseId") @Min(1) @NotNull Long courseId
    ) {
        log.info("请求获取课程总成绩，课程ID: {}", courseId);
        List<StudentTotalScoreDto> totalScores = studentScoreService.getTotalScoresByCourseId(courseId);
        if (totalScores.isEmpty()) {
            log.info("课程ID: {} 无成绩数据", courseId);
            return Result.fail(ResponseCode.BUSINESS_DATA_EMPTY, "当前无成绩数据");
        }
        log.debug("成功获取总成绩，数量: {}", totalScores.size());
        return Result.success(totalScores);
    }

    /**
     * 获取某一课程中所有学生的实验成绩明细
     * 请求方式：GET
     * 路径：/courses/{courseId}/scores/experiments
     * @param courseId 课程ID
     * @return 实验成绩明细列表，按学生和实验划分，若无数据则返回提示
     */
    @Operation(summary = "获取课程实验成绩明细", description = "根据课程的id，获取该课程中所有学生的每一个实验的成绩列表")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.TEACHER_COURSE_INFO)
    @GetMapping("/{courseId}/scores/experiments")
    public Result<List<StudentExperimentScoreDto>> getExperimentScoresByCourseId(
            @Parameter(description = "课程ID", required = true, example = "42041301")
            @PathVariable("courseId") @Min(1) @NotNull Long courseId
    ) {
        log.info("请求获取实验成绩明细，课程ID: {}", courseId);
        List<StudentExperimentScoreDto> experimentScores = studentScoreService.getExperimentScoresByCourseId(courseId);
        if (experimentScores.isEmpty()) {
            log.info("课程ID: {} 无实验成绩数据", courseId);
            return Result.fail(ResponseCode.BUSINESS_DATA_EMPTY, "当前无实验成绩数据");
        }
        log.debug("成功获取实验成绩明细，数量: {}", experimentScores.size());
        return Result.success(experimentScores);
    }
    /**
     * 获取某一课程的平均成绩
     * 请求方式：GET
     * 路径：/courses/{courseId}/scores/average
     * @param courseId 课程ID
     * @return 该课程的平均成绩
     */
    @Operation(summary = "获取课程平均分")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.TEACHER_COURSE_INFO)
    @GetMapping("/{courseId}/scores/average")
    public Result<Float> getCourseAverageScore(
            @Parameter(description = "课程ID", required = true, example = "42041301")
            @PathVariable("courseId") @Min(1) @NotNull Long courseId
    ) {
        log.info("请求获取课程平均分，课程ID: {}", courseId);
        return Result.success(studentScoreService.calculateAverageScore(courseId));
    }

    /**
     * 获取某一课程的成绩单
     * 请求方式：GET
     * 路径：/courses/{courseId}/scores/transcript
     * @param courseId 课程ID
     * @return 该课程的平均成绩
     */
    @Operation(summary = "获取课程总成绩单")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.TEACHER_COURSE_INFO)
    @GetMapping("/{courseId}/scores/transcript")
    public ResponseEntity<byte[]> getCourseTranscript(
            @Parameter(description = "课程ID", required = true, example = "42041301")
            @PathVariable("courseId") @Min(1) @NotNull Long courseId
    ) {
        List<StudentCourseScoreDto> transcript = new ArrayList<>();
        // 获取课程中所有学生的信息
        List<StudentInfoDto> studentList = studentService.selectByCourseId(courseId);

        // 处理空结果
        if (studentList.isEmpty()) {
            log.info("课程ID: {} 没有找到学生", courseId);
            return ResponseEntity
                    .status(ResponseCode.BUSINESS_DATA_EMPTY)
                    .body("该课程的学生列表为空".getBytes());
        }
        log.info("成功获取学生列表，数量: {}", studentList.size());

        // 遍历每个学生，获取其课程成绩单
        for (StudentInfoDto studentInfoDto : studentList) {
            Long studentIndex = studentInfoDto.getStudentIndex();
            StudentCourseScoreDto studentCourseScoreDto =
                    courseService.getStudentCourseInfo(studentIndex, courseId);
            transcript.add(studentCourseScoreDto);
        }
        log.debug("成功获取所有学生课程成绩单：{}", transcript);

        // 获取课程平均分
        log.info("请求获取课程平均分，课程ID: {}", courseId);
        Float averageScore = studentScoreService.calculateAverageScore(courseId);
        log.debug("课程ID: {} 平均分: {}", courseId, averageScore);

        try(Workbook workbook = documentService.generateExcel(transcript, averageScore)) {
            // 转换为字节数组
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            byte[] excelBytes = out.toByteArray();

            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "student_scores.xlsx");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            // 5. 确保资源关闭
            workbook.close();
            out.close();

            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new FileGenerationException("成绩单生成失败", e);
        }
    }
}