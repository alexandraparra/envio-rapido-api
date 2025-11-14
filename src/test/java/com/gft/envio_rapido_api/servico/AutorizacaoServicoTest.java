package com.gft.envio_rapido_api.servico;

import com.gft.envio_rapido_api.dominio.Usuario;
import com.gft.envio_rapido_api.repositorio.UsuarioRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutorizacaoServicoTest {

    @Mock
    private UsuarioRepositorio repositorio;

    @InjectMocks
    private AutorizacaoServico servico;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarUsuarioQuandoEncontrado() {
        Usuario usuario = new Usuario();
        when(repositorio.findByLogin("alex")).thenReturn(usuario);

        var result = servico.loadUserByUsername("alex");

        assertNotNull(result);
        verify(repositorio).findByLogin("alex");
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        when(repositorio.findByLogin("naoExiste")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                () -> servico.loadUserByUsername("naoExiste"));
    }
}
