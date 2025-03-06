package com.joaod.redis.controller;

import com.joaod.redis.dto.CepResponse;
import com.joaod.redis.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/cep")
public class CepController {
    @Autowired
    private ViaCepService viaCepService;

    @GetMapping("{cep}")
    private Mono<CepResponse> findCep(@PathVariable String cep) {
        return viaCepService.findByCep(cep);
    }
}
