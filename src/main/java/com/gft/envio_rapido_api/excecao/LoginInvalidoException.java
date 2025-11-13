package com.gft.envio_rapido_api.excecao;

public class LoginInvalidoException extends RegraNegocioException {
    public LoginInvalidoException(String mensagem) {
        super(mensagem);
    }
}
