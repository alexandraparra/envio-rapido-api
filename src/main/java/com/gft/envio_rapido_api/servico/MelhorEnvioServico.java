package com.gft.envio_rapido_api.servico;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.envio_rapido_api.dto.freteDTO.FreteRequisicaoDTO;
import com.gft.envio_rapido_api.dto.freteDTO.FreteRespostaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class MelhorEnvioServico {
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    @Value("${melhorenvio.base-url}")
    private String baseUrl;

    public MelhorEnvioServico(RestTemplate restTemplate, ObjectMapper mapper) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }

    public FreteRespostaDTO calcularFreteMelhorEnvio(FreteRequisicaoDTO request) {
        try {
            String json = enviarRequisicao(request);
            List<Map<String, Object>> lista = mapper.readValue(json, new TypeReference<>() {
            });
            if (lista == null || lista.size() < 2)
                throw new IllegalArgumentException("Lista de fretes incompleta ou inesperada");
            return montarRespostaFrete(lista, request);
        } catch (Exception exception) {
            throw new RuntimeException("Erro ao consultar API Melhor Envio", exception);
        }
    }

    private String enviarRequisicao(FreteRequisicaoDTO request) {
        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, new HttpEntity<>(request), String.class);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null)
            throw new IllegalStateException("Resposta inválida da API Melhor Envio");
        return response.getBody();
    }

    private FreteRespostaDTO montarRespostaFrete(List<Map<String, Object>> lista, FreteRequisicaoDTO requisicao) {
        Map<String, Object> pac = lista.get(0);
        Map<String, Object> sedex = lista.get(1);
        FreteRespostaDTO frete = new FreteRespostaDTO();
        frete.setCepOrigem(requisicao.getFrom().getPostalCode());
        frete.setCepDestino(requisicao.getTo().getPostalCode());
        frete.setValorPac(parseDouble(pac.get("custom_price")));
        frete.setPrazoPac(parseInt(pac.get("delivery_time")));
        frete.setValorSedex(parseDouble(sedex.get("custom_price")));
        frete.setPrazoSedex(parseInt(sedex.get("delivery_time")));
        Map<String, Object> company = (Map<String, Object>) pac.get("company");
        frete.setLinkPostagem(company != null ? (String) company.get("picture") : null);
        return frete;
    }

    private Double parseDouble(Object value) {
        return value == null ? null : Double.valueOf(value.toString());
    }

    private Integer parseInt(Object value) {
        return value == null ? null : Integer.valueOf(value.toString());
    }
}
