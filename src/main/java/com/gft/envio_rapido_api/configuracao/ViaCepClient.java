package com.gft.envio_rapido_api.configuracao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ViaCepClient {
    @Bean("viaCepRestTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}