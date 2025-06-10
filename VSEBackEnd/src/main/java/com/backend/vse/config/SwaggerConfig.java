package com.backend.vse.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

/**
 * Swagger 配置类，使用 OpenAPI 3 规范。
 * 主要用于配置 API 文档的元信息、安全认证方式和服务器信息。
 */
@Configuration
public class SwaggerConfig {

    // 从配置文件中读取服务上下文路径，默认值为 "/api"
    @Value("${server.servlet.context-path:/api}")
    private String contextPath;

    /**
     * 配置自定义的 OpenAPI 文档。
     * 包含服务器信息、文档基本信息、安全配置等。
     *
     * @return 配置完成的 OpenAPI 对象
     */
    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server()
                .url(contextPath)
                .description("默认服务器");

        return new OpenAPI()
                // 设置服务器信息
                .servers(Collections.singletonList(server))
                // 设置接口文档的基本信息：标题、版本、描述、联系人
                .info(new Info()
                        .title("虚拟仿真实验平台接口文档")
                        .version("1.0.0")
                        .description("API文档")
                        .contact(new Contact().name("VSE Team")))
                // 配置安全组件：使用 JWT Token 认证方式
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                                        .name("Authorization")))
                // 应用于所有 API 的安全配置（全局）
                .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));
    }
}