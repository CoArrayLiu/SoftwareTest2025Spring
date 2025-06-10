package com.backend.vse.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperimentDto {
    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull @Min(1) Long experimentId;          // 实验ID
    @NotNull @Length String kind;                // 实验类型
    @NotNull @Length String experimentName;      // 实验名称
    @NotNull @Length String experimentBook;      // 实验书
    @NotNull @Length String reportTemplate;      // 报告模板
    @NotNull @Min(0) Float experimentFullScore;  // 实验总分
    @NotNull Date startTime;             // 实验开始时间
    @NotNull Date endTime;               // 实验结束时间
}