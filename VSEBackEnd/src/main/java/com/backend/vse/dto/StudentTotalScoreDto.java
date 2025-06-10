package com.backend.vse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentTotalScoreDto {
    private @NotNull @Min(1) Long studentIndex;      // 学生学号
    private @NotNull @Min(0) Float totalScore;       // 学生总分
}