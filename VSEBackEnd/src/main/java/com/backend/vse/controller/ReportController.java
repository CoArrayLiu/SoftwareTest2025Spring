package com.backend.vse.controller;

import com.backend.vse.common.FileDownloadHelper;
import com.backend.vse.common.GetStudentIndexByUserIndex;
import com.backend.vse.common.ResponseCode;
import com.backend.vse.common.Result;
import com.backend.vse.dto.ExperimentReportDto;
import com.backend.vse.dto.StudentReportRecordDto;
import com.backend.vse.entity.Report;
import com.backend.vse.entity.Timer;
import com.backend.vse.exception.BusinessException;
import com.backend.vse.interceptor.RequirePermission;
import com.backend.vse.mapper.StudentMapper;
import com.backend.vse.mapper.TimerMapper;
import com.backend.vse.service.*;
import com.backend.vse.tools.Enums.PermissionType;
import com.backend.vse.tools.Enums.TeachingActivity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/reports")
@Tag(name = "Report", description = "报告管理接口")
@RequiredArgsConstructor // Lombok 自动生成构造函数
@Slf4j
public class ReportController {
    // 自动注入各类服务和Mapper
    private final ReportService reportService;
    private final CosService cosService;
    private final StudentMapper studentMapper;
    private final StudentService studentService;
    private final TimerMapper timerMapper;
    private final DocumentService documentService;
    private final ExperimentService experimentService;
    private final FileDownloadHelper fileDownloadHelper;

    // 声明工具类变量，但不直接初始化
    private GetStudentIndexByUserIndex getStudentIndexByUserIndex;

