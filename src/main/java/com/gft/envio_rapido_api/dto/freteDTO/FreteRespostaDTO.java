package com.gft.envio_rapido_api.dto.freteDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreteRespostaDTO {

    private String cepOrigem;
    private String cepDestino;
    private double valorPac;
    private int prazoPac;
    private double valorSedex;
    private int prazoSedex;

    @JsonIgnore
    private String linkPostagem;
}
