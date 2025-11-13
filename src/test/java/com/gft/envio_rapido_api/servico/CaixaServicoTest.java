package com.gft.envio_rapido_api.servico;

import com.gft.envio_rapido_api.dominio.Caixa;
import com.gft.envio_rapido_api.dto.CaixaDTO;
import com.gft.envio_rapido_api.mapper.CaixaMapper;
import com.gft.envio_rapido_api.repositorio.CaixaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CaixaServicoTest {

    @Mock
    private CaixaRepositorio repositorio;

    @Mock
    private CaixaMapper mapper;

    @InjectMocks
    private CaixaServico servico;

    private CaixaDTO caixaDTO;
    private Caixa caixa;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        caixaDTO = new CaixaDTO(10.0, 20.0, 30.0, 2.5);
        caixa = new Caixa("1", 10.0, 20.0, 30.0, 2.5);
    }

    @Test
    void deveSalvarCaixaComSucesso() {
        when(mapper.toEntidade(caixaDTO)).thenReturn(caixa);
        when(repositorio.save(any(Caixa.class))).thenReturn(caixa);

        Caixa resultado = servico.salvarCaixa(caixaDTO);

        assertNotNull(resultado);
        assertEquals("1", resultado.getId());

        verify(mapper).toEntidade(caixaDTO);
        verify(repositorio).save(caixa);
    }

    @Test
    void deveLancarExcecaoQuandoRepositorioFalha() {
        when(mapper.toEntidade(caixaDTO)).thenReturn(caixa);
        when(repositorio.save(any())).thenThrow(new RuntimeException("Falha ao salvar"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                servico.salvarCaixa(caixaDTO)
        );

        assertTrue(exception.getMessage().contains("Erro ao salvar caixa"));

        verify(mapper, times(1)).toEntidade(caixaDTO);
        verify(repositorio, times(1)).save(caixa);
    }
}
