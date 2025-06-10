package com.backend.vse.controller;

import com.backend.vse.common.Result;
import com.backend.vse.entity.Experiment;
import com.backend.vse.interceptor.RequirePermission;
import com.backend.vse.service.ExperimentService;
import com.backend.vse.tools.Enums.PermissionType;
import com.backend.vse.tools.Enums.TeachingActivity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Tag(name = "Experiment", description = "实验管理接口")
@RestController
@RequestMapping("/experiments")
@RequiredArgsConstructor // Lombok 自动生成构造函数
@Slf4j
public class ExperimentController {
    // 自动注入各类服务和Mapper
    private final ExperimentService experimentService;

    /**
     * 获取所有实验信息
     * 请求方式：GET
     * 路径：/experiments
     * @return 包含所有实验信息的列表
     */
    @Operation(summary = "获取所有实验信息", description = "获取所有实验信息")
    @GetMapping()
    public Result<List<Experiment>> getAllExperiments() {
        log.info("获取所有实验信息");
        return Result.success(experimentService.selectAllExperiments());
    }

    /**
     * 添加实验
     * 请求方式：POST
     * 路径：/experiments
     * @return 是否添加成功
     */
    @Operation(summary = "添加实验", description = "添加实验")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.MANAGE_COURSE_EXPERIMENT)
    @PostMapping()
    public Result<String> addExperiment(
            @RequestBody Experiment experiment
    )
    {
        log.info("添加实验：{}", experiment);
        experimentService.addExperiment(experiment);
        log.info("添加实验成功：{}", experiment.getExperimentName());
        return Result.success("添加实验成功");
    }

    /**
     * 删除实验
     * 请求方式：DELETE
     * 路径：/experiments/{experimentId}
     * @return 是否删除成功
     */
    @Operation(summary = "删除实验", description = "删除实验")
    @RequirePermission(type = PermissionType.TEACHING,teaching = TeachingActivity.MANAGE_COURSE_EXPERIMENT)
    @DeleteMapping("/{experimentId}")
    public Result<String> deleteExperiment(
            @Parameter(description = "实验ID", required = true, example = "11")
            @PathVariable("experimentId") @NotNull @Min(1) Long experimentId
    )
    {
        log.info("尝试删除实验，实验ID：{}", experimentId);
        experimentService.deleteExperiment(experimentId);
        log.info("删除实验成功，实验ID：{}", experimentId);
        return Result.success("删除实验成功");
    }
}