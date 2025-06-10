package com.backend.vse.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentImportDto {
    @ExcelProperty("学号")
    private @NotNull @Length String id;           // 学号
    @ExcelProperty("姓名")
    private @NotNull @Length String name;         // 姓名
    @ExcelProperty("邮箱")
    private @NotNull @Length String email;        // 邮箱
    @ExcelProperty("学校")
    private @NotNull @Length String school;       // 学校
}
