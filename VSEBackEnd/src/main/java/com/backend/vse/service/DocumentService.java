package com.backend.vse.service;

import com.backend.vse.dto.ExperimentReportDto;
import com.backend.vse.dto.StudentCourseScoreDto;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface DocumentService {
    /**
     * 生成实验报告的PDF文件
     * 该方法根据传入的实验报告数据（ExperimentReportDto）和报告模板文件路径，生成一个PDF格式的实验报告。
     *
     * @param reportDTO 实验报告的数据对象，包含实验报告的相关信息（ExperimentReportDto 类型）
     * @param ReportTemplateFileUrl 模板文件的URL路径，指向用于生成报告的Word模板文件（String 类型）
     * @return 返回生成的PDF报告的文件路径或URL（String 类型）
     * @throws IOException 如果文件读取或生成过程中发生IO异常，将抛出此异常
     */
    String generateReportPDF(ExperimentReportDto reportDTO,String ReportTemplateFileUrl) throws IOException;

    /**
     * 生成实验报告的Word文件
     * 该方法根据传入的实验报告数据（ExperimentReportDto）和报告模板文件路径，生成一个Word格式的实验报告。
     *
     * @param reportDTO 实验报告的数据对象，包含实验报告的相关信息（ExperimentReportDto 类型）
     * @param ReportTemplateFileUrl 模板文件的URL路径，指向用于生成报告的Word模板文件（String 类型）
     * @return 返回生成的Word报告的文件（MultipartFile 类型）
     * @throws IOException 如果文件读取或生成过程中发生IO异常，将抛出此异常
     */
    MultipartFile generateReportWord(ExperimentReportDto reportDTO, String ReportTemplateFileUrl) throws IOException;

    /**
     * 生成课程成绩单的excel
     * 该方法根据传入的学生成绩单数组（StudentCourseScoreDto），生成一个Excel格式的成绩单。
     *
     * @param dataList 课程中学生成绩单数组
     * @return 返回生成的Excel报告的文件（Workbook 类型）
     */
    Workbook generateExcel(List<StudentCourseScoreDto> dataList, double averageScore);
}