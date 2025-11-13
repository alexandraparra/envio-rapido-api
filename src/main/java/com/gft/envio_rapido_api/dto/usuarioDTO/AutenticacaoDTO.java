package com.gft.envio_rapido_api.dto.usuarioDTO;

import jakarta.validation.constraints.NotBlank;

public record AutenticacaoDTO(
        @NotBlank(message = "O login não pode estar vazio")
        String login,

        @NotBlank(message = "A senha não pode estar vazia")
        String password) { }
