package com.gft.envio_rapido_api.testconfig;

import com.gft.envio_rapido_api.configuracao.seguridade.ServicoToken;
import com.gft.envio_rapido_api.repositorio.UsuarioRepositorio;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class EnvioControllerTestConfig {

    @Bean
    public ServicoToken servicoToken() {
        return Mockito.mock(ServicoToken.class);
    }

    @Bean
    public UsuarioRepositorio usuarioRepositorio() {
        return Mockito.mock(UsuarioRepositorio.class);
    }
}
