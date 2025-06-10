package com.backend.vse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseRelationDto {
    private @NotNull @Min(1) Long studentId;      // 学生ID
    private @NotNull @Min(1) Long courseId;       // 课程ID
    private @NotNull @Min(1) Long teacherId;      // 教师ID
}
