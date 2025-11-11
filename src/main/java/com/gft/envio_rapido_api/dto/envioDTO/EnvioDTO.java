package com.gft.envio_rapido_api.dto.envioDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvioDTO {
    private String id;
    private String nomeRemetente;
    private String cepOrigem;
    private String cepDestino;
}
