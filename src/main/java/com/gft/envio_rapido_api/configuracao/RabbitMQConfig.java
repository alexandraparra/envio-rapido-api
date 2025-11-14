package com.gft.envio_rapido_api.configuracao;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String FILA_CALCULO_FRETE = "fila_calculo_frete";

    @Bean
    public Queue filaCalculoFrete() {
        return new Queue(FILA_CALCULO_FRETE, true);
    }
}

