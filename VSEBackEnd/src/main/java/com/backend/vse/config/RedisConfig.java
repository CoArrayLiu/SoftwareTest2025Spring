package com.backend.vse.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * Redis 配置类。
 * 用于自定义 RedisTemplate 的序列化方式，并启用 Spring 缓存注解支持。
 */
@Configuration
@EnableCaching // 启用缓存功能
@ConfigurationProperties(prefix = "redis.cache")
public class RedisConfig {
    private int ttl; // 缓存过期时间（min）
    /**
     * 自定义 RedisTemplate 配置。
     * 设置 Key 和 Value 的序列化方式为 String 类型，避免默认使用 JDK 序列化导致乱码。
     *
     * @return 配置好的 RedisTemplate 对象
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // Key 采用字符串序列化
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // Value 采用 JSON 序列化
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }
}