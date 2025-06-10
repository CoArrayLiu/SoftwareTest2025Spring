package com.backend.vse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadExperimentFileDto {
    @NotNull @Min(1) Long courseId;      //课程id
    @NotNull @Min(1) Long experimentId;  //实验id
    @NotNull @Min(1) Long teacherIndex;  //教师index
    @NotNull @Length String file;        //实验指导书/实验报告模板的url
}
