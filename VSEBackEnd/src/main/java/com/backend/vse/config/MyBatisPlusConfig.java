package com.backend.vse.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis Plus 配置类，用于配置分页插件等功能。
 * 启用后，MyBatis Plus 将支持分页查询的自动处理。
 */
@Configuration
// 为 MyBatis Plus 配置分页插件
public class MyBatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        // 创建 MyBatis Plus 插件容器
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 创建分页插件，并指定数据库类型为 MySQL
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);

        // 将分页插件添加到 MyBatis Plus 插件容器中
        interceptor.addInnerInterceptor(paginationInterceptor);

        return interceptor;
    }
}