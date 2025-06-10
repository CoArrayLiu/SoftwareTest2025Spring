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
 * Student 实体类
 * 表示学生的相关信息。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("Student")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Student {
    @TableId(type = IdType.AUTO)
    Long studentIndex;  // 学生索引
    String school;  // 学校
    Float courseScore;  // 课程分数
    @JsonSerialize(using= ToStringSerializer.class)
    Long userIndex;  // 用户索引
}
