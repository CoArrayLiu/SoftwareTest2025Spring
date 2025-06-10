package com.backend.vse.service;
import com.backend.vse.common.Result;
import com.backend.vse.dto.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service

public interface TeacherService {
    /**
     * 导入教师信息
     * 该方法用于批量导入教师信息，接收包含教师信息的 DTO 并进行处理。
     *
     * @param TeacherImportDto 包含教师导入信息的 DTO（TeacherImportDto 类型）
     * @return 返回操作结果，包含成功或失败的提示信息（Result<String> 类型）
     */
    Result<String> importTeacher(TeacherImportDto TeacherImportDto);

    /**
     * 获取教师管理信息
     * 该方法用于查询所有教师的管理信息，返回一个包含教师管理信息的列表。
     *
     * @return 返回教师管理信息列表（List<TeacherManageInfoDto> 类型）
     */
    List<TeacherManageInfoDto> fetchTeacherInfo();
}