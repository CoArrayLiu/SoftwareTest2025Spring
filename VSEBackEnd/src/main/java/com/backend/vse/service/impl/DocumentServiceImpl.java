package com.backend.vse.service.impl;

import com.backend.vse.common.ResponseCode;
import com.backend.vse.dto.ExperimentScoreDto;
import com.backend.vse.dto.StudentCourseScoreDto;
import com.backend.vse.exception.BusinessException;
import com.backend.vse.service.CosService;
import com.backend.vse.service.DocumentService;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import com.backend.vse.dto.ExperimentReportDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor // Lombok 自动生成构造函数
@Slf4j
public class DocumentServiceImpl implements DocumentService{
    private final CosService cosService;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @FunctionalInterface
    private interface ReportProcessor<T> {
        T process(XWPFDocument doc, File tempFile, ExperimentReportDto reportDTO) throws Exception;
    }

    @Override
    public String generateReportPDF(ExperimentReportDto reportDTO, String templateUrl) {
        return handleReportGeneration(reportDTO, templateUrl, this::convertToPdfAndUpload);
    }

    @Override
    public MultipartFile generateReportWord(ExperimentReportDto reportDTO, String templateUrl) {
        return handleReportGeneration(reportDTO, templateUrl, this::createWordMultipartFile);
    }

