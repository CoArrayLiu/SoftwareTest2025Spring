package com.backend.vse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TeacherReviewStudentReport 实体类
 * 表示教师对学生报告的评审关系。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "teacher_review_student_report")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherReviewStudentReport {
    Long teacherIndex; // 教师索引
    Long studentIndex; // 学生索引
    Long reportId; // 报告索引
}
