package com.backend.vse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseScoreDto {
    private @NotNull @Min(1) Long studentIndex;          // 学生序号
    private @NotNull @Length String name;
    private @NotNull @Length String school;              // 学校
    private @NotNull @Min(0) Float courseScore;          // 课程总分
    private @NotNull @NotEmpty List<ExperimentScoreDto> experimentScores;// 实验分数
}