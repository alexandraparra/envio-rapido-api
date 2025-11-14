package com.gft.envio_rapido_api.servico;

import com.gft.envio_rapido_api.configuracao.seguridade.ServicoToken;
import com.gft.envio_rapido_api.dominio.Usuario;
import com.gft.envio_rapido_api.dto.usuarioDTO.AutenticacaoDTO;
import com.gft.envio_rapido_api.dto.usuarioDTO.CadastroDTO;
import com.gft.envio_rapido_api.dto.usuarioDTO.LoginRespostaDTO;
import com.gft.envio_rapido_api.enums.UsuarioPapel;
import com.gft.envio_rapido_api.excecao.LoginInvalidoException;
import com.gft.envio_rapido_api.excecao.UsuarioJaExisteException;
import com.gft.envio_rapido_api.repositorio.UsuarioRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.TestingAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutenticacaoServicoTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UsuarioRepositorio repository;

    @Mock
    private ServicoToken servicoToken;

    @InjectMocks
    private AutenticacaoServico servico;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario("alex", "123", UsuarioPapel.ADMIN);
    }

    @Test
    void deveAutenticarComSucesso() {
        AutenticacaoDTO dto = new AutenticacaoDTO("alex", "123");
        TestingAuthenticationToken auth =
                new TestingAuthenticationToken(usuario, "123");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);
        when(servicoToken.gerarToken(usuario)).thenReturn("token123");

        LoginRespostaDTO resposta = servico.autenticar(dto);

        assertNotNull(resposta);
        assertEquals("token123", resposta.token());
        verify(authenticationManager).authenticate(any());
        verify(servicoToken).gerarToken(usuario);
    }

    @Test
    void deveLancarExcecaoQuandoLoginInvalido() {
        AutenticacaoDTO dto = new AutenticacaoDTO("alex", "123");
        when(authenticationManager.authenticate(any()))
                .thenThrow(new AuthenticationException("erro") {});

        assertThrows(LoginInvalidoException.class, () -> servico.autenticar(dto));
    }

    @Test
    void deveCadastrarComSucesso() {
        CadastroDTO dto = new CadastroDTO("novo", "senha123", "ADMIN");
        when(repository.findByLogin("novo")).thenReturn(null);

        servico.cadastrar(dto);

        verify(repository).save(any(Usuario.class));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioJaExiste() {
        CadastroDTO dto = new CadastroDTO("alex", "senha", "USER");
        when(repository.findByLogin("alex")).thenReturn(usuario);

        assertThrows(UsuarioJaExisteException.class, () -> servico.cadastrar(dto));
        verify(repository, never()).save(any());
    }

    @Test
    void senhaDeveSerCriptografadaNoCadastro() {
        CadastroDTO dto = new CadastroDTO("novo", "senhaPura", "USER");
        when(repository.findByLogin("novo")).thenReturn(null);

        servico.cadastrar(dto);

        verify(repository).save(argThat(u ->
                !u.getSenha().equals("senhaPura")
                        && u.getSenha().length() > 10
        ));
    }
}
