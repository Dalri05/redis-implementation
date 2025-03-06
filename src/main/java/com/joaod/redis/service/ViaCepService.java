package com.joaod.redis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaod.redis.client.ViaCepClient;
import com.joaod.redis.dto.CepResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static io.micrometer.common.util.StringUtils.isEmpty;

@Service
public class ViaCepService {

    @Autowired
    private ViaCepClient viaCepClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisCacheService redisCacheService;

    public Mono<CepResponse> findByCep(String cep) {
        return redisCacheService
                .exists(cep)
                .flatMap(exists -> {
                    if (exists) {
                        return redisCacheService.get(cep);
                    } else {
                        return viaCepClient
                                .findByCep(cep)
                                .flatMap(cepResponse -> redisCacheService.save(cep, cepResponse));
                    }
                }).flatMap(this::convertCepResponseToMono);

    }

    private Mono<CepResponse> convertCepResponseToMono(String response) {
        if(!isEmpty(response)) {
            try {
                return Mono.just(objectMapper.readValue(response, CepResponse.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Mono.empty();
    }
}
