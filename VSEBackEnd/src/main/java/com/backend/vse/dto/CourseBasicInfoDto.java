package com.backend.vse.dto;

import java.util.Objects;

import com.backend.vse.entity.Course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseBasicInfoDto implements Comparable<CourseBasicInfoDto> {
    @Min(1) @NotNull Long courseId;      //课程ID
    @Length @NotNull String courseName;  //课程名
    @Length @NotNull String semester;    //学期
    @Min(2000) @Max(2099) @NotNull Integer year;           //年份

    public CourseBasicInfoDto(Course course){
        if(course == null){
            return;
        }
        this.courseId = course.getCourseId();
        this.courseName = course.getCourseName();
        this.semester = course.getSemester();
        this.year = course.getYear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseBasicInfoDto that = (CourseBasicInfoDto) o;
        return Objects.equals(courseId, that.courseId) &&
                Objects.equals(courseName, that.courseName) &&
                Objects.equals(semester, that.semester) &&
                year.equals(that.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseName, semester, year);
    }

    @Override
    public int compareTo(@NotNull CourseBasicInfoDto o) {
        if(!year.equals(o.getYear())){
            return year - o.year;
        }
        else if("spring".equals(semester) && "fall".equals(o.semester)){
            return -1;
        }
        else if("fall".equals(semester) && "spring".equals(o.semester)){
            return 1;
        }
        else{
            return 0;
        }
    }
}