package com.backend.vse.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface OssService {
    // 上传文件，返回访问URL
    String uploadFile(MultipartFile file);
    String uploadImg(MultipartFile file,String dir,String filename);

    String uploadLongText(String content,String key);
}
