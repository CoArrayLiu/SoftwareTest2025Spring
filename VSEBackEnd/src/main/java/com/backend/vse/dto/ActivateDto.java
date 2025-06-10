package com.backend.vse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivateDto {
    private @NotNull @Length String email;       // 用户邮箱
    private @NotNull @Length String password;    // 用户密码
    private @NotNull @Length String code;        // 激活码
}