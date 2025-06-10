package com.backend.vse.service;

import com.backend.vse.service.impl.Personel;

import java.util.List;
import java.util.Map;

public interface AuthorizationService {
     /**
      * 根据用户ID获取单个用户及其权限信息
      * 根据传入的用户ID查询该用户的个人信息和相关权限
      * @param id 用户的唯一ID
      * @return 返回包含用户个人信息和权限的 Personel 对象
      */
     Personel GetSingleUserWithPermission(String id);

     /**
      * 根据用户ID列表获取对应用户及其权限信息
      * 根据传入的用户ID列表，批量查询每个用户的个人信息及其权限
      * @param IdList 用户ID列表
      * @return 返回一个Map，Key为用户ID，Value为对应的 Personel 对象（包含个人信息和权限）
      */
     Map<String, Personel> GetUserPermissionByID(List<String> IdList);

     /**
      * 获取用户的角色
      * 根据用户的唯一索引（userIndex）查询该用户的角色信息
      * @param userIndex 用户的唯一索引
      * @return 返回用户的角色名称
      */
     String getUserRole(Long userIndex);

     /**
      * 检查用户是否具有特定角色的权限
      * 根据用户的唯一索引、角色名称和活动检查该用户是否有权限执行指定活动
      * @param userIndex 用户的唯一索引
      * @param role 角色名称
      * @param activity 用户尝试执行的活动
      * @return 返回是否有权限的布尔值，true 表示有权限，false 表示没有权限
      */
     boolean checkUserPermission(Long userIndex, String role, Object activity);
}