package com.backend.vse.tools;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 赵帅涛
 * @date 2023/04/14

 * 阿里云OSS常量配置类
 * 负责加载阿里云OSS相关配置信息
 */
@Component
public class ConstantPropertiesTools implements InitializingBean {
    // OSS 服务的访问端点 URL
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    // 阿里云的 AccessKey ID
    @Value("${aliyun.oss.accessKeyId}")
    private String keyId;

    // 阿里云的 AccessKey Secret
    @Value("${aliyun.oss.accessKeySecret}")
    private String keySecret;

    // 存储桶的名称
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    //定义公开静态常量
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
    }
}
