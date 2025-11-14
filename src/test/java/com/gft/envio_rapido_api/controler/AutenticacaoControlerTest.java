package com.gft.envio_rapido_api.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.envio_rapido_api.dto.usuarioDTO.AutenticacaoDTO;
import com.gft.envio_rapido_api.dto.usuarioDTO.CadastroDTO;
import com.gft.envio_rapido_api.dto.usuarioDTO.LoginRespostaDTO;
import com.gft.envio_rapido_api.excecao.GlobalExceptionHandler;
import com.gft.envio_rapido_api.excecao.LoginInvalidoException;
import com.gft.envio_rapido_api.excecao.UsuarioJaExisteException;
import com.gft.envio_rapido_api.servico.AutenticacaoServico;
import com.gft.envio_rapido_api.testconfig.AutenticacaoControllerTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AutenticacaoControler.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({AutenticacaoControllerTestConfig.class, GlobalExceptionHandler.class})
class AutenticacaoControlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private AutenticacaoServico servico;

    @Test
    void deveLogarComSucesso() throws Exception {
        AutenticacaoDTO dto = new AutenticacaoDTO("alex", "123");
        LoginRespostaDTO resposta = new LoginRespostaDTO("token123");

        when(servico.autenticar(dto)).thenReturn(resposta);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token123"));
    }

    @Test
    void deveRetornarErroQuandoLoginInvalido() throws Exception {
        AutenticacaoDTO dto = new AutenticacaoDTO("alex", "123");

        when(servico.autenticar(dto))
                .thenThrow(new LoginInvalidoException("Login inválido"));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deveCadastrarComSucesso() throws Exception {
        CadastroDTO dto = new CadastroDTO("novo", "123456", "ADMIN");

        mockMvc.perform(post("/auth/cadastro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(servico).cadastrar(dto);
    }

    @Test
    void deveRetornarErroQuandoUsuarioJaExiste() throws Exception {
        CadastroDTO dto = new CadastroDTO("alex", "123456", "USER");

        doThrow(new UsuarioJaExisteException("Já existe"))
                .when(servico).cadastrar(dto);

        mockMvc.perform(post("/auth/cadastro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isConflict());
    }

    @Test
    void deveRetornarBadRequestQuandoJsonInvalido() throws Exception {
        String jsonInvalido = "{ \"login\": \"alex\" }";

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido))
                .andExpect(status().isBadRequest());
    }
}
