package com.backend.vse.controller;

import com.backend.vse.dto.Account;
import com.backend.vse.dto.UserInfo;
import com.backend.vse.entity.User;
import com.backend.vse.common.Result;
import com.backend.vse.interceptor.JwtInterceptor;
import com.backend.vse.interceptor.util.JwtUtil;
import com.backend.vse.service.CourseService;
import com.backend.vse.service.OssService;
import com.backend.vse.service.UserService;
import com.backend.vse.tools.MailSender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
@Api(tags = {"User"})
@RestController
//@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @ApiOperation("根据用户的id和学校返回指定用户（登录）")
    @PostMapping("login")
    public Result<Map<String, String>> login(@RequestBody HashMap<String, String> map) throws Exception {
        //这里的username应该指的是用户id
        String username = map.get("username");
        String school = map.get("school");
        String password = map.get("password");
        User user = userService.findUserByIdAndSchool(username, school);

        if (user == null) {
            return Result.fail(10001, "账号不存在");
        }
        if (!Objects.equals(user.getPassword(), password)) {
            return Result.fail(10001, "密码错误");
        }

        if (user.getStatus() == 1) { // 用户账号已激活，成功登录，返回token
            String userIndex = user.getIndex().toString();
            //根据用户index获取token
            String token = JwtUtil.sign(userIndex);
            Map<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("token", token);
            return Result.success(hashMap);
        } else { // 用户账户未激活，激活流程
            // 发送邮箱验证码 返回值为验证码
            StringBuilder code = MailSender.sendEmail(user.getEmail());
            // 操作 Redis 中 String 类型的键值对的接口
            ValueOperations<String, String> operations = redisTemplate.opsForValue();

            String msg;
            if (operations.get(user.getEmail()) != null) {
                msg = "验证码已发送，请三分钟后重试";
            } else {
                // 验证码存入redis
                operations.set(user.getEmail(), String.valueOf(code));
                // 设置过期时间为3分钟
                redisTemplate.expire(user.getEmail(), 3, TimeUnit.MINUTES);
                msg = "账户需要激活，验证码已发送";
            }
            return Result.fail(1, msg);
        }
    }

    @ApiOperation("获取用户信息（登录之后根据token）")
    @GetMapping("account")
    public Result<UserInfo> getUserInfo() {
        // 从拦截器中获取用户index
        Long index = JwtInterceptor.getLoginUser();
        User user = userService.findUserByIndex(index);
        if (user == null) {
            return Result.fail(10001, "用户不存在");
        }

        Account account = new Account(user.getIndex(),user.getName(), user.getAge(), user.getGender(), user.getEmail(), user.getAvatar());
        //UserInfo类为dto前端类，参数分别为：账户信息Account类、该用户的课程信息列表、permissions、role
        UserInfo userInfo = new UserInfo(account,courseService.getCourseInfoByIndex(index) ,new String[]{"edit", "delete", "add"}, user.getRole());

        return Result.success(userInfo);
    }

    @ApiOperation("用户账户激活")
    @PostMapping("activate")
    public Result<String> accountActivate(@RequestBody HashMap<String, String> map) {
        String code = map.get("code");
        String password = map.get("password");
        String email = map.get("username");
        // 验证验证码是否正确
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        if (Objects.equals(code, operations.get(email))) { // 验证码填写正确
            // 更新用户的密码 和 账户状态
            Integer result = userService.activateUserAccount(email, password, (byte) 1);
            if (result == 0) return Result.fail(400, "激活失败");
            else return Result.success("账户激活成功");
        } else {
            return Result.fail(400, "验证码输入错误");
        }
    }

    @ApiOperation("用户修改密码")
    @PostMapping("update")
    public Result<String> changePassword(

            @ApiParam(name = "email", value = "用户的邮箱", required = true)
            @RequestParam("email") String email,

            @ApiParam(name = "oldPassword", value = "用户当前的密码", required = true)
            @RequestParam("oldPassword") String oldPassword,

            @ApiParam(name = "newPassword", value = "用户新的密码", required = true)
            @RequestParam("newPassword") String newPassword
    ) {

        // 检验邮箱格式
        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            return Result.fail(10008,"邮箱格式有误");
        }
        //获取用户学号，根据邮箱"@"前的内容
        String userId = email.substring(0, atIndex);
        User user = userService.findUserById(Long.valueOf(userId));
        if(user==null){
            return Result.fail(40004, "用户不存在");
        }
        //获取用户旧密码
        String queryPassword = user.getPassword();
        if(!queryPassword.equals(oldPassword)){
            return Result.fail(10001, "密码有误");
        }
        //根据用户邮箱查找到对应用户，并修改其密码
        Integer res = userService.updatePassword(email, newPassword);
        if (res == 0) return Result.fail(400, "用户修改密码失败");
        else return Result.success("用户修改密码成功");
    }

    @Autowired
    private OssService ossService;

    @ApiOperation("修改头像")
    @PostMapping("user/change-avatar")
    public Result<Map<String, Object>> changeAvatar(@RequestPart("file") MultipartFile avatar) {
        String url;
        Long userId = JwtInterceptor.getLoginUser();
        if (userId == null) {
            // userId = 199L;
            return Result.fail(4001,"缺失用户ID");
        }
        try {
            // 获取头像文件的url
            url = ossService.uploadFile(avatar);
            // 根据用户id查找到对应用户，并修改其头像
            userService.updateAvatar(url, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(400, "上传错误");
        }
        Map<String, Object> res = new HashMap<>();
        res.put("fileUrl", url);
        //返回文件存储的url
        return Result.success(res);
    }
}
