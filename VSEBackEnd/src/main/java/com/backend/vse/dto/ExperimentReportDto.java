package com.backend.vse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperimentReportDto {
    private @NotNull @Min(1) Long experimentId;
    private @NotNull @Min(1) Long courseId;
    private Map<String, Object> other;
}