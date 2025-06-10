package com.backend.vse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User Entity Class
 * Represents a user in the system.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("User")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "user_index", type = IdType.AUTO)  // 确保自增
    Long userIndex;  //数据库自增主码
    String id;       //学号或工号
    String name;     //姓名
    String password; //密码
    String email;    //邮箱
    String avatar;   //头像url
    Byte status;     //用户状态，0表示未激活，1表示已激活，2表示已注销
    String role;     //用户角色，包括system_manager, responsible_teacher, teacher, student
}