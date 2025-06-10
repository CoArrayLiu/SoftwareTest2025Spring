package com.backend.vse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private @NotNull @Min(1) Long userIndex; //用户id
    private @NotNull @Length String name;    //姓名
    private @NotNull @Length String email;   //邮箱
    private @NotNull @Length String avatar;  //头像
    private @NotNull @Length String role;    //角色
}