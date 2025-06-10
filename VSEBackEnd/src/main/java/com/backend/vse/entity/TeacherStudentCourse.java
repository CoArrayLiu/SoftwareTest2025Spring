package com.backend.vse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TeacherStudentCourse 实体类
 * 表示学生、教师和课程之间的关系。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "teacher_student_course")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherStudentCourse {
    // 使用 @TableId 注解标识复合主键中的第一个字段
    @TableId(value = "student_index", type = IdType.INPUT)
    private Long studentIndex;  // 学生索引

    // 使用 @TableField 标注其他字段,与数据库中列的字段对应
    @TableField("teacher_index")
    private Long teacherIndex;  // 教师索引

    @TableField("course_id")
    private Long courseId;  // 课程索引
}

