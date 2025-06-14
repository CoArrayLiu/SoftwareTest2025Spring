package com.backend.vse.controller;

import com.backend.vse.common.Result;
import com.backend.vse.dto.ExperimentContentDto;
import com.backend.vse.dto.ExperimentDto;
import com.backend.vse.dto.ExperimentTemplateDto;
import com.backend.vse.entity.CourseExperiment;
import com.backend.vse.entity.Experiment;
import com.backend.vse.interceptor.JwtInterceptor;
import com.backend.vse.service.ExperimentService;
import com.backend.vse.service.OssService;
import com.backend.vse.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Api(tags = {"Experiment"})
@RestController
//@RequestMapping("experiment")
public class ExperimentController {
    @Autowired
    private ExperimentService experimentService;

    @Autowired
    private OssService ossService;

    @Autowired
    private UserService userService;

    @ApiOperation("获取所有实验信息")
    @GetMapping("allexperiment")
    public Result<List<Experiment>> getAllExperiments() {
        return Result.success(experimentService.selectAllExperiments());
    }

    @ApiOperation("根据课程id，返回该课程的所有实验信息")
    @GetMapping("experimentincourse")
    public Result<List<ExperimentDto>> getExperimentsByCourseId(@ApiParam(name = "courseId", value = "课程id", required = true)
                                                                @RequestParam("courseId") Long courseId) {
        List<ExperimentDto> experimentDtoList = experimentService.selectExperimentByCourseId(courseId);
        return Result.success(experimentDtoList);
    }

    @ApiOperation("根据课程id，返回该课程不包含的实验的信息")
    @GetMapping("experimentnotincourse")
    public Result<List<Experiment>> getExceptExperimentsByCourseId(@ApiParam(name = "courseId", value = "课程id", required = true)
                                                                   @RequestParam("courseId") Long courseId) {
        List<Experiment> experimentList = experimentService.selectExceptExperimentByCourseId(courseId);
        return Result.success(experimentList);
    }

    @ApiOperation("根据实验id，获取单个实验信息")
    @GetMapping("experiment/{experimentId}")
    public Result<ExperimentContentDto> getExperimentContentById(@ApiParam(name = "experimentId", value = "实验id", required = true)
                                                                 @PathVariable("experimentId") Long experimentId) {
        Long index = JwtInterceptor.getLoginUser();
        String file = "";
        System.out.print(index);
        //获取该用户该实验的指导书
        if(index!=null){
            file=experimentService.findInstructorByIndexAndExperiment(index,experimentId);
        }
        //根据实验id查找该实验的所有信息
        ExperimentContentDto experimentContentDto = experimentService.selectExperimentContentById(experimentId);
        //设置该实验指导书
        experimentContentDto.setFile(file);
        return Result.success(experimentContentDto);
    }

    @ApiOperation("根据实验id，获取实验报告模板")
    @GetMapping("experiment_template/{experimentId}")
    public Result<ExperimentTemplateDto> getExperimentTemplateById(@ApiParam(name = "experimentId", value = "实验id", required = true)
                                                                   @PathVariable("experimentId") Long experimentId) {
        ExperimentTemplateDto experimentTemplateDto = experimentService.selectExperimentTemplateById(experimentId);
        return Result.success(experimentTemplateDto);
    }

    @ApiOperation("根据课程id和实验id，修改课程中的实验")
    @PutMapping("modify_experimentincourse")
    public Result<String> modifyExperimentInCourse(@RequestBody HashMap<String, String> map) throws ParseException {
        //map中传递的是课程修改后的信息的键值对
        Long courseId = Long.valueOf(map.get("courseId"));
        Long experimentId = Long.valueOf(map.get("experimentId"));
        Timestamp startTime = Timestamp.valueOf(map.get("startTime"));
        Timestamp endTime = Timestamp.valueOf(map.get("endTime"));
        Integer score = Integer.valueOf(map.get("score"));
        //该处设置时间格式似乎未使用
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //根据修改的内容新创建一个courseExperiment对象
        CourseExperiment courseExperiment = new CourseExperiment(courseId, experimentId, startTime, endTime, score, null, null);
        //在course_experiment表中更新实验内容
        int res = experimentService.modifyExperimentInCourse(courseExperiment);
        if (res == 0) return Result.fail(400, "修改实验失败");
        else return Result.success("修改实验成功");
    }

    @ApiOperation("根据课程id和实验id，删除课程中的实验")
    @DeleteMapping("delete_experimentincourse")
    public Result<String> deleteExperimentInCourse(@ApiParam(name = "courseId", value = "课程id", required = true)
                                                   @RequestParam("courseId") Long courseId,
                                                   @ApiParam(name = "experimentId", value = "实验id", required = true)
                                                   @RequestParam("experimentId") Long experimentId) {
        int res = experimentService.deleteExperimentInCourse(courseId, experimentId);
        if (res == 0) return Result.fail(400, "删除失败");
        else return Result.success("删除成功");
    }

    @ApiOperation("根据课程id和实验id，添加课程中的实验")
    @PostMapping("add_experimentincourse")
    public Result<String> deleteExperimentInCourse(@RequestBody HashMap<String, Object> map) {
        String courseId = (String) map.get("courseId");
        //支持批量传递experimentIdList来进行添加
        ArrayList<String> experimentIdList = (ArrayList<String>) map.get("experimentIdList");
        for (String experimentId : experimentIdList) {
            int res = experimentService.insertExperimentInCourse(Long.valueOf(courseId), Long.valueOf(experimentId));
            if (res == 0) return Result.fail(400, "添加实验失败");
        }
        return Result.success("添加实验成功");
    }

    @ApiOperation("上传实验指导书")
    @PostMapping("upload_experiment_instructor")
    public Result<String> uploadExperimentInstructor(@RequestParam(value = "file") MultipartFile file,
                                                     @RequestParam(value = "courseId") String courseId,
                                                     @RequestParam(value = "experimentId") String experimentId) {
        //将id由字符串形式转成long型
        Long courseId_ = Long.valueOf(courseId);
        Long experimentId_ = Long.valueOf(experimentId);
        //将文件存入oss 返回url
        String fileUrl = ossService.uploadFile(file);
        //在数据库course_experiment表中存放对应id和url
        int res = experimentService.updateExperimentInstructor(courseId_, experimentId_, fileUrl);
        if (res == 0) return Result.fail(400, "上传实验指导书失败");
        else return Result.success("上传实验指导书成功");
    }

    @ApiOperation("上传实验报告模板")
    @PostMapping("upload_experiment_template")
    public Result<String> uploadExperimentTemplate(@RequestParam(value = "file") MultipartFile file,
                                                     @RequestParam(value = "courseId") String courseId,
                                                     @RequestParam(value = "experimentId") String experimentId) {

        Long courseId_ = Long.valueOf(courseId);
        Long experimentId_ = Long.valueOf(experimentId);
        // 将文件存入oss 返回url
        String fileUrl = ossService.uploadFile(file);

        int res = experimentService.updateExperimentTemplate(courseId_, experimentId_, fileUrl);
        if (res == 0) return Result.fail(400, "上传实验报告模板失败");
        else return Result.success("上传实验报告模板成功");
    }
}