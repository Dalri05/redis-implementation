package com.joaod.redis.service;

import com.joaod.redis.Repository.RedisCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RedisCacheService{
    @Autowired
    private RedisCacheRepository redisCacheRepository;

    public Mono<String> save(String key, String value) {
        try {
            return redisCacheRepository.save(key, value)
                    .flatMap( saved -> {
                        if (saved) {
                            return Mono.just(value);
                        }
                        return Mono.empty();
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Mono.empty();
    }

    public Mono<String> get(String key) {
        try {
            return redisCacheRepository.get(key)
                    .doOnNext( response -> {
                        System.out.println(response);
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Mono.empty();
    }

    public Mono<Boolean> exists(String key) {
        try {
            return redisCacheRepository.existsByKey(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Mono.just(false);
    }
}
