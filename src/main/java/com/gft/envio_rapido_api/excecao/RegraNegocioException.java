package com.gft.envio_rapido_api.excecao;

public abstract class RegraNegocioException extends RuntimeException {
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
