package com.gft.envio_rapido_api.servico;

import com.gft.envio_rapido_api.dto.EnderecoDTO;
import com.gft.envio_rapido_api.excecao.CepInvalidoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService {
    private final RestTemplate restTemplate;
    @Value("${viacep.base-url}")
    private String baseUrl;

    public ViaCepService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EnderecoDTO buscarEnderecoPorCep(String cep) {
        EnderecoDTO endereco = restTemplate.getForObject(baseUrl, EnderecoDTO.class, cep);

        if (endereco == null || Boolean.TRUE.equals(endereco.getErro())) {
            throw new CepInvalidoException("CEP inválido.");
        }
        return endereco;
    }
}
