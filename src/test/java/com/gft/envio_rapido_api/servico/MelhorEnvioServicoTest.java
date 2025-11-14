package com.gft.envio_rapido_api.servico;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.envio_rapido_api.dto.freteDTO.FreteRequisicaoDTO;
import com.gft.envio_rapido_api.dto.freteDTO.FreteRespostaDTO;
import com.gft.envio_rapido_api.dto.freteDTO.FromToDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MelhorEnvioServicoTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private MelhorEnvioServico servico;

    private FreteRequisicaoDTO requisicao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(servico, "baseUrl", "http://fake-url");

        requisicao = new FreteRequisicaoDTO();
        FromToDTO from = new FromToDTO();
        from.setPostalCode("11111000");
        FromToDTO to = new FromToDTO();
        to.setPostalCode("22222000");
        requisicao.setFrom(from);
        requisicao.setTo(to);
    }

    @Test
    void deveCalcularFreteComSucesso() throws Exception {
        String json = "["
                + "{ \"custom_price\": 19.99, \"delivery_time\": 5, \"company\": {\"picture\": \"link.png\"} },"
                + "{ \"custom_price\": 29.99, \"delivery_time\": 2, \"company\": {\"picture\": \"link2.png\"} }"
                + "]";

        ResponseEntity<String> resposta = new ResponseEntity<>(json, HttpStatus.OK);
        when(restTemplate.exchange(eq("http://fake-url"), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenReturn(resposta);

        List<Map<String, Object>> lista = new ArrayList<>();
        Map<String, Object> pac = new HashMap<>();
        pac.put("custom_price", 19.99);
        pac.put("delivery_time", 5);
        Map<String, Object> empresa = new HashMap<>();
        empresa.put("picture", "link.png");
        pac.put("company", empresa);

        Map<String, Object> sedex = new HashMap<>();
        sedex.put("custom_price", 29.99);
        sedex.put("delivery_time", 2);

        lista.add(pac);
        lista.add(sedex);

        when(mapper.readValue(eq(json), any(TypeReference.class))).thenReturn(lista);

        FreteRespostaDTO result = servico.calcularFreteMelhorEnvio(requisicao);

        assertNotNull(result);
        assertEquals("11111000", result.getCepOrigem());
        assertEquals("22222000", result.getCepDestino());
        assertEquals(19.99, result.getValorPac());
        assertEquals(5, result.getPrazoPac());
        assertEquals(29.99, result.getValorSedex());
        assertEquals(2, result.getPrazoSedex());
        assertEquals("link.png", result.getLinkPostagem());
    }

    @Test
    void deveLancarErroQuandoListaForMenorQueDois() throws Exception {
        String json = "[]";
        ResponseEntity<String> resposta = new ResponseEntity<>(json, HttpStatus.OK);

        when(restTemplate.exchange(anyString(), any(), any(), eq(String.class)))
                .thenReturn(resposta);

        when(mapper.readValue(eq(json), any(TypeReference.class)))
                .thenReturn(Collections.emptyList());

        assertThrows(RuntimeException.class,
                () -> servico.calcularFreteMelhorEnvio(requisicao));
    }

    @Test
    void deveLancarErroQuandoJsonInvalido() throws Exception {
        String json = "INVALID";
        ResponseEntity<String> resposta = new ResponseEntity<>(json, HttpStatus.OK);

        when(restTemplate.exchange(anyString(), any(), any(), eq(String.class)))
                .thenReturn(resposta);

        when(mapper.readValue(eq(json), any(TypeReference.class)))
                .thenThrow(new RuntimeException("erro json"));

        assertThrows(RuntimeException.class,
                () -> servico.calcularFreteMelhorEnvio(requisicao));
    }

    @Test
    void deveLancarErroQuandoRespostaDaApiForInvalida() {
        ResponseEntity<String> resposta = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        when(restTemplate.exchange(anyString(), any(), any(), eq(String.class)))
                .thenReturn(resposta);

        assertThrows(RuntimeException.class,
                () -> servico.calcularFreteMelhorEnvio(requisicao));
    }
}
