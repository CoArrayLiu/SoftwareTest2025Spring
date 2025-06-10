package com.backend.vse.tools.Enums;

public enum TeachingActivity implements Activity{
    MANAGE_COURSE, // 课程设置权限
    PUBLISH_PROJECT, // 发布项目权限
    PARTICIPATE_PROJECT, // 参与项目权限
    GRADE_ASSIGNMENT, // 批改作业权限
    MANAGE_GRADE, // 成绩管理权限
    TEACHER_MANAGE_QUOTE, // 供求管理教师权限
    STUDENT_QUOTE, // 学生报价权限
    TEACHER_COURSE_INFO, // 教师获取课程相关信息
    STUDENT_COURSE_INFO, // 学生获取课程相关信息
    MANAGE_COURSE_STUDENT, // 管理课程中学生名单
    MANAGE_COURSE_EXPERIMENT, // 管理课程实验
    TEACHER_EXPERIMENT_INFO, // 教师获取实验相关信息
    STUDENT_EXPERIMENT_INFO, // 学生获取实验相关信息
    STUDENT_REPORT, // 学生端对报告进行操作
    TEACHER_REPORT, // 教师端对报告进行操作
}
