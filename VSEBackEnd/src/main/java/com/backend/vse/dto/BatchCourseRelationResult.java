package com.backend.vse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchCourseRelationResult {
    private int totalCount;          // 总记录数
    private int successCount;        // 成功数量
    private int failureCount;        // 失败数量
    private List<FailureDetail> failureDetails;  // 失败详情

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FailureDetail {
        private StudentCourseRelationDto failedData;  // 失败的数据
        private String failureReason;                 // 失败原因
    }
}