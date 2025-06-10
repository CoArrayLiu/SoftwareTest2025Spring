package com.backend.vse.controller;

import com.backend.vse.common.GetStudentIndexByUserIndex;
import com.backend.vse.common.ResponseCode;
import com.backend.vse.common.Result;
import com.backend.vse.dto.SupplyAndDemandDto;
import com.backend.vse.entity.SupplyAndDemand;
import com.backend.vse.interceptor.RequirePermission;
import com.backend.vse.mapper.StudentMapper;
import com.backend.vse.service.CourseService;
import com.backend.vse.service.SupplyAndDemandService;
import com.backend.vse.tools.Enums.PermissionType;
import com.backend.vse.tools.Enums.TeachingActivity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@Tag(name = "SupplyAndDemand", description = "供给与需求子实验")
@RequestMapping("/courses/{courseId}/supply-demand")
@RequiredArgsConstructor // Lombok 自动生成构造函数
@Slf4j
public class SupplyAndDemandController {
    // 自动注入各类服务和Mapper
    private final SupplyAndDemandService supplyAndDemandService;
    private final CourseService courseService;
    private final StudentMapper studentMapper;

    // 声明工具类变量，但不直接初始化
    private GetStudentIndexByUserIndex getStudentIndexByUserIndex;

    // 在依赖注入完成后初始化工具类
    @PostConstruct
    public void init() {
        this.getStudentIndexByUserIndex = new GetStudentIndexByUserIndex(studentMapper);
    }
    /**
     * 获取当前学生报价
     * 获取当前已登录的学生对指定课程的报价金额
     * 请求方式：GET
     * 路径：/quotes/current
     * @param courseId 课程ID
     * @return 返回当前学生对该课程的报价金额
     */
    @Operation(summary = "获取当前学生报价", description = "获取当前已登录的学生的报价金额")
    @RequirePermission(type = PermissionType.TEACHING, teaching = TeachingActivity.STUDENT_QUOTE)
    @GetMapping("/quotes/current")
    public Result<Integer> getCurrentQuote(
            @Parameter(description = "课程ID", required = true, example = "42041301")
            @PathVariable("courseId") @Min(1) @NotNull Long courseId
    )
    {
        log.info("开始获取当前学生对课程 {} 的报价", courseId);
        Long studentIndex = getStudentIndexByUserIndex.get();
        int money = supplyAndDemandService.getQuoteMoneyOfStudent(courseId, studentIndex);
        log.info("获取成功，学生 {} 的报价为 {} 元", studentIndex, money);
        return Result.success(money);
    }
    /**
     * 获取学生所有报价数据
     * 获取所有学生对指定课程的报价金额
     * 请求方式：GET
     * 路径：/quotes
     * @param courseId 课程ID
     * @return 返回所有学生对该课程的报价数据
     */
    @Operation(summary = "获取学生所有报价数据", description = "获取所有学生的报价金额")
    @GetMapping("/quotes")
    public Result<List<SupplyAndDemandDto>> getAllQuotes(
            @Parameter(description = "课程ID", required = true, example = "42041301")
            @PathVariable("courseId") @Min(1) @NotNull Long courseId
    )
    {
        log.info("开始获取课程 {} 所有学生的报价数据", courseId);
        List<SupplyAndDemandDto> list = supplyAndDemandService.getQuoteAllByCourseId(courseId);
        if(list.isEmpty()){
            log.warn("查询结果为空");
            return Result.fail(ResponseCode.BUSINESS_DATA_EMPTY, "学生报价数据为空");
        }
        log.info("成功获取 {} 条报价记录", list.size());
        return Result.success(list);
    }
    /**
     * 进行报价
     * 当前已登录的学生对指定课程进行报价
     * 请求方式：POST
     * 路径：/quotes
     * @param courseId 课程ID
     * @param money 报价金额
     * @param quote_type 报价类型
     * @return 返回报价提交结果
     */
    @Operation(summary = "进行报价", description = "当前已登录的学生进行报价")
    @RequirePermission(type = PermissionType.TEACHING, teaching = TeachingActivity.STUDENT_QUOTE)
    @PostMapping("/quotes")
    public Result<String> createQuote(
            @Parameter(description = "课程ID", required = true, example = "42041301")
            @PathVariable("courseId") @Min(1) @NotNull Long courseId,
            @Parameter(description = "报价金额", required = true, example = "100")
            @RequestParam("money") @Min(0) @NotNull Integer money,
            @Parameter(description = "报价类型", required = true)
            @RequestParam("quote_type") @Length(max = 10) @NotNull String quote_type)
    {
        log.info("开始提交报价，课程ID：{}，金额：{}，类型：{}", courseId, money, quote_type);
        Long studentIndex = getStudentIndexByUserIndex.get();

        SupplyAndDemand supplyAndDemand = new SupplyAndDemand(
                studentIndex, courseId, money,
                Timestamp.valueOf(LocalDateTime.now()), quote_type);

        supplyAndDemandService.QuoteOfStudent(supplyAndDemand);
        log.info("学生 {} 报价成功", studentIndex);
        return Result.success("报价已成功提交或更新");
    }
    /**
     * 删除报价
     * 根据课程ID删除该课程的所有报价记录
     * 请求方式：DELETE
     * 路径：/quotes
     * @param courseId 课程ID
     * @return 返回删除报价的结果
     */
    @Operation(summary = "删除报价", description = "根据课号删除报价")
    @RequirePermission(type = PermissionType.TEACHING, teaching = TeachingActivity.TEACHER_MANAGE_QUOTE)
    @DeleteMapping("/quotes")
    public Result<String> clearQuotes(
            @Parameter(description = "课程ID", required = true, example = "42041301")
            @PathVariable("courseId") @Min(1) @NotNull Long courseId)
    {
        log.info("尝试删除课程 {} 的所有报价记录", courseId);
        supplyAndDemandService.deleteQuoteByCourseId(courseId);
        log.info("成功删除课程 {} 的报价记录", courseId);
        return Result.success("成功删除指定课程的报价记录");
    }
    /**
     * 获取对应课程供给与需求实验的投票是否开启
     * 根据课程ID获取该课程供给与需求实验的投票是否已开启
     * 请求方式：GET
     * 路径：/vote-status
     * @param courseId 课程ID
     * @return 返回该课程投票是否已开启
     */
    @Operation(summary = "获取对应课程供给与需求实验的投票是否开启", description = "根据courseId，获取对应课程供给与需求实验的投票是否开启")
    @GetMapping("/vote-status")
    public Result<Boolean> getQuote(
            @Parameter(description = "课程ID", required = true, example = "42041301")
            @PathVariable("courseId") @Min(1) @NotNull Long courseId)
    {
        log.info("查询课程 {} 的投票开启状态", courseId);
        Boolean status = courseService.getQuote(courseId);
        log.info("课程 {} 的投票状态为：{}", courseId, status);
        return Result.success(status);
    }
    /**
     * 更改对应课程供给与需求实验的投票开启状态
     * 根据课程ID更改该课程供给与需求实验的投票开启状态
     * 请求方式：PUT
     * 路径：/vote-status
     * @param courseId 课程ID
     * @return 返回投票状态更改的结果
     */
    @Operation(summary = "更改对应课程供给与需求实验的投票开启状态", description = "根据courseId，更改对应课程供给与需求实验的投票开启状态")
    @RequirePermission(type = PermissionType.TEACHING, teaching = TeachingActivity.TEACHER_MANAGE_QUOTE)
    @PutMapping("/vote-status")
    public Result<String> changeQuote(
            @Parameter(description = "课程ID", required = true, example = "42041301")
            @PathVariable("courseId") @Min(1) @NotNull Long courseId)
    {
        log.info("正在切换课程 {} 的投票开启状态", courseId);

        courseService.changeQuote(courseId);
        boolean quote = courseService.getQuote(courseId);

        log.info("投票状态切换成功，当前投票状态：{}", quote);
        return Result.success("更改成功,当前投票状态为"+(quote?"开启":"关闭"));
    }
}