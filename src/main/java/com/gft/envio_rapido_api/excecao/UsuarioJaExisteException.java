package com.gft.envio_rapido_api.excecao;

public class UsuarioJaExisteException extends RegraNegocioException {
    public UsuarioJaExisteException(String mensagem) {
        super(mensagem);
    }
}
