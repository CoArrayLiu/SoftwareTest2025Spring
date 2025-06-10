package com.backend.vse.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.SelectObjectRequest;
import com.backend.vse.common.Result;
import com.backend.vse.dto.ExperimentSubmitDto;
import com.backend.vse.entity.ExperimentSubmit;
import com.backend.vse.entity.StudentAttendCourse;
import com.backend.vse.interceptor.JwtInterceptor;
import com.backend.vse.service.CourseService;
import com.backend.vse.service.OssService;
import com.backend.vse.service.ReportService;
//import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Timestamp;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.*;
import java.util.Date;
import com.aliyun.oss.OSS;

@Api(tags = {"Report"})
@RestController
@RequestMapping("report")
public class ReportController {
    @Autowired
    ReportService reportService;

    @Autowired
    OssService ossService;

    @Autowired
    CourseService courseService;

    @ApiOperation("前端上传图片文件到oss")
    @PutMapping(value = "upload", consumes = {MediaType.ALL_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Result<String> uploadImg(
            @RequestPart("experiment_id") String eId,
            @RequestPart("student_id") String sId,
            @RequestPart("filename") String filename,
            @RequestPart(value = "file") MultipartFile file,
            HttpServletRequest request
    ) {
        String imgUrl = ossService.uploadImg(file,eId,sId+'-'+filename);
        if (imgUrl == null) {
            return Result.fail(400, "上传失败");
        }
        return Result.success(imgUrl);
    }
    @ApiOperation("根据提交者学号和实验ID，获取该学生提交的实验报告")
    @GetMapping("getByInfo")
    public Result<String> getReportByInfo(
            @RequestParam("experiment_id") String eId,
            @RequestParam("student_id") String sId,
            @RequestParam("name") String sName
    ) {
        // 获取环境变量，写入配置信息
        String reportFilename = eId + "-" + sName + "-" + sId + ".docx";
        String endpoint = "oss-cn-shanghai.aliyuncs.com";
        //从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
        String bucketName = "vse2024";
        String accessKeyId = System.getenv("accessKeyId");
        String accessKeySecret = System.getenv("accessKeySecret");
//        System.out.println("accessKeyId");
//        System.out.println(accessKeyId);
//        System.getenv().forEach((key, value) -> {
//            System.out.println(key + ":" + value);
//        });
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 先查看文件是否存在
        try {
            // 下载Object到本地文件，并保存到指定的本地路径中。如果指定的本地文件存在会覆盖，不存在则新建。
            // 如果未指定本地路径，则下载后的文件默认保存到示例程序所属项目对应本地路径中。
            ossClient.getObject(new GetObjectRequest(bucketName, reportFilename));
        }
        // 若不存在
        catch (OSSException oe) {
            //向OSS服务器提交请求时出现错误
            System.out.println(oe);
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
            return Result.fail(404, "未找到文件");
        } catch (ClientException ce) {
            //与OSS服务器连接时出现错误
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
            return Result.fail(404, "未找到文件");
        }
        // 若存在
        URL signedUrl = null;
        try {
            // 指定生成的签名URL过期时间，单位为毫秒。本示例以设置过期时间为1小时为例。
            Date expiration = new Date(new Date().getTime() + 3600 * 1000L);
            // 生成签名URL。
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, reportFilename, HttpMethod.GET);
            // 设置过期时间。
            request.setExpiration(expiration);
            // 通过HTTP GET请求生成签名URL。
            signedUrl = ossClient.generatePresignedUrl(request);
            // 打印签名URL。
            System.out.println("signed url for getObject: " + signedUrl);
            return Result.success(signedUrl.toString());
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
            return Result.fail(404, "授权异常");
        }
    }

    @ApiOperation("学生提交实验数据")
    @PostMapping(value = "submitData")
    // 接收的数据为json形式
    public Result<Object> studentSubmitData(
            @RequestBody JSONObject dynamicJson
    ) {
        Process proc;
        //当前工作目录
        String currentWorkingDirectory = System.getProperty("user.dir");
        // python脚本的路径
        // 若当前环境为开发环境
        String pythonScriptPath = currentWorkingDirectory + "/src/main/resources/" + "fillExpTemplate.py";
        // 若当前环境为生产环境
        if ("production".equals(System.getProperty("env"))) {
            pythonScriptPath = currentWorkingDirectory + "/fillExpTemplate.py";
        }
//        System.out.println(pythonScriptPath);
//        System.out.println(currentWorkingDirectory);
        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            String jsonString = objectMapper.writeValueAsString(dynamicJson);
//            System.out.println("jsonString: "+jsonString);
            // 处理传入的 JSON 数据，将其转换成字符串
            String jsonString = dynamicJson.toString();
            StringBuilder result = new StringBuilder();
//            System.out.println("jsonString: "+jsonString);
            // 转义
            for (int i = 0; i < jsonString.length(); i++) {
                char currentChar = jsonString.charAt(i);
                result.append(currentChar);

                // 如果当前字符是双引号，前方插入\
                if (currentChar == '"' && i > 0 && i < jsonString.length() - 1) {
                    result.insert(result.length() - 1, '\\');
                }
            }
//            System.out.println("result:\n" + result);
//            System.out.println("现在执行:\n" + "python " + pythonScriptPath + " " + "\"" + result + "\"");
            // 若当前环境为生产环境
            if ("production".equals(System.getProperty("env"))) {
                //运行一条cmd命令，用于启动python脚本
                //将用户提交的实验数据根据报告模版生成实验报告
                proc = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "python " + pythonScriptPath + " " + "\"" + result + "\""});
            }
            // 若当前环境为开发环境
            else {
                proc = Runtime.getRuntime().exec("python " + pythonScriptPath + " " + "\"" + result + "\"");
            }
            // 通过 BufferedReader 读取Python脚本的输出，并将其内容存储到 returnMsg 中
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            StringBuilder returnMsg = new StringBuilder();
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                returnMsg.append(line);
            }
            System.out.println("returnMsg:\n" + returnMsg);
            in.close();
            proc.waitFor();
            return Result.success(returnMsg.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail(400, e.getCause().toString());

        } catch (InterruptedException e) {
            e.printStackTrace();
            return Result.fail(401, e.getCause().toString());

        }
//        String currentWorkingDirectory = System.getProperty("user.dir");
//        String pythonScriptPath = currentWorkingDirectory+"\\"+"fillExpTemplate.py";
//        List<String> scriptArguments = Arrays.asList(dynamicJson.toString());
//
//        int exitCode = PythonRunner.runScript(pythonScriptPath, scriptArguments);
//
//        System.out.println("Python脚本执行完毕，退出码：" + exitCode);
    }

    @ApiOperation("根据提交者序号和实验ID，获取该学生提交的实验报告信息")
    @GetMapping("getStudentSubmit")
    public Result<ExperimentSubmitDto> teacherReview(@ApiParam(name = "index", value = "提交者序号", required = true)
                                                     @RequestParam("index") Long index,
                                                     @ApiParam(name = "reportId", value = "实验报告ID", required = true)
                                                     @RequestParam("reportId") Long reportId) {
        ExperimentSubmitDto experimentSubmitDto = reportService.selectByIndexAndExperimentId(index, reportId);
        return Result.success(experimentSubmitDto);
    }


}
