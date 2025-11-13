package com.gft.envio_rapido_api.dto.freteDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gft.envio_rapido_api.dto.CaixaDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreteRequisicaoDTO {
    @NotNull(message = "O campo 'from' é obrigatório")
    @Valid
    private FromToDTO from;

    @NotNull(message = "O campo 'to' é obrigatório")
    @Valid
    private FromToDTO to;

    @NotNull(message = "O campo 'package' é obrigatório")
    @Valid
    @JsonProperty("package")
    private CaixaDTO caixa;
}