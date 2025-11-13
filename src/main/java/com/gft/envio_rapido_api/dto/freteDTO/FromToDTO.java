package com.gft.envio_rapido_api.dto.freteDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FromToDTO {

    @NotBlank(message = "O campo 'postal_code' é obrigatório")
    @Size(min = 8, max = 8, message = "O CEP deve ter exatamente 8 dígitos")
    @Pattern(regexp = "\\d{8}", message = "O CEP deve conter apenas números (sem traço ou espaços)")
    @JsonProperty("postal_code")
    private String postalCode;
}