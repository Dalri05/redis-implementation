package com.joaod.redis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CepResponse {
    public String cep;
    public String logadouro;
    public String complemento;
    public String bairro;
    public String uf;

}