    //=================== 核心处理逻辑 ===================//
    private <T> T handleReportGeneration(ExperimentReportDto reportDTO,
                                         String templateUrl,
                                         ReportProcessor<T> processor) {
        if (reportDTO == null) {
            throw new IllegalArgumentException("报告数据不能为空");
        }

        XWPFDocument doc = null;
        File tempFile = null;
        try (InputStream templateStream = cosService.getFileMetadataByUrl(templateUrl).getFileContent()) {
            doc = new XWPFDocument(templateStream);
            log.info("开始处理模板: {}", templateUrl);

            processParagraphs(doc.getParagraphs(), reportDTO);
            processTables(doc.getTables(), reportDTO);

            tempFile = File.createTempFile("report_", ".docx");
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                doc.write(fos);
            }

            return processor.process(doc, tempFile, reportDTO);
        } catch (Exception e) {
            log.error("报告生成失败", e);
            throw new BusinessException(ResponseCode.BUSINESS_FILE_ERROR,"报告生成失败: " + e.getMessage());
        } finally {
            try {
                if (doc != null) { doc.close();}
            } catch (IOException e) {
                log.warn("关闭文档资源失败", e);
            }
            if (tempFile != null && !tempFile.delete()) {
                log.warn("临时文件删除失败: {}", tempFile.getAbsolutePath());
            }
        }
    }

    private void processParagraphs(List<XWPFParagraph> paragraphs, ExperimentReportDto reportDTO) {
        for (XWPFParagraph paragraph : paragraphs) {
            String originalText = paragraph.getText();
            // 先处理图片占位符（优先级高于普通文本）
            if (originalText.contains("${image:")) {
                processImagePlaceholder(paragraph, reportDTO.getOther());
            }
            // 文本替换逻辑
            else if (originalText.contains("${")) {
                String replacedText = replacePlaceholders(originalText, reportDTO);
                replacedText = replaceArrayPlaceholders(replacedText, reportDTO.getOther());
                rebuildParagraph(paragraph, replacedText);
            }
        }
    }

    private void processTables(List<XWPFTable> tables, ExperimentReportDto reportDTO) {
        for (XWPFTable table : tables) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    String originalText = cell.getText();
                    if (originalText.contains("${")) {
                        String replacedText = replacePlaceholders(originalText, reportDTO);
                        replacedText = replaceArrayPlaceholders(replacedText, reportDTO.getOther());
                        rebuildCell(cell, replacedText);
                    }
                }
            }
        }
    }

    private void rebuildParagraph(XWPFParagraph paragraph, String newText) {
        // 保存原有段落属性
        CTPPr pPr = paragraph.getCTP().getPPr();

        // 清空原有内容，但保留至少一个run以维持段落结构
        List<XWPFRun> runs = paragraph.getRuns();
        if (!runs.isEmpty()) {
            // 保留第一个run的样式，替换文本
            XWPFRun firstRun = runs.get(0);
            firstRun.setText(newText, 0);
            // 删除后续的runs
            for (int i = runs.size() - 1; i > 0; i--) {
                paragraph.removeRun(i);
            }
        } else {
            // 没有run时创建新run并设置样式
            XWPFRun newRun = paragraph.createRun();
            newRun.setFontFamily("SimSun");
            newRun.setText(newText);
        }

        // 重新应用段落属性
        if (pPr != null) {
            paragraph.getCTP().setPPr(pPr);
        }
    }

    private void rebuildCell(XWPFTableCell cell, String newText) {
        cell.removeParagraph(0);
        XWPFParagraph newParagraph = cell.addParagraph();
        newParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun newRun = newParagraph.createRun();
        newRun.setFontFamily("SimSun");
        newRun.setText(newText);
    }

    private void processImagePlaceholder(XWPFParagraph paragraph, Map<String, Object> otherData) {
        String originalText = paragraph.getText();
        Pattern pattern = Pattern.compile("\\$\\{image:(\\w+)((?:;\\w+=\\d+)*)}");
        Matcher matcher = pattern.matcher(originalText);

        // 收集占位符及其参数
        List<Map.Entry<String, Map<String, Integer>>> placeholders = new ArrayList<>();
        while (matcher.find()) {
            String fullPlaceholder = matcher.group(0);
            String params = matcher.group(2).replaceFirst("^;", ""); // 去除开头的分号

            // 解析参数
            Map<String, Integer> dimensions = new HashMap<>();
            if (!params.isEmpty()) {
                for (String pair : params.split(";")) {
                    String[] kv = pair.split("=");
                    if (kv.length == 2) {
                        try {
                            dimensions.put(kv[0], Integer.parseInt(kv[1]));
                        } catch (NumberFormatException e) {
                            log.warn("参数格式错误: {}", pair);
                        }
                    }
                }
            }

            placeholders.add(new AbstractMap.SimpleEntry<>(fullPlaceholder, dimensions));
        }

        // 逆序处理
        Collections.reverse(placeholders);
        for (Map.Entry<String, Map<String, Integer>> entry : placeholders) {
            String fullPlaceholder = entry.getKey();
            Map<String, Integer> dimensions = entry.getValue();
            String key = extractKeyFromPlaceholder(fullPlaceholder); // 提取 key（需实现此方法）

            clearFullPlaceholder(paragraph, fullPlaceholder);

            Object imageData = otherData.get(key);
            if (imageData != null) {
                try {
                    byte[] imageBytes = parseImageData(imageData);
                    int width = dimensions.getOrDefault("width", 400);
                    int height = dimensions.getOrDefault("height", (int)(width * 0.75));
                    insertImageToParagraph(paragraph, imageBytes, width, height);
                } catch (Exception e) {
                    log.error("图片插入失败: {}", key, e);
                }
            } else {
                insertMissingImagePlaceholder(paragraph);
            }
        }
    }

    private String extractKeyFromPlaceholder(String fullPlaceholder) {
        Pattern keyPattern = Pattern.compile("\\$\\{image:([^;]+?)(?:;.*?)?}");
        Matcher matcher = keyPattern.matcher(fullPlaceholder);
        if (matcher.find()) {
            return matcher.group(1); // 返回第一个捕获组（键名）
        }
        log.warn("占位符格式错误，无法提取键名: {}", fullPlaceholder);
        return null; // 或抛出 IllegalArgumentException
    }

    private void insertMissingImagePlaceholder(XWPFParagraph paragraph) {
        XWPFRun run = paragraph.createRun();
        run.setText("[图片缺失]", 0);
        run.setColor("FF0000"); // 红色
        run.setItalic(true); // 可选：斜体强调
    }

    private void clearFullPlaceholder(XWPFParagraph paragraph, String placeholder) {
        List<XWPFRun> runs = paragraph.getRuns();
        StringBuilder accumulatedText = new StringBuilder();
        int placeholderStart = -1;
        int placeholderEnd = -1;

        // 定位占位符的起始和结束位置
        for (XWPFRun run : runs) {
            String runText = run.getText(0);
            if (runText == null) continue;
            accumulatedText.append(runText);
            if (accumulatedText.toString().contains(placeholder)) {
                placeholderStart = accumulatedText.toString().indexOf(placeholder);
                placeholderEnd = placeholderStart + placeholder.length();
                break;
            }
        }

        if (placeholderStart == -1) return;

        // 分段清理占位符涉及的 Runs
        int currentLength = 0;
        for (int i = 0; i < runs.size(); i++) {
            XWPFRun run = runs.get(i);
            String runText = run.getText(0);
            if (runText == null) continue;

            int runStart = currentLength;
            int runEnd = currentLength + runText.length();
            currentLength += runText.length();

            // 计算当前 Run 是否包含占位符部分
            int deleteStart = Math.max(runStart, placeholderStart);
            int deleteEnd = Math.min(runEnd, placeholderEnd);
            if (deleteStart >= deleteEnd) continue;

            // 切割并保留非占位符部分
            String newText = runText.substring(0, deleteStart - runStart) +
                    runText.substring(deleteEnd - runStart);
            run.setText(newText, 0);

            // 如果 Run 文本为空，删除该 Run
            if (newText.isEmpty()) {
                paragraph.removeRun(i);
                i--; // 调整索引
            }
        }
    }

    private byte[] parseImageData(Object imageData) throws IOException {
        if (imageData instanceof byte[]) {
            return (byte[]) imageData;
        } else if (imageData instanceof String data) {
            // 如果是 Base64 编码
            if (data.startsWith("data:image/")) {
                String base64 = data.split(",")[1];
                return Base64.getDecoder().decode(base64);
            }
            // 如果是 URL
            else if (data.startsWith("http")) {
                return downloadImageFromUrl(data);
            }
        }
        throw new IllegalArgumentException("不支持的图片格式");
    }

    private byte[] downloadImageFromUrl(String url) throws IOException {
        try (InputStream in = new URL(url).openStream()) {
            return in.readAllBytes();
        }
    }

    private void insertImageToParagraph(
            XWPFParagraph paragraph, byte[] imageBytes, int widthPx, int heightPx
    ) throws Exception  {
        // 保留段落原有样式（对齐方式、字体等）
        CTPPr pPr = paragraph.getCTP().getPPr();

        // 创建新 Run 并插入图片
        XWPFRun run = paragraph.createRun();
        run.addPicture(
                new ByteArrayInputStream(imageBytes),
                XWPFDocument.PICTURE_TYPE_PNG,
                "image_" + UUID.randomUUID() + ".png",
                Units.toEMU(widthPx),
                Units.toEMU(heightPx)
        );

        // 恢复段落样式
        if (pPr != null) {
            paragraph.getCTP().setPPr(pPr);
        }
    }

    //=================== PDF处理逻辑 ===================//
    private String convertToPdfAndUpload(XWPFDocument doc, File tempFile, ExperimentReportDto reportDTO)
            throws Exception {
        String fileName = generateFileName(reportDTO, "pdf");

        // 从临时文件重新加载文档
        try (XWPFDocument newDoc = new XWPFDocument(new FileInputStream(tempFile))) {
            // 转换为PDF
            ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
            PdfConverter.getInstance().convert(newDoc, pdfStream, PdfOptions.create());

            // 上传到云存储
            MultipartFile pdfFile = new MockMultipartFile(
                    "report",
                    fileName,
                    "application/pdf",
                    pdfStream.toByteArray()
            );

            return cosService.uploadFile(pdfFile);
        }
    }

    //=================== Word处理逻辑 ===================//
    private MultipartFile createWordMultipartFile(XWPFDocument doc, File tempFile, ExperimentReportDto reportDTO)
            throws Exception {
        String fileName = generateFileName(reportDTO, "docx");
        return new MockMultipartFile(
                "wordFile",
                fileName,
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                Files.newInputStream(tempFile.toPath())
        );
    }

    private String generateFileName(ExperimentReportDto reportDTO, String extension) {
        String baseName = "report_" + reportDTO.getExperimentId() + "_" + System.currentTimeMillis();
        return URLEncoder.encode(baseName, StandardCharsets.UTF_8) + "." + extension;
    }

    private String replacePlaceholders(String text, ExperimentReportDto reportDTO) {
        String result = text;
        Map<String, Object> other = reportDTO.getOther();

        // 替换动态字段
        for (Map.Entry<String, Object> entry : other.entrySet()) {
            String placeholder = "${" + entry.getKey() + "}";
            result = result.replace(placeholder,
                    entry.getValue() != null ? entry.getValue().toString() : "");
        }

        // 替换固定字段
        return result.replace("${experimentId}", safeToString(reportDTO.getExperimentId()))
                .replace("${courseId}", safeToString(reportDTO.getCourseId()))
                .replace("${submit_time}", DATE_FORMAT.format(new Date()));
    }

    private String safeToString(Long value) {
        return value != null ? value.toString() : "";
    }

    private static String replaceArrayPlaceholders(String paragraphText, Map<String, Object> other) {
        // 定义正则表达式来匹配数组的占位符，例如 ${adjust_table[0].index}
        String regex = "\\$\\{([a-zA-Z_][a-zA-Z0-9_]*)\\[(\\d+)]\\.([a-zA-Z_][a-zA-Z0-9_]*)}";

        // 创建正则表达式的 Pattern 和 Matcher
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(paragraphText);

        // 循环匹配占位符并进行替换
        while (matcher.find()) {
            // 获取数组名称、索引和字段名称
            String arrayName = matcher.group(1);    // "adjust_table"
            int index = Integer.parseInt(matcher.group(2));  // 数组索引
            String fieldName = matcher.group(3);     // "index"

            // 从 other 中获取对应的数组对象
            Object arrayObject = other.get(arrayName);
            if (arrayObject instanceof List<?> list) {
                // 检查索引范围
                if (index >= 0 && index < list.size()) {
                    // 类型安全检查：确保元素是 Map 类型
                    if (list.get(index) instanceof Map<?, ?> rawMap) {
                        // 安全类型转换：将键和值强制转换为 String 类型
                        Map<String, String> arrayElement = new HashMap<>();
                        for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
                            String key = String.valueOf(entry.getKey());
                            String value = String.valueOf(entry.getValue());
                            arrayElement.put(key, value);
                        }

                        // 获取字段值
                        String value = arrayElement.getOrDefault(fieldName, "");
                        paragraphText = paragraphText.replace(matcher.group(0), value);
                    } else {
                        // 类型不匹配时的处理逻辑
                        String errorMessage = String.format(
                                "数组 '%s' 中的元素类型不是 Map (索引: %d)",
                                arrayName, index
                        );
                        log.warn(errorMessage);
                        paragraphText = paragraphText.replace(matcher.group(0), "");
                    }
                }
            }
        }
        return paragraphText;
    }

    @Override
    public Workbook generateExcel(List<StudentCourseScoreDto> dataList, double averageScore) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("学生成绩表");

        // ==================== 表头生成 ====================
        Row headerRow = sheet.createRow(0);
        List<String> headers = new ArrayList<>();
        headers.add("学生姓名");

        if (!dataList.isEmpty()) {
            for (ExperimentScoreDto exp : dataList.get(0).getExperimentScores()) {
                headers.add(exp.getExperimentName());
            }
        }
        headers.add("平均分");

        // 应用表头样式
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers.get(i));
            cell.setCellStyle(createHeaderStyle(workbook)); // 加粗居中
        }

        // ==================== 数据填充 ====================
        float totalScore = 0f;
        int rowNum = 1;
        for (StudentCourseScoreDto dto : dataList) {
            Row row = sheet.createRow(rowNum++);
            int cellNum = 0;
            Float scoreSum = 0f;

            // 学生姓名（应用文本居中样式）
            Cell nameCell = row.createCell(cellNum++);
            nameCell.setCellValue(dto.getName());
            nameCell.setCellStyle(createTextCenterStyle(workbook));

            // 实验分数
            for (ExperimentScoreDto exp : dto.getExperimentScores()) {
                Cell scoreCell = row.createCell(cellNum++);
                if (exp.getScore() == null) {
                    scoreCell.setCellValue("-");
                } else {
                    scoreCell.setCellValue(exp.getScore());
                    scoreSum += exp.getScore();
                }
                scoreCell.setCellStyle(createTextCenterStyle(workbook));
            }

            // 平均分（保留两位小数）
            float average = scoreSum / dto.getExperimentScores().size();
            Cell avgCell = row.createCell(cellNum);
            avgCell.setCellValue(String.format("%.2f", average)); // 格式化为两位小数
            avgCell.setCellStyle(createNumberStyle(workbook));
            totalScore += average;
        }

        // ==================== 班级平均行 ====================
        Row avgRow = sheet.createRow(rowNum);
        Cell avgTitleCell = avgRow.createCell(0);
        avgTitleCell.setCellValue("班级平均分");
        avgTitleCell.setCellStyle(createBoldStyle(workbook)); // 沿用原有加粗样式

        Cell classAvgCell = avgRow.createCell(headers.size() - 1);
        classAvgCell.setCellValue(String.format("%.2f", totalScore / dataList.size()));
        classAvgCell.setCellStyle(createResultStyle(workbook));

        // ==================== 列宽调整 ====================
        for (int i = 0; i < headers.size(); i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, (int) (sheet.getColumnWidth(i) * 1.75)); // 微调系数
        }

        return workbook;
    }

    // ==================== 样式工具方法 ====================
    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = createTextCenterStyle(workbook);

        // 字体加粗
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);

        return style;
    }

    private static CellStyle createNumberStyle(Workbook workbook) {
        CellStyle style = createTextCenterStyle(workbook);

        // 数字格式（保留两位小数）
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("0.00"));

        return style;
    }

    private static CellStyle createTextCenterStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        // 居中
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;
    }

    // 设置生成字体为粗体
    private static CellStyle createBoldStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    private static CellStyle createResultStyle(Workbook workbook) {
        CellStyle style = createTextCenterStyle(workbook);

        // 设置生成字体为粗体
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);

        // 数字格式（保留两位小数）
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("0.00"));

        return style;
    }
}