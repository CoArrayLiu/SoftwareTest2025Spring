package com.backend.vse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructorDto {
    @Length @NotNull String name;            // 姓名
    @Length @NotNull String department;      // 部门
    @Length @NotNull String email;           // 邮箱
}