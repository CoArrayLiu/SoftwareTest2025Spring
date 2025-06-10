package com.backend.vse.controller;


import com.backend.vse.service.impl.Personel;
import com.backend.vse.service.impl.AuthorizationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
/**
 * 控制器类：用于处理用户权限相关的请求
 * 使用 @RestController 注解表明这是一个 REST 风格的控制器
 * 使用 @RequestMapping 设定统一的接口前缀为 "/authorizations"
 */
@Tag(name="Authorization", description = "授权所有用户角色")
@RestController
@RequestMapping("/authorizations")
@RequiredArgsConstructor // Lombok 自动生成构造函数
@Slf4j
public class AuthorizationController {
    private final AuthorizationServiceImpl authorizationServiceimpl;
    /**
     * 获取单个用户的权限信息
     * 请求方式：GET
     * 路径：/authorizations/{id}
     * @param id 用户ID
     * @return 包含该用户权限信息的响应实体
     */
    @Operation(summary = "根据用户ID授权单个用户信息")
    @GetMapping("/{id}")
    public ResponseEntity<Personel> getSingleUserPermission(
            @PathVariable("id") String id
    ){
        if(id.isEmpty()){
            log.error("输入的用户id为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try{
            log.debug("开始调用服务层方法获取用户权限信息");
            Personel personel = authorizationServiceimpl.GetSingleUserWithPermission(id);
            log.info("已获取id为 {} 的权限信息", id);
            return ResponseEntity.ok(personel);
        }
        catch(IllegalArgumentException e) {
            log.error("获取id为 {} 的权限信息失败", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        catch (Exception e){
            log.error("系统异常: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
    /**
     * 批量获取多个用户的权限信息
     * 请求方式：POST
     * 路径：/authorizations
     * @param idList 用户ID列表
     * @return 包含多个用户权限信息的Map，键为用户ID，值为对应权限信息
     */
    @Operation(summary = "根据用户ID列表批量授权用户角色")
    @PostMapping()
    public ResponseEntity<Map<String, Personel>> getBatchUserPermissions(
            @RequestBody List<String> idList
    ){
        if(idList.isEmpty()){
            log.error("id列表为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Map<String, Personel> UserPermissions;

        try{
            log.debug("开始调用服务层方法批量获取权限信息");
            UserPermissions = this.authorizationServiceimpl.GetUserPermissionByID(idList);
            log.info("已成功获取{}等{}人的权限信息", idList.get(0), idList.size());
            return ResponseEntity.ok(UserPermissions);
        }
        catch(Exception e){
            log.error("未成功获取{}等{}人的权限信息\n原因为：{}", idList.get(0), idList.size(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}