package com.backend.vse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Experiment 实体类
 * 表示实验的相关信息。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("Experiment")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Experiment {
    @JsonSerialize(using= ToStringSerializer.class)
    @TableId(type = IdType.INPUT)
    Long experimentId; // 实验ID
    String experimentName; // 实验名称
    String kind; // 实验类型
}
