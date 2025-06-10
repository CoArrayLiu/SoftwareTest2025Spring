package com.backend.vse.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentScoreDto implements Comparable {
    @JsonSerialize(using= ToStringSerializer.class)
    Long index;
    @JsonSerialize(using= ToStringSerializer.class)
    Long studentId;
    String studentName;
    float totalScore;

    List<SingleExperimentScoreDto> scoreList;


    @Override
    public int compareTo(Object o) {
        StudentScoreDto stu = (StudentScoreDto) o;
        return (int)(index - stu.getIndex());
    }
}
