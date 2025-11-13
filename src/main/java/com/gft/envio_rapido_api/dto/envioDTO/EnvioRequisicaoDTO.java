package com.gft.envio_rapido_api.dto.envioDTO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.gft.envio_rapido_api.dto.CaixaDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "id",
        "nomeRemetente",
        "endereco",
        "cepOrigem",
        "cepDestino",
        "larguraCaixa",
        "alturaCaixa",
        "comprimentoCaixa",
        "pesoCaixa"
})
public class EnvioRequisicaoDTO extends EnvioDTO {

    @NotBlank(message = "O campo 'endereco' é obrigatório.")
    @Size(min = 5, max = 50, message = "O endereço deve ter entre 5 e 50 caracteres.")
    private String endereco;

    @Valid
    @NotNull(message = "Os dados da 'caixa' são obrigatórios.")
    @JsonUnwrapped
    private CaixaDTO caixa;
}
