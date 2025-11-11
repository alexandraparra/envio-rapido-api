package com.gft.envio_rapido_api.servico;

import com.gft.envio_rapido_api.dominio.Envio;
import com.gft.envio_rapido_api.dto.envioDTO.EnvioRequisicaoDTO;
import com.gft.envio_rapido_api.dto.envioDTO.EnvioRespostaDTO;
import com.gft.envio_rapido_api.mapper.EnvioMapper;
import com.gft.envio_rapido_api.repositorio.EnvioRepositorio;
import org.springframework.stereotype.Service;

@Service
public class EnvioServico {

    private final EnvioRepositorio repositorio;

    private final EnvioMapper mapper;

    private final CaixaServico caixaServico;

    public EnvioServico(EnvioRepositorio repositorio, EnvioMapper mapper, CaixaServico caixaServico) {
        this.repositorio = repositorio;
        this.mapper = mapper;
        this.caixaServico = caixaServico;
    }

    public EnvioRespostaDTO salvarEnvio(EnvioRequisicaoDTO envioDTO) {
        Envio entidade = mapper.toEntidade(envioDTO);
        this.caixaServico.salvarCaixa(entidade.getCaixa());
        Envio salvo = repositorio.save(entidade);
        return mapper.toDTO(salvo);
    }

    public EnvioRespostaDTO buscarEnvioPorIdDto(String id) {
        Envio envio = this.buscarEnvioPorId(id);
        return mapper.toDTO(envio);
    }

    public Envio buscarEnvioPorId(String id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário com id: " + id + " não encontrado"));
    }
}
