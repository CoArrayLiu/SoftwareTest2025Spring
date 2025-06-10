package com.backend.vse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileMetadataResponseDto {
    private @NotNull @Length String contentType;   // 文件类型
    private @NotNull @Min(1) long contentLength;   // 文件大小
    private Date lastModified;    // 最后修改时间
    private InputStream fileContent; // 文件内容
}
