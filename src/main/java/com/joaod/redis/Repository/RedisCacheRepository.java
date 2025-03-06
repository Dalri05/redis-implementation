package com.joaod.redis.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Repository
public class RedisCacheRepository {
    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    @Value("${app-config.cache.ttl}")
    private int cacheTtl;

    public Mono<Boolean> save(String key, String value) {
        return reactiveRedisTemplate.opsForValue().set(key, value)
                .then(reactiveRedisTemplate.expire(key, Duration.ofSeconds(cacheTtl)))
                .onErrorResume(throwable -> Mono.just(false));
    }

    public Mono<Boolean> existsByKey(String key) {
        return reactiveRedisTemplate.hasKey(key)
                .onErrorResume(throwable -> Mono.just(false));
    }

    public Mono<String> get(String key) {
        return reactiveRedisTemplate.opsForValue().get(key)
                .onErrorResume(throwable -> Mono.empty());
    }
}
