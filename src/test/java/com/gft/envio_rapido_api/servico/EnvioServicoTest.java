package com.gft.envio_rapido_api.servico;

import com.gft.envio_rapido_api.dominio.*;
import com.gft.envio_rapido_api.dto.EnderecoDTO;
import com.gft.envio_rapido_api.dto.envioDTO.EnvioRequisicaoDTO;
import com.gft.envio_rapido_api.dto.envioDTO.EnvioRespostaDTO;
import com.gft.envio_rapido_api.dto.freteDTO.FreteRespostaDTO;
import com.gft.envio_rapido_api.excecao.CepOrigemDestinoIgualException;
import com.gft.envio_rapido_api.excecao.EnderecoInvalidoException;
import com.gft.envio_rapido_api.mapper.EnvioMapper;
import com.gft.envio_rapido_api.repositorio.EnvioRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnvioServicoTest {

    @Mock
    private EnvioRepositorio repositorio;

    @Mock
    private EnvioMapper mapper;

    @Mock
    private UsuarioServico usuarioServico;

    @Mock
    private EnderecoServico enderecoServico;

    @Mock
    private CaixaServico caixaServico;

    @Mock
    private FreteServico freteServico;

    @InjectMocks
    private EnvioServico servico;

    private EnvioRequisicaoDTO dto;
    private Usuario usuario;
    private Endereco enderecoOrigem;
    private Endereco enderecoDestino;
    private Caixa caixa;
    private FreteRespostaDTO freteResposta;
    private Frete freteSalvo;
    private Envio envio;
    private EnvioRespostaDTO resposta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        dto = new EnvioRequisicaoDTO();
        dto.setCepOrigem("11111000");
        dto.setCepDestino("22222000");
        dto.setEndereco("Rua A, Casa 1");
        dto.setCaixa(mock());

        usuario = new Usuario();
        enderecoOrigem = new Endereco();
        enderecoDestino = new Endereco();
        caixa = new Caixa();
        freteResposta = new FreteRespostaDTO();
        freteSalvo = new Frete();
        envio = new Envio();
        resposta = new EnvioRespostaDTO();
    }

    @Test
    void deveSalvarEnvioComSucesso() {
        when(usuarioServico.resolverRemetente(dto)).thenReturn(usuario);
        when(enderecoServico.salvarEndereco(any(EnderecoDTO.class))).thenReturn(enderecoOrigem).thenReturn(enderecoDestino);
        when(caixaServico.salvarCaixa(dto.getCaixa())).thenReturn(caixa);
        when(freteServico.calcularFrete(dto, dto.getCaixa())).thenReturn(freteResposta);
        when(freteServico.salvarFrete(freteResposta)).thenReturn(freteSalvo);
        when(mapper.toEntity(dto)).thenReturn(envio);
        when(repositorio.save(envio)).thenReturn(envio);
        when(mapper.toRespostaDTO(envio)).thenReturn(resposta);

        EnvioRespostaDTO resultado = servico.salvarEnvio(dto);

        assertNotNull(resultado);
        verify(usuarioServico).resolverRemetente(dto);
        verify(enderecoServico, times(2)).salvarEndereco(any());
        verify(caixaServico).salvarCaixa(dto.getCaixa());
        verify(freteServico).calcularFrete(dto, dto.getCaixa());
        verify(freteServico).salvarFrete(freteResposta);
        verify(repositorio).save(envio);
        verify(mapper).toRespostaDTO(envio);
    }

    @Test
    void deveLancarExcecaoQuandoCepOrigemIgualCepDestino() {
        dto.setCepDestino("11111000");

        assertThrows(CepOrigemDestinoIgualException.class,
                () -> servico.salvarEnvio(dto));
    }

    @Test
    void deveLancarExcecaoQuandoEnderecoOrigemForInvalido() {
        dto.setEndereco(" ");

        assertThrows(EnderecoInvalidoException.class,
                () -> servico.salvarEnvio(dto));
    }

    @Test
    void deveLancarExcecaoQuandoRepositorioFalhar() {
        when(usuarioServico.resolverRemetente(dto)).thenReturn(usuario);
        when(enderecoServico.salvarEndereco(any())).thenReturn(enderecoOrigem).thenReturn(enderecoDestino);
        when(caixaServico.salvarCaixa(any())).thenReturn(caixa);
        when(freteServico.calcularFrete(any(), any())).thenReturn(freteResposta);
        when(freteServico.salvarFrete(any())).thenReturn(freteSalvo);
        when(mapper.toEntity(dto)).thenReturn(envio);
        when(repositorio.save(envio)).thenThrow(new DataIntegrityViolationException("Erro"));

        assertThrows(RuntimeException.class, () -> servico.salvarEnvio(dto));
    }

    @Test
    void deveBuscarEnvioPorIdComSucesso() {
        when(repositorio.findById("1")).thenReturn(Optional.of(envio));
        when(mapper.toRespostaDTO(envio)).thenReturn(resposta);

        EnvioRespostaDTO result = servico.buscarEnvioPorId("1");

        assertNotNull(result);
        verify(repositorio).findById("1");
        verify(mapper).toRespostaDTO(envio);
    }

    @Test
    void deveLancarExcecaoQuandoEnvioNaoForEncontrado() {
        when(repositorio.findById("99")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> servico.buscarEnvioPorId("99"));
    }
}
