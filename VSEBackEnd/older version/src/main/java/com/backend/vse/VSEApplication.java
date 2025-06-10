package com.backend.vse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 指定MyBatis的Mapper接口所在的包路径,用来操作数据库
@MapperScan("com.backend.vse.mapper")
public class VSEApplication {
    // 入口方法
    public static void main(String[] args) {
        SpringApplication.run(VSEApplication.class, args);
    }
}
