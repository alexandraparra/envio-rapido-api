package com.gft.envio_rapido_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreteDTO {

    private String id;
    private double valorPac;
    private int prazoPac;
    private double valorSedex;
    private int prazoSedex;
    private String linkPostagem;
}
