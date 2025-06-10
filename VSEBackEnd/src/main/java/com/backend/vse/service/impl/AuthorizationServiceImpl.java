package com.backend.vse.service.impl;

import com.backend.vse.common.ResponseCode;
import com.backend.vse.entity.User;
import com.backend.vse.exception.AuthException;
import com.backend.vse.mapper.UserMapper;
import com.backend.vse.service.AuthorizationService;
import com.backend.vse.tools.Enums.Roles;
import com.backend.vse.tools.Enums.TeachingActivity;
import com.backend.vse.tools.Enums.UserAndAccountActivity;
import com.backend.vse.tools.LoggerManager;
import com.backend.vse.tools.PermissionMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor // Lombok 自动生成构造函数
public class AuthorizationServiceImpl implements AuthorizationService {
    private final UserMapper userMapper;

    @Override
    public Personel GetSingleUserWithPermission(String id){
        User user = Optional.ofNullable(userMapper.selectByUserId(id))
                .orElseThrow(() ->{
                    LoggerManager.ServiceLogger.warn("没有找到id为 {} 的用户", id);
                    return new IllegalArgumentException("User not found with id " + id);
                });
        // 这里假设User.Role与Enums.Roles里完全一致
        try{
            String role = user.getRole();
            Roles userRole = Roles.valueOf(role.toUpperCase());
            return PersonelImpl.CreatePersonel(userRole);
        }catch (IllegalArgumentException e){
            LoggerManager.ServiceLogger.error("无效的角色 {}", user.getRole());
            throw new IllegalArgumentException("Invalid role: " + user.getRole());
        }
    }

    @Override
    public Map<String, Personel> GetUserPermissionByID(List<String> IdList){
        if(IdList.isEmpty()){
            LoggerManager.ServiceLogger.warn("在授权角色时，传入的id列表为空");
            return Collections.emptyMap();
        }

        Map<String, Personel> ans = new HashMap<>();
        for(String id : IdList){
            Personel person = this.GetSingleUserWithPermission(id);
            ans.put(id, person);
        }
        return ans;
    }


    @Override
    public String getUserRole(Long userIndex) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(User::getRole)
                .eq(User::getUserIndex, userIndex);
        User user = Optional.ofNullable(userMapper.selectOne(queryWrapper))
                .orElseThrow(() -> new AuthException(ResponseCode.AUTH_ROLE_NOT_FOUND, "用户角色未配置"));
        return user.getRole().toLowerCase();
    }

    @Override
    public boolean checkUserPermission(Long userIndex, String role, Object activity) {
        String userRole = getUserRole(userIndex);
        if (userRole == null) {
            return false;
        }

        if (activity instanceof TeachingActivity) {
            return PermissionMapper.hasPermission(userRole, (TeachingActivity) activity);
        } else if (activity instanceof UserAndAccountActivity) {
            return PermissionMapper.hasPermission(userRole, (UserAndAccountActivity) activity);
        }
        return false;
    }
}
