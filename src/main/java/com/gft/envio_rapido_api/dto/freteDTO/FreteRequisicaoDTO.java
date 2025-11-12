package com.gft.envio_rapido_api.dto.freteDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gft.envio_rapido_api.dto.CaixaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreteRequisicaoDTO {

    private FromDTO from;
    private FromDTO to;

    @JsonProperty("package")
    private CaixaDTO pacote;
}
