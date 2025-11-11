package com.gft.envio_rapido_api.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Frete {

    @Id
    private String id;
    private double valorPac;
    private int prazoPac;
    private double valorSedex;
    private int prazoSedex;
    private String linkPostagem;
}
