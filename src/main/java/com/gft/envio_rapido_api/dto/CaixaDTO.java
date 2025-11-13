package com.gft.envio_rapido_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaixaDTO {

    @NotNull(message = "A largura é obrigatória.")
    @DecimalMin(value = "10.0", message = "A largura mínima é de 10 cm.")
    @DecimalMax(value = "150.0", message = "A largura máxima é de 150 cm.")
    @Digits(integer = 5, fraction = 1, message = "A largura deve ter no máximo uma casa decimal.")
    @JsonProperty("larguraCaixa")
    private Double larguraCaixa;

    @NotNull(message = "A altura é obrigatória.")
    @DecimalMin(value = "2.0", message = "A altura mínima é de 2 cm.")
    @DecimalMax(value = "150.0", message = "A altura máxima é de 150 cm.")
    @Digits(integer = 5, fraction = 1, message = "A altura deve ter no máximo uma casa decimal.")
    @JsonProperty("alturaCaixa")
    private Double alturaCaixa;

    @NotNull(message = "O comprimento é obrigatório.")
    @DecimalMin(value = "13.0", message = "O comprimento mínimo é de 13 cm.")
    @DecimalMax(value = "200.0", message = "O comprimento máximo é de 200 cm.")
    @Digits(integer = 5, fraction = 1, message = "O comprimento deve ter no máximo uma casa decimal.")
    @JsonProperty("comprimentoCaixa")
    private Double comprimentoCaixa;

    @NotNull(message = "O peso é obrigatório.")
    @DecimalMin(value = "0.3", message = "O peso mínimo é de 0,3 kg.")
    @DecimalMax(value = "30.0", message = "O peso máximo é de 30 kg.")
    @Digits(integer = 3, fraction = 2, message = "O peso deve ter no máximo duas casas decimais.")
    @JsonProperty("pesoCaixa")
    private Double pesoCaixa;
}
