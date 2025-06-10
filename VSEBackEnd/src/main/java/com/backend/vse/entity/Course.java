package com.backend.vse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Course 实体类
 * 表示课程的相关信息。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("Course")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Course {
    @JsonSerialize(using=ToStringSerializer.class)
    /*
    被用在 courseId 字段上，这意味着当 Course 对象被序列化为JSON时，
    courseId 字段的值会被转换为字符串形式。
    即使 courseId 是一个 Long 类型的值，
    它也会被序列化为一个JSON字符串而不是数字。
     */
    @TableId(type = IdType.INPUT)
    Long courseId;
    String courseName;
    String semester;
    int year;
}
