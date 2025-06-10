package com.backend.vse.controller;

import java.io.IOException;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.backend.vse.common.ResponseCode;
import com.backend.vse.dto.*;
import com.backend.vse.interceptor.RequirePermission;
import com.backend.vse.common.StudentImportListener;
import com.backend.vse.tools.Enums.PermissionType;
import com.backend.vse.tools.Enums.UserAndAccountActivity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.backend.vse.common.Result;
import com.backend.vse.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Tag(name = "Student", description = "学生管理接口")
@RestController
@RequestMapping("/users/students")
@RequiredArgsConstructor // Lombok 自动生成构造函数
@Slf4j
public class StudentController {
    private final StudentService studentService;
    /**
     * 导入一个学生（新增用户）
     * 教师可以通过此接口导入一个学生的基本信息（新增用户）
     * 请求方式：POST
     * 路径：/students
     * @param studentDto 学生信息数据传输对象
     * @return 返回学生导入结果
     */
    @Operation(summary = "导入一个学生（新增用户）")
    @RequirePermission(type = PermissionType.ACCOUNT,account = UserAndAccountActivity.CREATE_ACCOUNT)
    @PostMapping()
    public Result<String> addAStudent(@RequestBody @NotNull StudentImportDto studentDto){
        log.info("导入单个学生，学号: {}", studentDto.getId());
        studentService.importStudent(studentDto);
        log.info("导入单个学生成功，学号: {}", studentDto.getId());
        return Result.success("导入单个学生成功");
    }
    /**
     * 批量导入学生（新增用户）
     * 教师可以通过此接口批量导入多个学生的基本信息（新增用户）
     * 请求方式：POST
     * 路径：/students/batch
     * @param studentDtos 学生信息数据传输对象的列表
     * @return 返回学生批量导入的结果
     */
    @Operation(summary = "批量导入学生（新增用户）")
    @RequirePermission(type = PermissionType.ACCOUNT,account = UserAndAccountActivity.CREATE_ACCOUNT)
    @PostMapping("/batch")
    public Result<BatchImportResult> batchImportStudents(
            @RequestBody @NotNull @NotEmpty List<StudentImportDto> studentDtos)
    {
        log.info("开始批量导入学生，共 {} 条记录", studentDtos.size());
        Result<BatchImportResult> result = studentService.batchImportStudents(studentDtos);
        log.info("批量导入学生成功，共 {} 条记录", studentDtos.size());
        return result;
    }
    /**
     * 批量导入学生（通过Excel）
     * 教师可以通过此接口批量导入学生信息，通过上传Excel文件来进行学生数据的导入
     * 请求方式：POST
     * 路径：/students/import/excel
     * 请求类型：multipart/form-data
     * @param file 上传的Excel文件
     * @return 返回学生批量导入的结果
     */
    @Operation(summary = "批量导入学生（通过Excel）")
    @RequirePermission(type = PermissionType.ACCOUNT, account = UserAndAccountActivity.CREATE_ACCOUNT)
    @PostMapping(value = "/import/excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<BatchImportResult> batchImportStudentsFromExcel(
            @RequestParam("file") @NotNull MultipartFile file)
    {
        log.info("开始通过 Excel 导入学生");

        try {
            StudentImportListener listener = new StudentImportListener();
            EasyExcel.read(file.getInputStream(), StudentImportDto.class, listener)
                    .sheet()
                    .doRead();

            List<StudentImportDto> studentDtos = listener.getStudentDtos();
            log.info("Excel 解析成功，共解析出 {} 条学生记录", studentDtos.size());

            Result<BatchImportResult> result = studentService.batchImportStudents(studentDtos);
            log.info("通过 Excel 批量导入学生成功，共 {} 条", studentDtos.size());
            return result;
        } catch (IOException e) {
            log.error("Excel 文件解析失败，错误信息: {}", e.getMessage(), e);
            return Result.fail(ResponseCode.SYSTEM_FILE_PROCESS_ERROR, "文件解析失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("通过 Excel 批量导入学生失败，错误信息: {}", e.getMessage(), e);
            return Result.fail(ResponseCode.INTERNAL_SERVER_ERROR, "导入学生失败：" + e.getMessage());
        }
    }
    /**
     * 查询学生信息列表
     * 可以通过此接口查看所有学生的基本信息
     * 请求方式：GET
     * 路径：/students
     * @return 返回学生信息列表
     */
    @Operation(summary = "查询学生信息列表")
    @RequirePermission(type = PermissionType.ACCOUNT, account = UserAndAccountActivity.EDIT_ACCOUNT_INFO)
    @GetMapping
    public Result<List<StudentManageInfoDto>> getStudentList() {
        log.info("开始查询学生信息列表");
        List<StudentManageInfoDto> students = studentService.fetchStudentInfo();
        if(students.isEmpty()){
            log.warn("查询结果为空");
            return Result.fail(ResponseCode.BUSINESS_DATA_EMPTY, "学生信息列表为空");
        }
        log.info("查询学生信息列表成功，返回 {} 条记录", students.size());
        return Result.success(students);
    }
    /**
     * 更新学生信息
     * 可以根据学生ID更新学生的基本信息
     * 请求方式：PUT
     * 路径：/students/{studentId}
     * @param studentId 学生ID
     * @param request 包含更新信息的学生数据传输对象
     * @return 返回更新结果
     */
    @PutMapping("/{studentId}")
    @RequirePermission(type = PermissionType.ACCOUNT, account = UserAndAccountActivity.EDIT_ACCOUNT_INFO)
    @Operation(summary = "更新学生信息")
    public Result<String> updateStudent(
            @PathVariable("studentId") @NotNull @Length String studentId,
            @RequestBody @Valid StudentManageInfoDto request
    )
    {
        log.info("接收更新学生信息请求，studentId: {}", studentId);
        request.setStudentId(studentId);
        studentService.updateStudent(request);
        log.info("更新学生信息成功，studentId: {}", studentId);
        return Result.success("更新学生信息成功");
    }
}