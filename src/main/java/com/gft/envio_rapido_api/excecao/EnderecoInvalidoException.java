package com.gft.envio_rapido_api.excecao;

public class EnderecoInvalidoException extends RegraNegocioException {
    public EnderecoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
