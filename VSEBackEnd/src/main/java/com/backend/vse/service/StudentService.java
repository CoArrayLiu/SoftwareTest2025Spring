package com.backend.vse.service;
import com.backend.vse.common.Result;
import com.backend.vse.dto.*;
import com.backend.vse.entity.Student;
import org.springframework.stereotype.Service;
import java.util.List;

@Service

public interface StudentService {
    /**
     * 根据课程ID获取学生信息列表
     * @param courseId 课程ID
     * @return 学生信息列表
     */
    List<StudentInfoDto> selectByCourseId(long courseId);

    /**
     * 根据课程 ID 查询该课程未选的学生信息
     * 该方法用于查询某一课程中未选该课程的学生信息。
     *
     * @param courseId 课程 ID（long 类型）
     * @return 返回学生信息的列表（List<StudentInfoDto>）
     */
    List<StudentInfoDto> selectFreeByCourseId(long courseId);

    /**
     * 添加已存在的学生到课程中
     * 该方法用于将已存在的学生添加到某一课程中。
     *
     * @param courseId     课程 ID（long 类型）
     * @param teacherIndex 教师索引（long 类型）
     * @param studentId    学生id（long 类型）
     */
    void addExistStudent(Long courseId, Long teacherIndex, Long studentId);

    /**
     * 删除已存在的学生从课程中
     * 该方法用于将已存在的学生从某一课程中删除。
     *
     * @param courseId     课程 ID（long 类型）
     * @param teacherIndex 教师索引（long 类型）
     * @param studentId    学生id（long 类型）
     */
    void deleteExistStudent(Long courseId, Long teacherIndex, Long studentId);

    /**
     * 导入学生信息
     * 该方法用于导入学生数据。通常用于批量添加学生信息。
     *
     * @param studentImportDto 包含学生导入信息的 DTO（StudentImportDto 类型）
     * @return 返回导入操作结果，包含操作成功与否及可能的错误信息（Result<String> 类型）
     */
    Result<String> importStudent(StudentImportDto studentImportDto);

    /**
     * 批量导入学生信息
     * 该方法用于批量导入学生数据，通常用于批量添加多个学生。
     *
     * @param studentDtos 包含学生信息的 DTO 列表（List<StudentImportDto> 类型）
     * @return 返回批量导入操作的结果（Result<BatchImportResult> 类型）
     */
    Result<BatchImportResult> batchImportStudents(List<StudentImportDto> studentDtos);

    /**
     * 根据学生索引查询该学生对应的教师索引
     * 该方法用于查询某个学生所选课程的教师索引。
     *
     * @param studentIndex 学生索引（long 类型）
     * @return 返回教师索引（Long 类型）
     */
    Long selectTeacherIndexByStudentIndex(long studentIndex);

    /**
     * 查询所有学生的管理信息
     * 该方法用于查询系统中所有学生的管理信息。
     *
     * @return 返回学生管理信息的列表（List<StudentManageInfoDto> 类型）
     */
    List<StudentManageInfoDto> fetchStudentInfo();

    /**
     * 更新学生信息
     * 该方法用于更新学生的管理信息，通常用于修改学生的个人信息或状态。
     *
     * @param studentManageInfoDto 包含学生管理信息的 DTO（StudentManageInfoDto 类型）
     */
    void updateStudent(StudentManageInfoDto studentManageInfoDto);

    /**
     * 根据用户索引查询该学生所有信息
     *
     * @param userIndex 用户索引（long 类型）
     * @return 返回学生信息（Student 类型）
     */
    Student selectStudentByUserIndex(Long userIndex);
}