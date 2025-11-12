package com.gft.envio_rapido_api.servico;

import com.gft.envio_rapido_api.dominio.Envio;
import com.gft.envio_rapido_api.dto.envioDTO.EnvioRequisicaoDTO;
import com.gft.envio_rapido_api.dto.envioDTO.EnvioRespostaDTO;
import com.gft.envio_rapido_api.dto.freteDTO.FreteRequisicaoDTO;
import com.gft.envio_rapido_api.dto.freteDTO.FreteRespostaDTO;
import com.gft.envio_rapido_api.dto.freteDTO.FromDTO;
import com.gft.envio_rapido_api.mapper.EnvioMapper;
import com.gft.envio_rapido_api.repositorio.EnvioRepositorio;
import org.springframework.stereotype.Service;

@Service
public class EnvioServico {

    private final EnvioRepositorio repositorio;

    private final EnvioMapper mapper;

    private final CaixaServico caixaServico;

    private final MelhorEnvioServico melhorEnvioServico;

    private final FreteServico freteServico;

    public EnvioServico(EnvioRepositorio repositorio, EnvioMapper mapper, CaixaServico caixaServico, MelhorEnvioServico melhorEnvioServico, FreteServico freteServico) {
        this.repositorio = repositorio;
        this.mapper = mapper;
        this.caixaServico = caixaServico;
        this.melhorEnvioServico = melhorEnvioServico;
        this.freteServico = freteServico;
    }

    public EnvioRespostaDTO salvarEnvio(EnvioRequisicaoDTO envioDTO) {
        Envio entidade = mapper.toEntidade(envioDTO);
        this.caixaServico.salvarCaixa(entidade.getCaixa());
        FreteRequisicaoDTO freteRequisicao = this.montarFreteRequisicao(envioDTO);
        FreteRespostaDTO freteCalculado = melhorEnvioServico.calcularFrete(freteRequisicao);
        freteServico.salvarFrete(freteCalculado);

        Envio salvo = repositorio.save(entidade);
        EnvioRespostaDTO resposta = mapper.toDTO(salvo);
        resposta.setFrete(freteCalculado);
        resposta.setMensagem("Envio cadastrado com sucesso");
        return resposta;
    }

    public EnvioRespostaDTO buscarEnvioPorIdDto(String id) {
        Envio envio = this.buscarEnvioPorId(id);
        return mapper.toDTO(envio);
    }

    public Envio buscarEnvioPorId(String id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário com id: " + id + " não encontrado"));
    }

    private FreteRequisicaoDTO montarFreteRequisicao(EnvioRequisicaoDTO envioDTO) {
        FromDTO origem = new FromDTO();
        origem.setPostalCode(envioDTO.getCepOrigem());

        FromDTO destino = new FromDTO();
        destino.setPostalCode(envioDTO.getCepDestino());

        FreteRequisicaoDTO freteRequisicao = new FreteRequisicaoDTO();
        freteRequisicao.setFrom(origem);
        freteRequisicao.setTo(destino);
        freteRequisicao.setPacote(envioDTO.getCaixa());
        return freteRequisicao;
    }
}
