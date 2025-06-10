package com.backend.vse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCourseDto {
    @NotNull @Min(1) Long courseId;                  // 课号
    @NotNull @Length String courseName;              // 课程名称
    @NotNull @Length String semester;                // 学期
    @NotNull @Min(2000) @Max(2099) Integer year;     // 学年
    @NotNull @NotEmpty List<Long> teacherList;       // 教师列表
}