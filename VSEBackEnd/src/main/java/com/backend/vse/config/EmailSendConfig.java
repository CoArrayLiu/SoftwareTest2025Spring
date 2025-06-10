package com.backend.vse.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 邮件发送配置类，用于从配置文件中自动加载邮件服务器相关参数。
 * 使用 @ConfigurationProperties 实现将配置项注入到 Java 对象中。

 * 对应 application.properties 或 application.yml 中的配置项前缀为 spring.mail，例如：

 * spring.mail.host=smtp.example.com
 * spring.mail.port=587
 * spring.mail.username=your_email@example.com
 * spring.mail.password=your_password
 * spring.mail.from=your_email@example.com
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.mail")  // 会自动绑定 application.properties/yml 中以 spring.mail 为前缀的配置项
public class EmailSendConfig {
    // 邮件服务器地址
    private String host;
    // 邮件服务器端口
    private Integer port;
    // 邮件服务器用户名
    private String username;
    // 邮件服务器密码
    private String password;
    // 发件人邮箱
    private String from;
}