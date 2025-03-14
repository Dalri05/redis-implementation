package com.joaod.redis.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ViaCepClient {
    @Autowired
    private WebClient webClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${app-config.client.viacep}")
    private String viaCeoUri;

    public Mono<String> findByCep(String cep) {
        return webClient
                .get()
                .uri(buildUri(cep))
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(throwable -> {
                    System.out.println(throwable.getMessage());
                    return Mono.empty();
                })
                .doOnNext(response -> {
                    System.out.println("Retornando dados do viaCep");
                });
    }

    public String buildUri(String cep) {
        return String.format(viaCeoUri, cep);
    }
}
