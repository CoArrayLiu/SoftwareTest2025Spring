package com.backend.vse.common;

import com.backend.vse.dto.FileMetadataResponseDto;
import com.backend.vse.service.CosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class FileDownloadHelper {

    @Autowired
    private CosService cosService;

    public ResponseEntity<byte[]> downloadFile(String fileUrl) throws IOException {
        // 调用服务层查询文件信息
        FileMetadataResponseDto metadata = cosService.getFileMetadataByUrl(fileUrl);

        // 获取文件内容
        try (InputStream fileContent = metadata.getFileContent()) {
            byte[] content = fileContent.readAllBytes();

            // 构建响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(metadata.getContentType()));
            headers.setContentLength(metadata.getContentLength());
            headers.setContentDisposition(buildContentDisposition(fileUrl));

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(content);
        }
    }

    private ContentDisposition buildContentDisposition(String fileUrl) {
        String fileName = extractFileName(fileUrl);
        return ContentDisposition.attachment()
                .filename(fileName, StandardCharsets.UTF_8)
                .build();
    }

    private String extractFileName(String fileUrl) {
        return fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
    }
}
