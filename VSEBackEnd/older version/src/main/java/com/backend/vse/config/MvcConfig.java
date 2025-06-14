package com.backend.vse.config;
import com.backend.vse.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * 添加jwt拦截器，并指定拦截路径
     * */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截除了登录login的所有资源
        // 除了登录请求以外的这些路径都需要进行 JWT 认证
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/account")
                .addPathPatterns("/experiment/**")
                .addPathPatterns("/menu/list")
                .addPathPatterns("/menu/student_experiment")
                .addPathPatterns("/review/score")
                .addPathPatterns("/review/change-userinfo")
                .addPathPatterns("/report/submit")
                .addPathPatterns("/user/change-avatar")
                .addPathPatterns("/change-password")
                .addPathPatterns("/addcourse")
                .addPathPatterns("/courses")
                .addPathPatterns("/coursescore")
                .addPathPatterns("/studentscore");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 所有以 /static/** 开头的请求都会映射到 classpath:/static/ 下的资源文件
        registry.addResourceHandler("/static/**") //过滤策略
                .addResourceLocations("classpath:/static/");  // 静态资源路径
    }

    /**
     * jwt拦截器
     * */
    @Bean
    // 作为 Bean 注入到 Spring 容器中
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
}