package com.backend.vse.common;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.backend.vse.exception.ExcelParseException;
import com.backend.vse.dto.StudentImportDto;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
public class StudentImportListener extends AnalysisEventListener<StudentImportDto> {
    private final List<StudentImportDto> studentDtos = new ArrayList<>();
    private final List<String> errorMessages = new ArrayList<>();

    @Override
    public void invoke(StudentImportDto data, AnalysisContext context) {
        // 数据校验
        if (StringUtils.isBlank(data.getId())) {
            throw new ExcelParseException("第" + (context.readRowHolder().getRowIndex() + 1) + "行学号不能为空");
        }

        // 数据预处理
        data.setId(data.getId().trim());
        data.setName(StringUtils.trimToEmpty(data.getName()));

        studentDtos.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // Excel 解析完成后的处理
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        // 自定义异常处理
        throw new ExcelParseException("第" + (context.readRowHolder().getRowIndex() + 1) + "行数据格式错误: " + exception.getMessage());
    }
}