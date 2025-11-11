package com.gft.envio_rapido_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreteDTO {

    @JsonIgnore
    private String id;
    private double valorPac;
    private int prazoPac;
    private double valorSedex;
    private int prazoSedex;

    @JsonIgnore
    private String linkPostagem;
}
