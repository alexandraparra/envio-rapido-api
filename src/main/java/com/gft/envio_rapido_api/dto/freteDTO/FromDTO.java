package com.gft.envio_rapido_api.dto.freteDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FromDTO {

    @JsonProperty("postal_code")
    private String postalCode;
}
