package com.backend.vse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentExperimentScoreDto {
    private @NotNull @Min(1) Long studentIndex;      // 学生学号
    private @NotNull @Min(1) Long experimentId;      // 实验id
    private @NotNull @Min(0) Float reportScore;      // 报告分数
}