package com.backend.vse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperimentScoreDto {
    @NotNull @Min(1) Long experimentId;           // 实验id
    @NotNull @Length String experimentName;       // 实验名称
    @NotNull @Min(0) Float score;                 // 实验成绩
    @NotNull @Min(0) Integer fullScore;           // 实验满分
}