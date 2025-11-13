package com.gft.envio_rapido_api.dto.freteDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "cepOrigem",
        "cepDestino",
        "valorPac",
        "prazoPac",
        "valorSedex",
        "prazoSedex",
        "linkPostagem"})
public class FreteRespostaDTO extends FreteDTO {
    @JsonProperty("cepOrigem")
    private String cepOrigem;

    @JsonProperty("cepDestino")
    private String cepDestino;

    @JsonProperty("linkPostagem")
    private String linkPostagem;
}
