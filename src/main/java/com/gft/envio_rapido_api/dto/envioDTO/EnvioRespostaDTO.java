package com.gft.envio_rapido_api.dto.envioDTO;

import com.gft.envio_rapido_api.dto.FreteDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvioRespostaDTO extends EnvioDTO {
    private FreteDTO frete;
    private String mensagem;
}
