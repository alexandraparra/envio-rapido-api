package com.gft.envio_rapido_api.servico;

import com.gft.envio_rapido_api.dominio.Frete;
import com.gft.envio_rapido_api.dto.CaixaDTO;
import com.gft.envio_rapido_api.dto.envioDTO.EnvioRequisicaoDTO;
import com.gft.envio_rapido_api.dto.freteDTO.FreteRequisicaoDTO;
import com.gft.envio_rapido_api.dto.freteDTO.FreteRespostaDTO;
import com.gft.envio_rapido_api.mapper.FreteMapper;
import com.gft.envio_rapido_api.repositorio.FreteRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class FreteServicoTest {

    @Mock
    private FreteRepositorio repositorio;

    @Mock
    private FreteMapper mapper;

    @Mock
    private MelhorEnvioServico melhorEnvioServico;

    @InjectMocks
    private FreteServico servico;

    private FreteRespostaDTO resposta;
    private Frete freteSalvo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        resposta = new FreteRespostaDTO();
        freteSalvo = new Frete();
    }

    @Test
    void deveSalvarFreteComSucesso() {
        when(mapper.toEntidade(resposta)).thenReturn(freteSalvo);
        when(repositorio.save(freteSalvo)).thenReturn(freteSalvo);

        Frete resultado = servico.salvarFrete(resposta);

        assertNotNull(resultado);
        verify(mapper).toEntidade(resposta);
        verify(repositorio).save(freteSalvo);
    }

    @Test
    void deveLancarExcecaoQuandoSalvarFreteFalhar() {
        when(mapper.toEntidade(resposta)).thenReturn(freteSalvo);
        when(repositorio.save(freteSalvo)).thenThrow(new DataIntegrityViolationException("erro"));

        assertThrows(RuntimeException.class, () -> servico.salvarFrete(resposta));
    }

    @Test
    void deveCalcularFreteCorretamente() {
        EnvioRequisicaoDTO envio = new EnvioRequisicaoDTO();
        envio.setCepOrigem("11111000");
        envio.setCepDestino("22222000");

        CaixaDTO caixa = mock(CaixaDTO.class);
        FreteRespostaDTO respostaMelhorEnvio = new FreteRespostaDTO();

        when(melhorEnvioServico.calcularFreteMelhorEnvio(any(FreteRequisicaoDTO.class)))
                .thenReturn(respostaMelhorEnvio);

        FreteRespostaDTO result = servico.calcularFrete(envio, caixa);

        assertNotNull(result);
        verify(melhorEnvioServico).calcularFreteMelhorEnvio(any(FreteRequisicaoDTO.class));
    }
}
