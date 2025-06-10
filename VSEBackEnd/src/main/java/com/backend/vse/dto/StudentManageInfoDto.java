package com.backend.vse.dto;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentManageInfoDto {
    @JsonSerialize(using= ToStringSerializer.class)
    @NotNull @Min(1) Long userIndex;
    @NotNull @Length String school;
    @NotNull @Length String studentName;
    @NotNull @Length String studentId;
    @NotNull @Min(0) Float courseScore;
    @NotNull @Min(0) @Max(1) Byte status;
    @NotNull @Length String email;
}