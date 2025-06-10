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
 * Report 实体类
 * 表示实验报告的相关信息。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("Report")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Report {
    @JsonSerialize(using= ToStringSerializer.class)
    @TableId(type = IdType.AUTO)
    Long reportId; // 主键
    String reportContent; // 实验报告内容
    String reportComment; // 实验报告评语
    Float reportScore;       // 实验报告成绩
    String status; // 实验报告状态
    Long studentIndex; // 学生索引
    Long teacherIndex; // 教师索引
    Long timerId; // 定时器索引
}
