package com.backend.vse.service.impl;

import com.backend.vse.common.ResponseCode;
import com.backend.vse.exception.FileUploadException;
import com.backend.vse.exception.SystemException;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.*;
import com.backend.vse.config.TencentCosConfig;
import com.backend.vse.dto.FileMetadataResponseDto;
import com.backend.vse.service.CosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

@Service
@RequiredArgsConstructor // Lombok 自动生成构造函数
public class CosServiceImpl implements CosService {
    private final COSClient cosClient;
    private final TencentCosConfig tencentCosConfig;

    /**
     * 上传文件到腾讯云COS
     */
    @Override
    public String uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            // 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString().replace("-", "")
                    + "/" + originalFilename;

            // 设置元数据
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(getContentType(originalFilename));

            // 上传文件
            InputStream inputStream = file.getInputStream();
            PutObjectRequest putRequest = new PutObjectRequest(
                    tencentCosConfig.getBucket(),
                    fileName,
                    inputStream,
                    metadata
            );
            cosClient.putObject(putRequest);

            // 返回完整URL（使用配置中的base-url）
            return tencentCosConfig.getBaseUrl() + "/" + fileName;
        } catch (IOException e) {
            throw new SystemException(ResponseCode.SYSTEM_FILE_PROCESS_ERROR,"文件读取失败");
        } catch (CosClientException e) {
            throw new FileUploadException("COS上传失败", e);
        }
    }

    /**
     * 获取文件元数据
     */
    @Override
    public FileMetadataResponseDto getFileMetadataByUrl(String fileUrl) {
        FileMetadataResponseDto response = new FileMetadataResponseDto();

        try {
            // 解析文件路径（从URL中提取Key）
            String fileKey = extractFileKeyFromUrl(fileUrl);

            // 获取元数据
            ObjectMetadata metadata = cosClient.getObjectMetadata(
                    tencentCosConfig.getBucket(),
                    fileKey
            );

            // 填充响应对象
            response.setContentType(metadata.getContentType());
            response.setContentLength(metadata.getContentLength());
            response.setLastModified(metadata.getLastModified());

            // 获取文件内容
            COSObject cosObject = cosClient.getObject(
                    tencentCosConfig.getBucket(),
                    fileKey
            );
            try (InputStream content = cosObject.getObjectContent()) {
                byte[] bytes = content.readAllBytes();
                response.setFileContent(new ByteArrayInputStream(bytes));
            }

        } catch (CosClientException | IOException e) {
            throw new SystemException(ResponseCode.SYSTEM_FILE_PROCESS_ERROR,"文件读取失败");
        }
        return response;
    }

    /**
     * 删除文件
     */
    @Override
    public boolean deleteFile(String fileUrl) {
        try {
            // 解析文件路径
            String fileKey = extractFileKeyFromUrl(fileUrl);

            // 检查文件是否存在
            if (!cosClient.doesObjectExist(tencentCosConfig.getBucket(), fileKey))
                return false;

            // 执行删除
            cosClient.deleteObject(tencentCosConfig.getBucket(), fileKey);

            return true;
        } catch (CosClientException e) {
            throw new SystemException(ResponseCode.SYSTEM_FILE_DELETE_ERROR,"文件删除失败", e);
        }
    }

    /**
     * 从URL中提取文件Key
     */
    private String extractFileKeyFromUrl(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            // 格式示例：https://tjvse.cos.ap-shanghai.myqcloud.com/abc/1.jpg
            String path = url.getPath();
            return path.startsWith("/") ? path.substring(1) : path;
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的COS文件URL");
        }
    }

    /**
     * 获取文件格式
     */
    public static String getContentType(String filenameExtension) {
        if (".pdf".equalsIgnoreCase(filenameExtension)) {
            return "application/pdf";
        }
        if (".bmp".equalsIgnoreCase(filenameExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(filenameExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(filenameExtension) ||
                ".jpg".equalsIgnoreCase(filenameExtension) ||
                ".png".equalsIgnoreCase(filenameExtension)) {
            return "image/jpg";
        }
        if (".html".equalsIgnoreCase(filenameExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(filenameExtension)) {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(filenameExtension)) {
            return "application/vnd.visio";
        }
        if (".pptx".equalsIgnoreCase(filenameExtension) ||
                ".ppt".equalsIgnoreCase(filenameExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".docx".equalsIgnoreCase(filenameExtension)) {
            return "application/msword";
        }
        if (".xml".equalsIgnoreCase(filenameExtension)) {
            return "text/xml";
        }
        return "image/jpg";
    }
}
