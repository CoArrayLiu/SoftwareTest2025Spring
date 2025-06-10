package com.backend.vse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@MapperScan(basePackages = {"com.backend.vse.mapper"})
@OpenAPIDefinition(
        info = @Info(
                title = "虚拟仿真实验平台接口文档",
                version = "1.0.0",
                description = "API文档",
                contact = @Contact(name = "VSE Team")
        ),
        servers = {
                @Server(url = "/api", description = "默认服务器")
        }
)
public class VSEApplication {
    public static void main(String[] args) {
        SpringApplication.run(VSEApplication.class, args);
    }
}
