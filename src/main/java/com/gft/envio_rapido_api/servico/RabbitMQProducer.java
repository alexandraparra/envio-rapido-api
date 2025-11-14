package com.gft.envio_rapido_api.servico;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.gft.envio_rapido_api.configuracao.RabbitMQConfig.FILA_CALCULO_FRETE;

@Service
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void enviarConfirmacao(Object mensagem) {
        try {
            String msgJson = objectMapper.writeValueAsString(mensagem);
            rabbitTemplate.convertAndSend(FILA_CALCULO_FRETE, msgJson);
            System.out.println("Mensagem enviada para RabbitMQ: " + msgJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao serializar mensagem", e);
        }
    }
}
