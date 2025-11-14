package com.gft.envio_rapido_api.servico;

import com.gft.envio_rapido_api.dominio.Endereco;
import com.gft.envio_rapido_api.dto.EnderecoDTO;
import com.gft.envio_rapido_api.mapper.EnderecoMapper;
import com.gft.envio_rapido_api.repositorio.EnderecoRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnderecoServicoTest {

    @Mock
    private EnderecoRepositorio repositorio;

    @Mock
    private EnderecoMapper mapper;

    @Mock
    private ViaCepService viaCepService;

    @InjectMocks
    private EnderecoServico servico;

    private EnderecoDTO entradaDTO;
    private EnderecoDTO viaCepDTO;
    private Endereco enderecoSalvo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        entradaDTO = new EnderecoDTO();
        entradaDTO.setCep("01001000");
        entradaDTO.setLogradouro(null);
        entradaDTO.setComplemento(null);

        viaCepDTO = new EnderecoDTO();
        viaCepDTO.setCep("01001000");
        viaCepDTO.setLogradouro("Praça da Sé");
        viaCepDTO.setComplemento("Lado ímpar");
        viaCepDTO.setBairro("Sé");
        viaCepDTO.setLocalidade("São Paulo");
        viaCepDTO.setUf("SP");

        enderecoSalvo = new Endereco();
        enderecoSalvo.setId("1");
        enderecoSalvo.setLogradouro("Praça da Sé");
        enderecoSalvo.setComplemento("Lado ímpar");
        enderecoSalvo.setCep("01001000");
        enderecoSalvo.setBairro("Sé");
        enderecoSalvo.setLocalidade("São Paulo");
        enderecoSalvo.setUf("SP");
    }

    @Test
    void deveSalvarEnderecoPreenchendoDadosDoViaCepQuandoFaltarem() {
        when(viaCepService.buscarEnderecoPorCep("01001000")).thenReturn(viaCepDTO);
        when(mapper.toEntidade(any())).thenReturn(enderecoSalvo);
        when(repositorio.save(any())).thenReturn(enderecoSalvo);

        Endereco resultado = servico.salvarEndereco(entradaDTO);

        assertNotNull(resultado);
        assertEquals("Praça da Sé", resultado.getLogradouro());
        assertEquals("Lado ímpar", resultado.getComplemento());
        assertEquals("Sé", resultado.getBairro());
        assertEquals("São Paulo", resultado.getLocalidade());
        assertEquals("SP", resultado.getUf());

        verify(viaCepService, times(1)).buscarEnderecoPorCep("01001000");
        verify(mapper, times(1)).toEntidade(any(EnderecoDTO.class));
        verify(repositorio, times(1)).save(any(Endereco.class));
    }

    @Test
    void deveManterLogradouroQuandoJaVemPreenchidoNoDTO() {
        entradaDTO.setLogradouro("Rua Customizada");

        when(viaCepService.buscarEnderecoPorCep("01001000")).thenReturn(viaCepDTO);
        when(mapper.toEntidade(any())).thenReturn(enderecoSalvo);
        when(repositorio.save(any())).thenReturn(enderecoSalvo);

        Endereco resultado = servico.salvarEndereco(entradaDTO);

        assertEquals("Rua Customizada", entradaDTO.getLogradouro());
        verify(viaCepService).buscarEnderecoPorCep("01001000");
        verify(repositorio).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoRepositorioFalhar() {
        when(viaCepService.buscarEnderecoPorCep("01001000")).thenReturn(viaCepDTO);
        when(mapper.toEntidade(any())).thenReturn(enderecoSalvo);
        when(repositorio.save(any())).thenThrow(new DataIntegrityViolationException("Erro"));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> servico.salvarEndereco(entradaDTO));

        assertTrue(ex.getMessage().contains("Erro ao salvar endereço"));

        verify(viaCepService).buscarEnderecoPorCep("01001000");
        verify(repositorio).save(any());
    }
}
