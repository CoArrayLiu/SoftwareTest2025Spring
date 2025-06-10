package com.backend.vse.controller;

import com.backend.vse.common.*;
import com.backend.vse.dto.*;
import com.backend.vse.entity.Experiment;
import com.backend.vse.entity.TeacherAssignExperiment;
import com.backend.vse.exception.SystemException;
import com.backend.vse.interceptor.JwtInterceptor;
import com.backend.vse.interceptor.RequirePermission;
import com.backend.vse.mapper.CourseStudentTeacherMapper;
import com.backend.vse.mapper.StudentMapper;
import com.backend.vse.mapper.TeacherMapper;
import com.backend.vse.service.CosService;
import com.backend.vse.service.ExperimentService;
import com.backend.vse.service.UserService;
import com.backend.vse.tools.Enums.PermissionType;
import com.backend.vse.tools.Enums.TeachingActivity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@Tag(name = "CourseExperiment", description = "课程实验管理接口")
@RestController
@RequestMapping("/courses/{courseId}/experiments")
@RequiredArgsConstructor // Lombok 自动生成构造函数
@Slf4j
public class CourseExperimentController {
    // 自动注入各类服务和Mapper
    private final ExperimentService experimentService;
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;
    private final CosService cosService;
    private final UserService userService;
    private final CourseStudentTeacherMapper courseStudentTeacherMapper;
    private final FileDownloadHelper fileDownloadHelper;
    // 声明工具类变量，但不直接初始化
    private GetStudentIndexByUserIndex getStudentIndexByUserIndex;
    private GetTeacherIndexByUserIndex getTeacherIndexByUserIndex;
    // 文件类型（指导书，报告模板）
    private enum DocumentType { GUIDE_BOOK, TEMPLATE }
    // 在依赖注入完成后初始化工具类
    @PostConstruct
    public void init() {
        this.getStudentIndexByUserIndex = new GetStudentIndexByUserIndex(studentMapper);
        this.getTeacherIndexByUserIndex = new GetTeacherIndexByUserIndex(teacherMapper);
    }

    /**
     * 根据课程ID获取该课程的实验列表
     * 请求方式：GET
     * 路径：/experiments/{courseId}
     * @param courseId 课程ID
     * @return 当前课程下的所有实验信息（包含实验配置数据）
     */
    @Operation(summary = "获取课程实验列表", description = "根据课程id，返回该课程的所有实验信息")
    @GetMapping
    public Result<List<ExperimentDto>> getExperimentsByCourseId(
            @Parameter(description = "课程ID", required = true, example = "42041301")
            @PathVariable("courseId") @Min(1) @NotNull Long courseId){
        log.info("请求获取课程实验列表，courseId: {}", courseId);
        List<ExperimentDto> experiments = experimentService.selectExperimentByCourseId(courseId);
        log.debug("返回实验列表：{}", experiments);
        return Result.success(experiments);
    }

    /**
     * 获取可添加到课程中的实验列表
     * 请求方式：GET
     * 路径：/experiments/{courseId}/available
     * @param courseId 课程ID
     * @return 当前课程未包含的实验列表
     */
    @Operation(summary = "获取可添加实验列表",description ="根据课程id，返回该课程不包含的实验的信息")
    @GetMapping("/available")
    public Result<List<Experiment>> getAvailableExperiments(
            @Parameter(description = "课程ID", required = true, example = "42041301")
            @PathVariable("courseId") @Min(1) @NotNull Long courseId) {
        log.info("请求获取可添加实验列表，courseId: {}", courseId);
        List<Experiment> availableExperiments = experimentService.selectExceptExperimentByCourseId(courseId);
        log.debug("返回可添加实验列表：{}", availableExperiments);
        return Result.success(availableExperiments);
    }

