package com.backend.vse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentReportRecordDto {
    private @NotNull @Min(1) Long reportId;             //报告id
    private @NotNull @Length String reportContent;      //报告的url
    private @NotNull @Length String reportComment;      //教师对报告的评论
    private @NotNull @Min(0) Float reportScore;         //报告分数
    private @NotNull @Length String status;             //报告的状态（已提交、已评分、迟交）
    private @NotNull LocalDateTime submitTime;  //提交时间
    private @NotNull LocalDateTime updateTime;  //更新时间
}