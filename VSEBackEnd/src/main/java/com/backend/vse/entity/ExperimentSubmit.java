package com.backend.vse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ExperimentSubmit 实体类
 * 表示学生提交的实验的相关信息。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "experiment_submit")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExperimentSubmit {
    @JsonSerialize(using= ToStringSerializer.class)
    Long reportId; // 实验报告ID
    @JsonSerialize(using= ToStringSerializer.class)
    Long studentIndex; // 学生索引
    @JsonSerialize(using= ToStringSerializer.class)
    Long experimentId; // 实验ID
    @JsonSerialize(using= ToStringSerializer.class)
    Long courseId; // 课程ID
}
