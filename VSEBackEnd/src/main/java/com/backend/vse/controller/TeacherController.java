package com.backend.vse.controller;

import com.backend.vse.common.ResponseCode;
import com.backend.vse.dto.TeacherManageInfoDto;
import com.backend.vse.interceptor.RequirePermission;
import com.backend.vse.tools.Enums.PermissionType;
import com.backend.vse.tools.Enums.UserAndAccountActivity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import com.backend.vse.common.Result;
import com.backend.vse.dto.TeacherImportDto;
import com.backend.vse.service.TeacherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Teacher", description = "教师管理接口")
@RestController
@RequestMapping("/users/teachers")
@RequiredArgsConstructor // Lombok 自动生成构造函数
@Slf4j
public class TeacherController {
    private final TeacherService teacherService;
    /**
     * 导入一个教师（新增用户）
     * 导入一个新的教师信息并将其添加到系统中
     * 请求方式：POST
     * 路径：/users/teachers
     * @param teacherImportDto 教师信息数据传输对象
     * @return 返回导入教师结果
     */
    @Operation(summary = "导入一个教师（新增用户）")
    @PostMapping()
    @RequirePermission(type = PermissionType.ACCOUNT,account = UserAndAccountActivity.CREATE_ACCOUNT)
    public Result<String> addATeacher(@RequestBody TeacherImportDto teacherImportDto) {
        log.info("开始导入教师：{}", teacherImportDto);
        Result<String> result = teacherService.importTeacher(teacherImportDto);
        log.info("导入教师成功");
        return result;
    }
    /**
     * 查询教师信息列表
     * 获取所有教师的管理信息列表
     * 请求方式：GET
     * 路径：/users/teachers
     * @return 返回所有教师的管理信息
     */
    @Operation(summary = "查询教师信息列表")
    @RequirePermission(type = PermissionType.ACCOUNT, account = UserAndAccountActivity.EDIT_ACCOUNT_INFO)
    @GetMapping()
    public Result<List<TeacherManageInfoDto>> getTeacherList() {
        log.info("开始查询教师信息列表");
        List<TeacherManageInfoDto> teacherList = teacherService.fetchTeacherInfo();
        if(teacherList.isEmpty()){
            log.warn("查询结果为空");
            return Result.fail(ResponseCode.BUSINESS_DATA_EMPTY, "教师信息列表为空");
        }
        log.info("查询成功，共查询到 {} 位教师", teacherList.size());
        return Result.success(teacherList);
    }
}