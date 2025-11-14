package com.gft.envio_rapido_api.excecao;

public class CepInvalidoException extends RegraNegocioException {
    public CepInvalidoException(String mensagem) {
        super(mensagem);
    }
}
