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
public class Envio {

    @Id
    private String id;
    private String nomeRemetente;
    private String cepOrigem;
    private String cepDestino;
    private Caixa caixa;
    private Frete frete;
    private String mensagem;
}
