package com.gft.envio_rapido_api.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.envio_rapido_api.dto.CaixaDTO;
import com.gft.envio_rapido_api.dto.envioDTO.EnvioRequisicaoDTO;
import com.gft.envio_rapido_api.dto.envioDTO.EnvioRespostaDTO;
import com.gft.envio_rapido_api.servico.EnvioServico;
import com.gft.envio_rapido_api.testconfig.EnvioControllerTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EnvioControler.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(EnvioControllerTestConfig.class)
class EnvioControlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private EnvioServico servico;

    @Test
    void deveSalvarEnvioComSucesso() throws Exception {
        CaixaDTO caixa = new CaixaDTO(10.0, 20.0, 30.0, 5.0);

        EnvioRequisicaoDTO requisicao = new EnvioRequisicaoDTO();
        requisicao.setNomeRemetente("Maria");
        requisicao.setCepOrigem("12345678");
        requisicao.setCepDestino("87654321");
        requisicao.setEndereco("Rua Central 123");
        requisicao.setCaixa(caixa);

        EnvioRespostaDTO resposta = new EnvioRespostaDTO();
        resposta.setMensagem("ok");

        when(servico.salvarEnvio(any())).thenReturn(resposta);

        mockMvc.perform(post("/envios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requisicao)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mensagem").value("ok"));
    }

    @Test
    void deveBuscarEnvioPorIdComSucesso() throws Exception {
        EnvioRespostaDTO resposta = new EnvioRespostaDTO();
        resposta.setMensagem("ok");

        when(servico.buscarEnvioPorId("123")).thenReturn(resposta);

        mockMvc.perform(get("/envios/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value("ok"));
    }

    @Test
    void deveRetornarNotFoundQuandoEnvioNaoExiste() throws Exception {
        when(servico.buscarEnvioPorId("999")).thenThrow(new RuntimeException("não encontrado"));

        mockMvc.perform(get("/envios/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornarBadRequestQuandoJsonInvalido() throws Exception {
        String jsonInvalido = "{ \"cepOrigem\": 123 }";

        mockMvc.perform(post("/envios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido))
                .andExpect(status().isBadRequest());
    }
}
