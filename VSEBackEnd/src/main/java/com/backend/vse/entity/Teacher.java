package com.backend.vse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Teacher 实体类
 * 表示教师的相关信息。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("Teacher")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Teacher {
    @TableId(type = IdType.AUTO)
    Long teacherIndex; // 教师索引
    String department; // 教师所属部门
    Long userIndex; // 教师对应的用户索引
}
