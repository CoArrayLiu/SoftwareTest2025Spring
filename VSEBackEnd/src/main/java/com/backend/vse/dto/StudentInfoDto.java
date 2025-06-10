package com.backend.vse.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentInfoDto {
    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull @Min(1) Long studentIndex;      // 学生索引
    @NotNull @Length String school;          // 学校
    @NotNull @Length String studentName;     // 学生姓名
    @NotNull @Min(0) @Max(1) Byte status;
    @NotNull @Length String studentId;       // 学生学号
    @NotNull @Length String email;           // 学生邮箱
    @NotNull @Length String role;            // 学生角色
}