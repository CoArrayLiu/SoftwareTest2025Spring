package com.backend.vse.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 腾讯云 COS（对象存储）配置类
 * 用于自动加载配置文件中的腾讯云 COS 配置信息，并创建 COS 客户端对象
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "tencent.cloud.cos")
public class TencentCosConfig {
    // 从配置文件中读取 COS 地域信息
    private String region;
    // 从配置文件中读取 SecretId
    private String secretId;
    // 从配置文件中读取 SecretKey
    private String secretKey;
    // 从配置文件中读取 COS 的 Bucket 名称
    private String bucket;
    // 从配置文件中读取文件访问的基础 URL
    private String baseUrl;

    /**
     * 创建并注入 COS 客户端对象（COSClient）
     * 该对象用于执行上传、下载等操作
     *
     * @return COSClient 实例
     */
    @Bean
    public COSClient cosClient() {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region regionConfig = new Region(region);
        ClientConfig clientConfig = new ClientConfig(regionConfig);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        return new COSClient(cred, clientConfig);
    }
}