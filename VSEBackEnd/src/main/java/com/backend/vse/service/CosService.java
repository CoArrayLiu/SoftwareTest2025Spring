package com.backend.vse.service;

import com.backend.vse.dto.FileMetadataResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface CosService {
    /**
     * 上传文件
     * 该方法用于将文件上传到 COS 存储中
     * @param file 要上传的文件（MultipartFile 类型）
     * @return 返回文件的URL地址，上传成功后可以通过该URL访问文件
     */
    String uploadFile(MultipartFile file);

    /**
     * 根据文件URL获取文件的元数据
     * 该方法根据文件的URL查询并返回文件的元数据，包括文件大小、类型等信息
     * @param fileUrl 文件的URL地址
     * @return 返回文件元数据的响应对象（FileMetadataResponseDto 类型）
     */
    FileMetadataResponseDto getFileMetadataByUrl(String fileUrl);

    /**
     * 删除文件
     * 根据文件的URL删除存储在COS中的文件
     * @param fileUrl 文件的URL地址
     * @return 返回删除操作是否成功的布尔值，true表示成功，false表示失败
     */
    boolean deleteFile(String fileUrl);
}