    /**
     * 向课程中批量添加实验
     * 请求方式：POST
     * 路径：/experiments/{courseId}
     * @param courseId 课程ID
     * @param experimentIds 实验ID列表
     * @return 操作结果字符串
     */
    @Operation(summary = "批量添加课程实验", description = "根据课程id和实验id，添加课程中的实验")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.MANAGE_COURSE_EXPERIMENT)
    @PostMapping
    public Result<String> addExperimentInCourse(
            @Parameter(name = "courseId", description = "课程id", required = true)
            @PathVariable("courseId") @Min(1) @NotNull Long courseId,
            @Parameter(name = "experimentIds", description = "实验id数组", required = true)
            @RequestBody @NotNull @NotEmpty List<Long> experimentIds
    ) {
        log.info("开始批量添加实验到课程，courseId: {}, experimentIds: {}", courseId, experimentIds);
        Long teacherIndex = getTeacherIndexByUserIndex.get();
        //支持批量传递experimentIdList来进行添加
        for (Long experimentId : experimentIds) {
            experimentService.insertExperimentInCourse(courseId, experimentId,teacherIndex);
        }
        log.info("批量添加实验成功，courseId: {}", courseId);
        return Result.success("添加实验成功");
    }

    /**
     * 从课程中移除指定实验
     * 请求方式：DELETE
     * 路径：/experiments/{courseId}/{experimentId}
     * @param courseId 课程ID
     * @param experimentId 实验ID
     * @return 操作结果字符串
     */
    @Operation(summary = "移除课程实验", description = "根据课程id和实验id，删除课程中的实验")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.MANAGE_COURSE_EXPERIMENT)
    @DeleteMapping("/{experimentId}")
    public Result<String> deleteExperimentInCourse(
            @Parameter(name = "courseId", description = "课程id", required = true)
            @PathVariable("courseId") @Min(1) @NotNull Long courseId,
            @Parameter(name = "experimentId", description = "实验id", required = true)
            @PathVariable("experimentId") @Min(1) @NotNull Long experimentId)
    {
        log.info("请求移除课程实验，courseId: {}, experimentId: {}", courseId, experimentId);

        experimentService.deleteExperimentInCourse(courseId, experimentId);
        log.info("移除课程实验成功，courseId: {}, experimentId: {}", courseId, experimentId);
        return Result.success("删除成功");
    }

    /**
     * 修改课程中某实验的配置
     * 请求方式：PUT
     * 路径：/experiments/{courseId}/{experimentId}
     * @param courseId 课程ID
     * @param experimentId 实验ID
     * @param dto 包含实验配置信息的DTO对象
     * @return 操作结果字符串
     */
    @Operation(summary = "更新实验配置", description = "根据课程id和实验id，修改课程中的实验")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.TEACHER_EXPERIMENT_INFO)
    @PutMapping("/{experimentId}")
    public Result<String> modifyExperimentInCourse(
            @Parameter(name = "courseId", description = "课程id", required = true)
            @PathVariable("courseId") @Min(1) @NotNull Long courseId,
            @Parameter(name = "experimentId", description = "实验id", required = true)
            @PathVariable("experimentId") @Min(1) @NotNull Long experimentId,
            @Parameter(name = "ModifyExperimentDto",
                    description = """
                                  实验相关信息，包含如下字段：{
                                      Timestamp startTime;    //开始时间
                                      Timestamp endTime ;     //结束时间
                                      Float score;            //总分
                                      String experimentContent;//实验内容
                                  }""", required = true)
            @RequestBody @NotNull ModifyExperimentDto dto
    )
    {
        log.info("请求更新实验配置，courseId: {}, experimentId: {}", courseId, experimentId);
        dto.setCourseId(courseId);
        dto.setExperimentId(experimentId);
        // 在course_experiment表中更新实验内容
        experimentService.modifyExperimentInCourse(dto);
        log.info("更新实验配置成功，courseId: {}, experimentId: {}", courseId, experimentId);
        return Result.success("更新成功");
    }

    /**
     * 获取某学生的实验详情（学生视图）
     * 请求方式：GET
     * 路径：/experiments/{courseId}/{experimentId}/student-view
     * @param courseId 课程ID
     * @param experimentId 实验ID
     * @return 实验内容详情（含指导书下载地址）
     */
    @Operation(summary = "获取实验详情（学生视图）",description = "根据实验id和课程id，获取某学生的单个实验信息")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.STUDENT_EXPERIMENT_INFO)
    @GetMapping("/{experimentId}/student-view")
    public Result<ExperimentContentDto> getExperimentContentById(
            @Parameter(name = "courseId", description = "课程id", required = true)
            @PathVariable("courseId") @Min(1) @NotNull Long courseId,
            @Parameter(name = "experimentId", description = "实验id", required = true)
            @PathVariable("experimentId") @Min(1) @NotNull Long experimentId)
    {
        log.info("请求获取实验详情（学生视图），courseId: {}, experimentId: {}", courseId, experimentId);
        Long studentIndex = getStudentIndexByUserIndex.get();
        log.debug("当前学生index: {}", studentIndex);
        Long teacherIndex = courseStudentTeacherMapper.getTeacherOfAStudentInCourse(studentIndex,courseId);

        //获取该用户该实验的指导书
        String file = experimentService.getExperimentBook(courseId,experimentId,teacherIndex);
        //根据实验id查找该实验的所有信息
        ExperimentContentDto experimentContentDto = experimentService.selectExperimentContentByExperimentIdAndCourseId(experimentId,courseId);
        //设置该实验指导书
        experimentContentDto.setFile(file);
        log.debug("返回实验详情：{}", experimentContentDto);
        return Result.success(experimentContentDto);
    }

    /* 上传实验文档（通用方法）*/
    private Result<String> handleDocumentUpload(
            Long courseId,
            Long experimentId,
            MultipartFile file,
            DocumentType docType
    ) {
        log.info("上传实验文档开始，courseId: {}, experimentId: {}, docType: {}", courseId, experimentId, docType);
        Long teacherIndex = getTeacherIndexByUserIndex.get();
        String fileUrl = cosService.uploadFile(file);
        if (fileUrl == null) {
            log.error("文件上传失败，courseId: {}, experimentId: {}", courseId, experimentId);
            throw new SystemException(ResponseCode.SYSTEM_FILE_UPLOAD_ERROR,"文件上传失败");
        }
        log.debug("文件上传成功，fileUrl: {}", fileUrl);

        UploadExperimentFileDto dto = new UploadExperimentFileDto(courseId, experimentId, teacherIndex, fileUrl);
        switch (docType) {
            case GUIDE_BOOK -> {
                experimentService.updateExperimentBook(dto);
                log.info("更新实验指导书成功，courseId: {}, experimentId: {}", courseId, experimentId);
            }
            case TEMPLATE -> {
                experimentService.updateExperimentReportTemplate(dto);
                log.info("更新实验报告模板成功，courseId: {}, experimentId: {}", courseId, experimentId);
            }
            default -> {
                log.error("未知的文档类型：{}", docType);
                return Result.fail(400, "未知文档类型");
            }
        }
        return Result.success(fileUrl);
    }

    /**
     * 上传实验指导书
     * 请求方式：PUT
     * 路径：/experiments/{courseId}/{experimentId}/guidebook
     * @param file 实验指导书文件
     * @param experimentId 实验ID
     * @param courseId 课程ID
     * @return 上传结果，包含文件URL
     */
    @Operation(summary = "上传实验指导书", description = "根据课程id，实验id上传实验指导书")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.TEACHER_EXPERIMENT_INFO)
    @PutMapping(value = "/{experimentId}/guidebook", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<String> uploadExperimentBook(
            @Parameter(name = "file", description = "MultipartFile格式文件", required = true)
            @RequestParam("file") @NotNull MultipartFile file,
            @Parameter(name = "experimentId", description = "实验id", required = true)
            @PathVariable("experimentId") @Min(1) @NotNull Long experimentId,
            @Parameter(name = "courseId", description = "课程id", required = true)
            @PathVariable("courseId") @Min(1) @NotNull Long courseId
    ) {
        log.info("上传实验指导书，课程ID：{}，实验ID：{}", courseId, experimentId);
        return handleDocumentUpload(courseId, experimentId, file, DocumentType.GUIDE_BOOK);
    }

    /**
     * 上传实验报告模板
     * 请求方式：PUT
     * 路径：/experiments/{courseId}/{experimentId}/template
     * @param file 实验报告模板文件
     * @param experimentId 实验ID
     * @param courseId 课程ID
     * @return 上传结果，包含文件URL
     */
    @Operation(summary = "上传实验报告模板", description = "根据课程id，实验id上传实验报告模板")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.TEACHER_EXPERIMENT_INFO)
    @PutMapping(value = "/{experimentId}/template", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<String> uploadExperimentReportTemplate(
            @Parameter(name = "file", description = "MultipartFile格式文件", required = true)
            @RequestParam("file") @NotNull MultipartFile file,
            @Parameter(name = "experimentId", description = "实验id", required = true)
            @PathVariable("experimentId") @Min(1) @NotNull Long experimentId,
            @Parameter(name = "courseId", description = "课程id", required = true)
            @PathVariable("courseId") @Min(1) @NotNull Long courseId
    ) {
        log.info("上传实验报告模板，课程ID：{}，实验ID：{}", courseId, experimentId);
        return handleDocumentUpload(courseId, experimentId, file, DocumentType.TEMPLATE);
    }

    /**
     * 下载实验指导书
     * 请求方式：GET
     * 路径：/experiments/{courseId}/{experimentId}/guidebook
     * @param experimentId 实验ID
     * @param courseId 课程ID
     * @return 指导书文件的二进制内容
     */
    @Operation(summary = "获取实验指导书", description = "根据实验和课程ID获取实验指导书")
    @GetMapping("/{experimentId}/guidebook")
    public ResponseEntity<?> downloadExperimentBook(
            @Parameter(name = "experimentId", description = "实验id", required = true)
            @PathVariable("experimentId") @Min(1) @NotNull Long experimentId,
            @Parameter(name = "courseId", description = "课程id", required = true)
            @PathVariable("courseId") @Min(1) @NotNull Long courseId)
    {
        log.info("下载实验指导书，课程ID：{}，实验ID：{}", courseId, experimentId);
        Long loginUserIndex = JwtInterceptor.getLoginUser();
        String role = userService.findUserByIndex(loginUserIndex).getRole();
        Long studentIndex,teacherIndex = 0L;
        if("student".equals(role)) {
            studentIndex = getStudentIndexByUserIndex.get();
            teacherIndex = courseStudentTeacherMapper.getTeacherOfAStudentInCourse(studentIndex,courseId);
        }else if("teacher".equals(role)||"responsible_teacher".equals(role)) {
            teacherIndex = getTeacherIndexByUserIndex.get();
        }

        String fileUrl = experimentService.getExperimentBook(courseId, experimentId, teacherIndex);
        try {
            return fileDownloadHelper.downloadFile(fileUrl);
        } catch (IOException e) {
            log.error("实验指导书下载失败，课程ID：{}，实验ID：{}", courseId, experimentId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Result.fail(ResponseCode.SYSTEM_FILE_DOWNLOAD_ERROR, "实验指导书下载失败"));
        }
    }

    /**
     * 下载实验报告模板
     * 请求方式：GET
     * 路径：/experiments/{courseId}/{experimentId}/template
     * @param experimentId 实验ID
     * @param courseId 课程ID
     * @return 实验报告模板文件的二进制内容
     */
    @Operation(summary = "获取实验报告模板", description = "根据实验和课程ID获取实验报告模板")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.TEACHER_EXPERIMENT_INFO)
    @GetMapping("/{experimentId}/template")
    public ResponseEntity<?> downloadExperimentReportTemplate(
            @Parameter(name = "experimentId", description = "实验id", required = true)
            @PathVariable("experimentId") @Min(1) @NotNull Long experimentId,
            @Parameter(name = "courseId", description = "课程id", required = true)
            @PathVariable("courseId") @Min(1) @NotNull Long courseId
    )
    {
        log.info("下载实验报告模板，课程ID：{}，实验ID：{}", courseId, experimentId);
        Long teacherIndex = getTeacherIndexByUserIndex.get();
        String fileUrl = experimentService.getExperimentReportTemplate(courseId, experimentId, teacherIndex);
        try {
            return fileDownloadHelper.downloadFile(fileUrl);
        } catch (IOException e) {
            log.error("实验报告模板下载失败，课程ID：{}，实验ID：{}", courseId, experimentId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Result.fail(ResponseCode.SYSTEM_FILE_DOWNLOAD_ERROR, "实验报告模板下载失败"));
        }
    }

    /**
     * 教师获取实验所有内容
     * 请求方式：GET
     * 路径：/experiments/{courseId}/{experimentId}/config
     * @param courseId 课程ID
     * @param experimentId 实验ID
     * @return 当前课程下对应实验的完整配置信息
     */
    @Operation(summary = "教师获取实验所有内容", description = "根据课程id和实验id，获取实验所有内容")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.TEACHER_EXPERIMENT_INFO)
    @GetMapping("/{experimentId}/config")
    public Result<TeacherAssignExperiment> getExperimentsContent(
            @Parameter(description = "课程ID", required = true, example = "42041301")
            @PathVariable("courseId") @Min(1) @NotNull Long courseId,
            @Parameter(description = "实验ID", required = true, example = "11")
            @PathVariable("experimentId") @Min(1) @NotNull Long experimentId)
    {
        log.info("教师获取实验所有内容，课程ID：{}，实验ID：{}", courseId, experimentId);
        Long teacherIndex = getTeacherIndexByUserIndex.get();
        TeacherAssignExperiment content = experimentService.getExperimentAllContent(courseId,experimentId,teacherIndex);
        return Result.success(content);
    }
}