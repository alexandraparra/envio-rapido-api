package com.gft.envio_rapido_api.configuracao;

public record ConfirmacaoFreteDTO(
        String idEnvio,
        Double valorFrete,
        String status
) {}
