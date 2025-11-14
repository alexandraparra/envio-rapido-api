package com.gft.envio_rapido_api.servico;

import com.gft.envio_rapido_api.dominio.Usuario;
import com.gft.envio_rapido_api.dto.envioDTO.EnvioRequisicaoDTO;
import com.gft.envio_rapido_api.repositorio.UsuarioRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServicoTest {

    @Mock
    private UsuarioRepositorio repositorio;

    @InjectMocks
    private UsuarioServico servico;

    private EnvioRequisicaoDTO dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dto = new EnvioRequisicaoDTO();
        SecurityContextHolder.clearContext();
    }

    @Test
    void deveSalvarUsuarioSimplesComSucesso() {
        Usuario usuario = new Usuario();
        usuario.setNome("Maria");

        when(repositorio.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = servico.salvarUsuarioSimples("Maria");

        assertNotNull(resultado);
        assertEquals("Maria", resultado.getNome());
        verify(repositorio).save(any(Usuario.class));
    }

    @Test
    void deveLancarExcecaoQuandoSalvarUsuarioFalhar() {
        when(repositorio.save(any())).thenThrow(new DataIntegrityViolationException("erro"));

        assertThrows(RuntimeException.class, () -> servico.salvarUsuarioSimples("João"));
    }

    @Test
    void deveBuscarUsuarioPorLogin() {
        Usuario usuario = new Usuario();
        when(repositorio.findByLogin("teste")).thenReturn(usuario);

        assertEquals(usuario, servico.buscarUsuarioPorLogin("teste"));
        verify(repositorio).findByLogin("teste");
    }

    @Test
    void deveResolverRemetenteQuandoNomeRemetenteInformado() {
        dto.setNomeRemetente(" Ana ");

        Usuario usuario = new Usuario();
        when(repositorio.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = servico.resolverRemetente(dto);

        assertNotNull(resultado);
        verify(repositorio).save(any(Usuario.class));
    }

    @Test
    void deveResolverRemetenteComUsuarioAutenticado() {
        Usuario usuario = new Usuario();
        when(repositorio.findByLogin("alex")).thenReturn(usuario);

        TestingAuthenticationToken auth =
                new TestingAuthenticationToken("alex", "123", "ROLE_USER");
        auth.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(auth);

        Usuario resultado = servico.resolverRemetente(dto);

        assertNotNull(resultado);
        verify(repositorio).findByLogin("alex");
    }

    @Test
    void deveLancarExcecaoQuandoNaoHouverAutenticacao() {
        dto.setNomeRemetente(null);

        SecurityContextHolder.clearContext();

        assertThrows(RuntimeException.class, () -> servico.resolverRemetente(dto));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioForAnonymous() {
        dto.setNomeRemetente(null);

        TestingAuthenticationToken auth =
                new TestingAuthenticationToken("anonymousUser", "123");
        auth.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(auth);

        assertThrows(RuntimeException.class, () -> servico.resolverRemetente(dto));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioAutenticadoNaoForEncontrado() {
        dto.setNomeRemetente(null);

        TestingAuthenticationToken auth =
                new TestingAuthenticationToken("alex", "123");
        auth.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(auth);

        when(repositorio.findByLogin("alex")).thenReturn(null);

        assertThrows(RuntimeException.class, () -> servico.resolverRemetente(dto));
    }
}
