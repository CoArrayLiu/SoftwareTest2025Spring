package com.backend.vse.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "批量导入学生结果")
public class BatchImportResult {
    @Schema(description = "总记录数")
    private int totalCount;

    @Schema(description = "成功数")
    private int successCount;

    @Schema(description = "失败数")
    private int failureCount;

    @Schema(description = "失败记录列表")
    private List<FailureRecord> failureRecords;

    @Data
    @Schema(description = "失败记录")
    public static class FailureRecord {
        @Schema(description = "学号")
        private String id;

        @Schema(description = "姓名")
        private String name;

        @Schema(description = "失败原因")
        private String reason;
    }
}
