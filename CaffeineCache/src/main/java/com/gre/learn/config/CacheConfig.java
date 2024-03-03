package com.gre.learn.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;

@Configuration
public class CacheConfig {

	@Bean
    CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
        		.initialCapacity(50)
        		.expireAfterWrite(3, TimeUnit.MINUTES)
        		.evictionListener((Object key, Object value, RemovalCause cause) ->
                	System.out.printf("Key %s was evicted (%s)%n", key, cause))
        		.removalListener((Object key, Object value, RemovalCause cause) ->
                	System.out.printf("Key %s was removed (%s)%n", key, cause)).recordStats());
        return cacheManager;
    }
}
