package com.gft.envio_rapido_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private String id;
    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
    private String nome;

    @Pattern(
            regexp = "^[a-zA-Z0-9._]{3,20}$",
            message = "O login deve conter apenas letras, números, ponto ou underline, e ter entre 3 e 20 caracteres."
    )
    private String login;

    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    private String senha;

    @Pattern(
            regexp = "^(ADMIN|USER)$",
            message = "O papel deve ser ADMIN ou USER."
    )
    private String papel;
}
