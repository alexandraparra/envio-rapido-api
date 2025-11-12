package com.gft.envio_rapido_api.servico;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.envio_rapido_api.dominio.Frete;
import com.gft.envio_rapido_api.dto.freteDTO.FreteRequisicaoDTO;
import com.gft.envio_rapido_api.dto.freteDTO.FreteRespostaDTO;
import com.gft.envio_rapido_api.mapper.FreteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MelhorEnvioServico {

    @Value("${melhorenvio.api.url}")
    private String urlApi;

    @Value("${melhorenvio.api.token}")
    private String token;

    private final RestTemplate clienteHttp = new RestTemplate();
    private final ObjectMapper conversorJson = new ObjectMapper();
    private final FreteMapper freteMapper;

    public FreteRespostaDTO calcularFrete(FreteRequisicaoDTO requisicao) {
        String url = urlApi + "/me/shipment/calculate";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<FreteRequisicaoDTO> entidade = new HttpEntity<>(requisicao, headers);
        ResponseEntity<String> resposta = clienteHttp.exchange(url, HttpMethod.POST, entidade, String.class);

        Frete frete = extrairFrete(resposta.getBody());
        FreteRespostaDTO freteResposta = freteMapper.toDTO(frete);
        freteResposta.setCepOrigem(requisicao.getFrom().getPostalCode());
        freteResposta.setCepDestino(requisicao.getTo().getPostalCode());
        return freteResposta;
    }

    private Frete extrairFrete(String json) {
        try {
            Frete frete = new Frete();
            JsonNode node = conversorJson.readTree(json);

            for (JsonNode item : node) {
                if (!item.has("name")) {
                    continue;
                }

                String nomeServico = item.get("name").asText().toLowerCase();
                double valor = item.path("price").asDouble(0.0);
                int prazo = item.path("delivery_time").asInt(0);

                if (nomeServico.contains("pac")) {
                    frete.setValorPac(valor);
                    frete.setPrazoPac(prazo);
                } else if (nomeServico.contains("sedex")) {
                    frete.setValorSedex(valor);
                    frete.setPrazoSedex(prazo);
                }
            }
            frete.setLinkPostagem("https://www.melhorenvio.com.br/painel/envios");
            return frete;
        } catch (Exception excecao) {
            throw new RuntimeException("Erro ao processar resposta da API Melhor Envio", excecao);
        }
    }
}