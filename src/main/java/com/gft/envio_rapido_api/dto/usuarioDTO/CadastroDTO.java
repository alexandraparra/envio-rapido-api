package com.gft.envio_rapido_api.dto.usuarioDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CadastroDTO(
        @NotBlank(message = "O login não pode estar vazio")
        @Size(min = 3, max = 50, message = "O login deve ter entre 3 e 50 caracteres")
        @Pattern(regexp = "^[A-Za-zÀ-ÿ0-9_ ]+$", message = "O login só pode conter letras, números, espaços e underline")
        String login,

        @NotBlank(message = "A senha não pode estar vazia")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        String password,

        @NotBlank(message = "O papel é obrigatório")
        @Pattern(regexp = "ADMIN|USER",
                message = "O papel deve ser ADMIN ou USER")
        String role
) { }
