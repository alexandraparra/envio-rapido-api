package com.gft.envio_rapido_api.servico;

import com.gft.envio_rapido_api.dto.EnderecoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ViaCepServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ViaCepService servico;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(servico, "baseUrl", "https://viacep.com.br/ws/{cep}/json/");
    }

    @Test
    void deveBuscarEnderecoComSucesso() {
        EnderecoDTO endereco = new EnderecoDTO();
        endereco.setCep("01001000");

        when(restTemplate.getForObject("https://viacep.com.br/ws/{cep}/json/", EnderecoDTO.class, "01001000"))
                .thenReturn(endereco);

        EnderecoDTO resultado = servico.buscarEnderecoPorCep("01001000");

        assertNotNull(resultado);
        assertEquals("01001000", resultado.getCep());
        verify(restTemplate).getForObject("https://viacep.com.br/ws/{cep}/json/", EnderecoDTO.class, "01001000");
    }

    @Test
    void deveRetornarNullQuandoViaCepRetornarNull() {
        when(restTemplate.getForObject(anyString(), eq(EnderecoDTO.class), anyString()))
                .thenReturn(null);

        EnderecoDTO resultado = servico.buscarEnderecoPorCep("00000000");

        assertNull(resultado);
    }
}
