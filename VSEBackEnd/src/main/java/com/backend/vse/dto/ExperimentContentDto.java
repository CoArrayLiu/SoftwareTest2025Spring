package com.backend.vse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperimentContentDto {
    @Length @NotNull String title;       //实验题目
    @Length @NotNull String content;     //实验内容
    @Length @NotNull String file;        //实验指导书url
}