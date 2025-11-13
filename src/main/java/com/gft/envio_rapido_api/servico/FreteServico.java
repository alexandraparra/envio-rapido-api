package com.gft.envio_rapido_api.servico;

import com.gft.envio_rapido_api.dominio.Frete;
import com.gft.envio_rapido_api.dto.CaixaDTO;
import com.gft.envio_rapido_api.dto.envioDTO.EnvioRequisicaoDTO;
import com.gft.envio_rapido_api.dto.freteDTO.FreteRequisicaoDTO;
import com.gft.envio_rapido_api.dto.freteDTO.FreteRespostaDTO;
import com.gft.envio_rapido_api.dto.freteDTO.FromToDTO;
import com.gft.envio_rapido_api.mapper.FreteMapper;
import com.gft.envio_rapido_api.repositorio.FreteRepositorio;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class FreteServico {
    private final FreteRepositorio repositorio;
    private final FreteMapper mapper;
    private final MelhorEnvioServico melhorEnvioServico;

    public FreteServico(FreteRepositorio repositorio, FreteMapper mapper, MelhorEnvioServico melhorEnvioServico) {
        this.repositorio = repositorio;
        this.mapper = mapper;
        this.melhorEnvioServico = melhorEnvioServico;
    }

    public Frete salvarFrete(FreteRespostaDTO freteRespostaDTO) {
        try {
            return repositorio.save(mapper.toEntidade(freteRespostaDTO));
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException("Erro ao salvar frete: dados inaválidos ", exception);
        }
    }

    public FreteRespostaDTO calcularFrete(EnvioRequisicaoDTO dto, CaixaDTO caixa) {
        FreteRequisicaoDTO requisicao = new FreteRequisicaoDTO();
        FromToDTO from = new FromToDTO();
        from.setPostalCode(dto.getCepOrigem());
        FromToDTO to = new FromToDTO();
        to.setPostalCode(dto.getCepDestino());
        requisicao.setFrom(from);
        requisicao.setTo(to);
        requisicao.setCaixa(caixa);
        return melhorEnvioServico.calcularFreteMelhorEnvio(requisicao);
    }
}
