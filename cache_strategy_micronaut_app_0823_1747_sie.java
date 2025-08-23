// 代码生成时间: 2025-08-23 17:47:28
package com.example.demo;

import io.micronaut.cache.annotation.Cacheable;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import io.micronaut.cache.CacheConfiguration;import io.micronaut.cache.RedisCacheConfiguration;
import io.micronaut.cache.annotation.CacheConfig;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Primary;
import io.micronaut.cache.RedisCacheManager;
import io.micronaut.core.util.StringUtils;
import javax.inject.Singleton;
import javax.inject.Inject;
import java.util.Optional;

// 定义一个服务类来演示缓存策略
@Singleton
public class CacheService {

    // 定义缓存配置
    @CacheConfig
    private CacheConfiguration cacheConfiguration;

    // 定义Redis缓存配置
    @CacheConfig
    private RedisCacheConfiguration redisCacheConfiguration;

    // 定义缓存管理器
    private final RedisCacheManager cacheManager;

    // 构造函数注入缓存管理器
    @Inject
    public CacheService(RedisCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    // 演示缓存方法，使用注解@Cacheable实现缓存
    @Cacheable(cacheNames = "testCache", unless = "#result == null")
    public String getCachedData(String key) {
        // 模拟复杂计算过程
        return "Data for key: " + key;
    }

}

// 配置类，用于配置缓存
@Factory
@Requires(beans = RedisCacheConfiguration.class)
public class CacheConfigurations {

    // 定义缓存配置
    @Bean
    @Primary
    RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration.builder()
                .simpleValueSerialization()
                .build();
    }

    // 定义缓存管理器
    @Bean
    public RedisCacheManager cacheManager(CacheConfiguration cacheConfiguration, RedisCacheConfiguration redisCacheConfiguration) {
        return RedisCacheManager.builder(cacheConfiguration, redisCacheConfiguration)
                .build();
    }

}
