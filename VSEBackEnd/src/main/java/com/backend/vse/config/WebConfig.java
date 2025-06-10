package com.backend.vse.config;

import com.backend.vse.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.*;

import java.util.Arrays;
import java.util.Collections;

/**
 * Web 配置类
 * 主要功能包括：
 * 1. 配置 JWT 拦截器用于接口权限验证；
 * 2. 配置静态资源访问路径。
 * 3. 配置全局跨域支持
 * 4. 配置资源映射
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final JwtInterceptor jwtInterceptor;

    /**
     * 注入 JwtInterceptor Bean，用于接口权限拦截。
     */
    @Autowired
    public WebConfig(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    /**
     * 构建跨域配置信息
     * @return CorsConfiguration 跨域配置对象
     */
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 使用allowedOriginPatterns替代已废弃的addAllowedOrigin
        corsConfiguration.setAllowedOriginPatterns(Arrays.asList(
                "http://localhost:[*]",  // 允许任意端口
                "http://127.0.0.1:[*]",
                "http://see-toju.com:[*]" // 生产环境域名
        ));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        // 关键：暴露Content-Disposition头
        corsConfiguration.setExposedHeaders(Arrays.asList("Content-Disposition", "Authorization"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L); // 预检缓存时间
        return corsConfiguration;
    }

    /**
     * 注册全局的 CORS 过滤器，允许跨域访问
     * @return CorsFilter 跨域过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

    /**
     * 配置自定义拦截器。
     * 将 JwtInterceptor 注册到拦截链中，拦截所有接口请求（/**），
     * 并排除部分无需验证的接口路径（如登录、验证码、swagger 文档等）。
     *
     * @param registry 拦截器注册对象
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        // 登录相关接口
                        "/users/auth/login",
                        "/users/verification-codes",
                        "/users/activations",
                        "/users/token",  // 测试token接口

                        // Swagger 文档相关路径，避免拦截导致 Swagger 无法访问
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/webjars/**",
                        "/error",

                        // 兼容带 /api 前缀的文档路径
                        "/api/swagger-ui/**",
                        "/api/swagger-resources/**",
                        "/api/v3/api-docs/**",
                        "/api/webjars/**",
                        "/api/swagger-ui.html",

                        "/**/OPTIONS"
                );
    }

    /**
     * 配置静态资源与Swagger映射。
     * 将访问 /static/** 的请求映射到 classpath:/static/ 目录下。
     * 配置 Swagger UI 的静态资源映射路径，防止 Swagger 页面访问不到资源文件
     * @param registry 资源处理注册对象
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Swagger资源
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springdoc-openapi-ui/")
                .resourceChain(false);

        // 静态资源
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}