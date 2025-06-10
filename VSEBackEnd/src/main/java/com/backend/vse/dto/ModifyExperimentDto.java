package com.backend.vse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyExperimentDto {
    @NotNull @Min(1) Long courseId ;         //课程id
    @NotNull @Min(1) Long experimentId;      //实验id
    @NotNull Timestamp startTime;    //开始时间
    @NotNull Timestamp endTime ;     //结束时间
    @NotNull @Min(0) Float score;            //总分
    @NotNull @Length String experimentContent;//实验内容
}