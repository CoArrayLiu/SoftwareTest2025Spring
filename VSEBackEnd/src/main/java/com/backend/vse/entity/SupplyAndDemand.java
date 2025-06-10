package com.backend.vse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

/**
 * 供给与需求 实体类
 * 表示拍卖的相关信息。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "SupplyAndDemand")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupplyAndDemand {
    @TableId(type = IdType.INPUT)
    Long studentIndex; // 学生索引
    Long courseId; // 课程索引
    Integer money; // 金额
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Timestamp submitTime; // 提交时间
    String quoteType; // 报价类型
}

