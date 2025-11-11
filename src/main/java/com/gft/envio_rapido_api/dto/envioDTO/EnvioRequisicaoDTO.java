package com.gft.envio_rapido_api.dto.envioDTO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.gft.envio_rapido_api.dto.CaixaDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "id",
        "nomeRemetente",
        "endereco",
        "cepOrigem",
        "cepDestino",
        "larguraCaixa",
        "alturaCaixa",
        "comprimentoCaixa",
        "pesoCaixa"
})
public class EnvioRequisicaoDTO extends EnvioDTO {
    private String endereco;

    @Valid
    private CaixaDTO caixa;
}
