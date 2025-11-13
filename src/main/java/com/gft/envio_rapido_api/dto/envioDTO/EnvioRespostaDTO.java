package com.gft.envio_rapido_api.dto.envioDTO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.gft.envio_rapido_api.dto.freteDTO.FreteDTO;
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
        "cepOrigem",
        "cepDestino",
        "frete",
        "mensagem"})
public class EnvioRespostaDTO extends EnvioDTO {
    @Valid
    private FreteDTO frete;

    private String mensagem;
}