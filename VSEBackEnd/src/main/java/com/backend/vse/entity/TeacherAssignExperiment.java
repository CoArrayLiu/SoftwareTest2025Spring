package com.backend.vse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * TeacherAssignExperiment 实体类
 * 表示教师为课程分配的实验任务。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "teacher_assign_experiment")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherAssignExperiment {
    Long courseId; // 课程 ID
    Long experimentId;         // 实验 ID
    Long teacherIndex;         // 教师索引
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    Date startTime; // 实验开始时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    Date endTime;  // 实验结束时间
    Float experimentFullScore;  // 实验满分
    String experimentBook;  // 实验书籍
    String reportTemplate;  // 实验报告模板
    String experimentContent;  // 实验内容
}