    // 在依赖注入完成后初始化工具类
    @PostConstruct
    public void init() {
        this.getStudentIndexByUserIndex = new GetStudentIndexByUserIndex(studentMapper);
    }
    /**
     * 设置报告分数
     * 根据报告ID设置报告分数
     * 请求方式：PUT
     * 路径：/{reportId}/score
     * @param reportId 报告ID
     * @param score 报告分数
     * @return 设置分数成功或失败的结果
     */
    @Operation(summary = "设置报告分数", description = "根据报告ID设置报告分数")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.GRADE_ASSIGNMENT)
    @PutMapping("/{reportId}/score")
    public Result<String> setReportScore(
            @Parameter(description = "报告id", required = true,example = "1")
            @PathVariable("reportId") @Min(1) @NotNull Long reportId,
            @Parameter(description = "报告分数", required = true,example = "10")
            @RequestBody @Min(0) @NotNull Float score
    )
    {
        log.info("设置报告分数，reportId={}, score={}", reportId, score);
        reportService.setReportScore(reportId,score);
        return Result.success("设置报告分数成功！");
    }
    /**
     * 上传实验报告
     * 根据文件上传实验报告
     * 请求方式：PUT
     * 路径：/
     * @param file MultipartFile格式的报告文件
     * @param experimentId 实验ID
     * @param courseId 课程ID
     * @return 文件上传成功或失败的结果
     */
    @Operation(summary = "上传实验报告", description = "根据文件上传实验报告")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.STUDENT_REPORT)
    @PutMapping(consumes = {MediaType.ALL_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Result<String> uploadFile(
            @Parameter(name = "file", description = "MultipartFile格式文件", required = true)
            @RequestParam("file") @NotNull MultipartFile file,
            @Parameter(description = "实验id", required = true,example = "11")
            @RequestParam("experimentId") @Min(1) @NotNull Long experimentId,
            @Parameter(description = "课程id", required = true,example = "42041301")
            @RequestParam("courseId") @Min(1) @NotNull Long courseId
    ) {

        //获取学生index
        Long studentIndex = getStudentIndexByUserIndex.get();
        log.info("上传实验报告开始 - 学生ID: {}, 实验ID: {}, 课程ID: {}, 文件名: {}", studentIndex, experimentId, courseId, file.getOriginalFilename());
        //获取该学生的老师
        Long teacherIndex = studentService.selectTeacherIndexByStudentIndex(studentIndex);
        String fileUrl = cosService.uploadFile(file);
        if (fileUrl == null) {
            log.error("文件上传失败 - 学生ID: {}, 实验ID: {}, 课程ID: {}", studentIndex, experimentId, courseId);
            return Result.fail(400, "上传失败");
        }
        //设置计时器
        Timer timer = new Timer(0L, Timestamp.valueOf(LocalDateTime.now()),null);
        timerMapper.insert(timer);
        //向Report表中添加报告（可记录某一学生的所有提交记录）
        Report report = new Report(0L,fileUrl,null,null,"submitted",studentIndex,teacherIndex,timer.getTimerId());
        reportService.submitReport(report);
        Long reportId = report.getReportId();
        //向experiment_submit表中添加实验-学生-报告的对应关系（只记录最新一次提交）
        reportService.updateReport(reportId,studentIndex,experimentId,courseId);

        log.info("上传实验报告成功 - 报告ID: {}, 文件URL: {}", reportId, fileUrl);
        return Result.success(fileUrl);
    }
    /**
     * 学生端获取最新一次实验报告
     * 根据实验和课程ID获取已登录学生的实验报告
     * 请求方式：GET
     * 路径：/students/latest
     * @param experimentId 实验ID
     * @param courseId 课程ID
     * @return 返回学生最新一次提交的实验报告文件
     */
    @Operation(summary = "学生端获取最新一次实验报告", description = "根据实验和课程ID获取已登录学生的实验报告")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.STUDENT_REPORT)
    @GetMapping(value = "/students/latest")
    public ResponseEntity<?> getStudentLatestReport(
            @Parameter(description = "实验id", required = true,example = "11")
            @RequestParam("experimentId") @Min(1) @NotNull Long experimentId,
            @Parameter(description = "课程id", required = true,example = "42041301")
            @RequestParam("courseId") @Min(1) @NotNull Long courseId
    ) {
        //获取学生index
        Long studentIndex = getStudentIndexByUserIndex.get();
        log.info("查询学生实验报告 - 学生ID: {}, 实验ID: {}, 课程ID: {}", studentIndex, experimentId, courseId);

        Long reportId = reportService.getLatestReportId(studentIndex,courseId,experimentId);
        if(reportId == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Result.fail(ResponseCode.BUSINESS_DATA_NOT_FOUND, "该学生还未提交过实验报告"));
        }

        Report report = reportService.getReportById(reportId);
        String fileUrl = report.getReportContent();
        if (fileUrl.isEmpty()) {
            log.warn("报告文件URL为空 - 报告ID: {}", reportId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Result.fail(ResponseCode.BUSINESS_DATA_EMPTY, "文件url不能为空"));
        }
        try {
            return fileDownloadHelper.downloadFile(fileUrl);
        } catch (IOException e) {
            log.error("获取实验报告失败 - 报告ID: {}, 错误: {}", reportId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Result.fail(ResponseCode.SYSTEM_FILE_DOWNLOAD_ERROR, "获取实验报告失败"));
        }
    }

    /**
     * 教师端根据学生index查看学生提交报告记录
     * 教师可以根据学生index查看学生提交的所有报告记录，包含提交时间
     * 请求方式：GET
     * 路径：/students/{studentIndex}
     * @param studentIndex 学生索引
     * @return 返回该学生的所有报告提交记录
     */
    @Operation(summary = "教师端根据学生index查看学生提交报告记录", description = "教师可以根据学生index查看学生提交的所有报告记录，包含提交时间")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.TEACHER_REPORT)
    @GetMapping("/students/{studentIndex}")
    public Result<List<StudentReportRecordDto>> getSubmissionHistory(
            @Parameter(description = "学生索引", required = true,example = "2")
            @PathVariable("studentIndex") @Min(1) @NotNull Long studentIndex
    ) {
        log.info("接收请求：获取学生报告记录，studentIndex: {}", studentIndex);
            // 查询报告记录
        List<StudentReportRecordDto> reportRecords = reportService.getStudentReportRecords(studentIndex);
        if(reportRecords.isEmpty()) {
            log.info("该学生: {} 无提交报告记录", studentIndex);
            return Result.fail(ResponseCode.BUSINESS_DATA_EMPTY, "当前学生无提交报告记录");
        }
        log.info("成功获取学生 {} 的报告记录，记录数：{}", studentIndex, reportRecords.size());
        return Result.success(reportRecords);
    }
    /**
     * 教师对某篇报告添加评论
     * 教师根据报告ID对报告添加评论
     * 请求方式：PUT
     * 路径：/{reportId}/comment
     * @param reportId 报告ID
     * @param comment 评论内容
     * @return 返回评论添加成功或失败的结果
     */
    @Operation(summary = "教师对某篇报告添加评论", description = "教师根据报告ID对报告添加评论")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.TEACHER_REPORT)
    @PutMapping("/{reportId}/comment")
    public Result<String> addCommentToReport(
            @Parameter(description = "报告id", required = true,example = "1")
            @PathVariable("reportId")  @Min(1) @NotNull Long reportId,
            @Parameter(description = "评论", required = true,example = "这篇报告写的很好")
            @RequestParam @Length(max = 1023) @NotNull String comment
    )
    {
        log.info("接收请求：为报告 {} 添加评论", reportId);
        reportService.addCommentToReport(reportId, comment);
        log.info("成功为报告 {} 添加评论", reportId);
        return Result.success("评论添加成功！");
    }
    /**
     * 教师端查询某学生最新提交的实验报告
     * 根据学生index、课程ID和实验ID查询学生的最新实验报告
     * 请求方式：GET
     * 路径：/students/{studentIndex}/latest
     * @param studentIndex 学生索引
     * @param courseId 课程ID
     * @param experimentId 实验ID
     * @return 返回学生最新提交的实验报告
     */
    @Operation(summary = "教师端查询某学生最新提交的实验报告", description = "根据学生index、课程ID和实验ID查询学生的最新实验报告")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.TEACHER_REPORT)
    @GetMapping("/students/{studentIndex}/latest")
    public ResponseEntity<?> getStudentLatestReport(
            @Parameter(description = "学生索引", required = true,example = "2")
            @PathVariable("studentIndex") @Min(1) @NotNull Long studentIndex,
            @Parameter(description = "课程id", required = true,example = "42041301")
            @RequestParam("courseId") @Min(1) @NotNull Long courseId,
            @Parameter(description = "实验id", required = true,example = "11")
            @RequestParam("experimentId") @Min(1) @NotNull Long experimentId
    )
    {
        log.info("接收请求：获取学生 {} 在课程 {} 中实验 {} 的最新报告", studentIndex, courseId, experimentId);
        String fileUrl = reportService.getLatestReportContent(studentIndex, courseId, experimentId);
        if(fileUrl == null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Result.fail(ResponseCode.BUSINESS_DATA_NOT_FOUND, "该学生还未提交实验报告"));
        }

        try {
            return fileDownloadHelper.downloadFile(fileUrl);
        } catch (IOException e) {
            log.error("读取文件流失败，fileUrl: {}，错误信息: {}", fileUrl, e.getMessage(), e);
            // 错误处理，文件下载失败
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Result.fail(ResponseCode.SYSTEM_FILE_DOWNLOAD_ERROR, "获取实验报告失败"));
        }
    }
    /**
     * 教师端查看学生报告的状态
     * 教师端根据报告ID查看学生报告的提交状态
     * 请求方式：GET
     * 路径：/{reportId}/status
     * @param reportId 报告ID
     * @return 返回报告的状态
     */
    @Operation(summary = "教师端查看学生报告的状态", description = "教师端根据报告index查看学生报告的状态")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.TEACHER_REPORT)
    @GetMapping("/{reportId}/status")
    public Result<String> getReportStatus(
            @Parameter(description = "报告id", required = true,example = "1")
            @PathVariable("reportId") @Min(1) @NotNull Long reportId
    )
    {
        log.info("接收请求：获取报告状态，reportId: {}", reportId);
        String status = reportService.getReportStatus(reportId);
        log.info("成功获取报告 {} 的状态为：{}", reportId, status);
        return Result.success(status);
    }
    /**
     * 查看学生报告的分数
     * 根据reportId查看学生报告的分数
     * 请求方式：GET
     * 路径：/{reportId}/score
     * @param reportId 报告ID
     * @return 返回报告的分数
     */
    @Operation(summary = "查看学生报告的分数", description = "根据reportId查看学生报告的分数")
    @GetMapping("/{reportId}/score")
    public Result<Float> getReportScore(
            @Parameter(description = "报告id", required = true,example = "1")
            @PathVariable("reportId")  @Min(1) @NotNull Long reportId
    )
    {
        log.info("接收请求：获取报告分数，reportId: {}", reportId);
        Float score = reportService.getReportScore(reportId);
        log.info("成功获取报告 {} 的分数为：{}", reportId, score);
        return Result.success(score);
    }

    /**
     * 生成实验报告
     * 根据请求的格式生成报告PDF或Word
     * 请求方式：POST
     * 路径：/generate/{format}
     * @param format 格式类型，支持 "pdf" 或 "word"
     * @param reportDTO 实验报告数据
     * @return 返回报告的下载链接或文件
     */
    @Operation(summary = "生成实验报告")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.STUDENT_REPORT)
    @PostMapping("/generate/{format}")
    public ResponseEntity<?> generateReportPDF(
            @PathVariable @Length(max = 5) @NotNull String format, // pdf/word
            @RequestBody @NotNull ExperimentReportDto reportDTO
    ) {
        log.info("接收请求：生成实验报告，格式：{}", format);
        // 参数校验
        if (!Set.of("pdf", "word").contains(format.toLowerCase())) {
            log.warn("不支持的报告格式类型：{}", format);
            return ResponseEntity.badRequest()
                    .body(Result.fail(ResponseCode.BUSINESS_PARAM_ERROR, "不支持的格式类型"));
        }

        //获取学生index
        Long studentIndex = getStudentIndexByUserIndex.get();
        //获取该学生的老师
        Long teacherIndex = studentService.selectTeacherIndexByStudentIndex(studentIndex);

        log.info("获取实验报告模板，courseId: {}, experimentId: {}, teacherIndex: {}",
                reportDTO.getCourseId(), reportDTO.getExperimentId(), teacherIndex);
        //获取实验报告模板
        String ReportTemplateFileUrl = experimentService.getExperimentReportTemplate
                (reportDTO.getCourseId(),reportDTO.getExperimentId(),teacherIndex);

        try {
            // 分发处理逻辑
            return "pdf".equalsIgnoreCase(format)
                    ? handlePdfGeneration(reportDTO, studentIndex, teacherIndex, ReportTemplateFileUrl)
                    : handleWordGeneration(reportDTO, ReportTemplateFileUrl);
        } catch (IllegalArgumentException e) {
            log.error("参数错误", e);
            return ResponseEntity.badRequest()
                    .body(Result.fail(ResponseCode.BUSINESS_PARAM_ERROR, e.getMessage()));
        } catch (Exception e) {
            log.error("生成报告失败", e);
            return ResponseEntity.badRequest()
                    .body(Result.fail(ResponseCode.INTERNAL_SERVER_ERROR, "生成报告失败：" + e.getMessage()));
        }
    }

    //=================== PDF处理逻辑 ===================//
    private ResponseEntity<?> handlePdfGeneration(ExperimentReportDto reportDTO,
                                                  Long studentIndex,
                                                  Long teacherIndex,
                                                  String templateUrl) throws Exception {
        log.info("开始生成 PDF 报告...");

        Long latestReportId = reportService.getLatestReportId(studentIndex, reportDTO.getCourseId(), reportDTO.getExperimentId());
        if(latestReportId != null){
            String reportStatus = reportService.getReportStatus(latestReportId);
            if(reportStatus.equals("graded")){
                throw new BusinessException(ResponseCode.BUSINESS_REPORT_GRADED, "该学生曾提交的报告已评分，无法再提交新的报告");
            }
        }

        String pdfUrl = documentService.generateReportPDF(reportDTO, templateUrl);

        // 提交记录
        Timer timer = new Timer(0L, Timestamp.valueOf(LocalDateTime.now()), null);
        timerMapper.insert(timer);

        Report report = new Report(0L, pdfUrl, null, null, "submitted",
                studentIndex, teacherIndex, timer.getTimerId());
        reportService.submitReport(report);

        // 更新关联关系
        reportService.updateReport(report.getReportId(), studentIndex,
                reportDTO.getExperimentId(), reportDTO.getCourseId());

        log.info("报告 PDF 生成成功并已提交，reportId: {}, pdfUrl: {}", report.getReportId(), pdfUrl);

        return ResponseEntity.ok(Result.success(pdfUrl));
    }

    //=================== Word处理逻辑 ===================//
    private ResponseEntity<?> handleWordGeneration(ExperimentReportDto reportDTO, String templateUrl) throws Exception {
        log.info("开始生成 Word 报告...");
        MultipartFile reportFile = documentService.generateReportWord(reportDTO, templateUrl);

        if (reportFile == null || reportFile.isEmpty()) {
            log.error("Word 报告生成失败，返回文件为 null");
            throw new IllegalArgumentException("生成Word文件失败");
        }

        // 构建响应头
        String encodedFileName = URLEncoder.encode(
                Objects.requireNonNull(reportFile.getOriginalFilename()),
                StandardCharsets.UTF_8
        ).replace("+", "%20");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(Objects.requireNonNull(reportFile.getContentType())));
        headers.setContentLength(reportFile.getSize());
        headers.add("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);

        log.info("Word 报告生成成功，文件名：{}", encodedFileName);
        return new ResponseEntity<>(reportFile.getBytes(), headers, HttpStatus.OK);
    }
}