package com.gft.envio_rapido_api.dto.envioDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvioDTO {
    private String id;

    @JsonProperty("nomeRemetente")
    @NotBlank(message = "O campo 'nomeRemetente' é obrigatório.")
    @Size(min = 3, max = 50, message = "O nome do remetente deve ter entre 3 e 50 caracteres.")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$", message = "O nome do remetente deve conter apenas letras e espaços.")
    private String nomeRemetente;

    @JsonProperty("cepOrigem")
    @NotBlank(message = "O campo 'cepOrigem' é obrigatório.")
    @Pattern(regexp = "^\\d{8}$", message = "O CEP de origem deve estar no formato 00000000 (apenas números).")
    private String cepOrigem;

    @JsonProperty("cepDestino")
    @NotBlank(message = "O campo 'cepDestino' é obrigatório.")
    @Pattern(regexp = "^\\d{8}$", message = "O CEP de destino deve estar no formato 00000000 (apenas números).")
    private String cepDestino;
}
