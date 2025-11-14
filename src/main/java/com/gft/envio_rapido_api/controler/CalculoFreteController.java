package com.gft.envio_rapido_api.controler;

import com.gft.envio_rapido_api.configuracao.ConfirmacaoFreteDTO;
import com.gft.envio_rapido_api.servico.RabbitMQProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/frete")
public class CalculoFreteController {

    private final RabbitMQProducer producer;

    public CalculoFreteController(RabbitMQProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/confirmar")
    public String confirmarEnvio(@RequestBody ConfirmacaoFreteDTO confirmacaoFreteDTO) {
        producer.enviarConfirmacao(confirmacaoFreteDTO);
        return "Confirmação enviada para RabbitMQ!";
    }
}
