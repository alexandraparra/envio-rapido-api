package com.gft.envio_rapido_api.servico;

import com.gft.envio_rapido_api.dominio.Frete;
import com.gft.envio_rapido_api.dto.freteDTO.FreteRespostaDTO;
import com.gft.envio_rapido_api.mapper.FreteMapper;
import com.gft.envio_rapido_api.repositorio.FreteRepositorio;
import org.springframework.stereotype.Service;

@Service
public class FreteServico {

    private final FreteRepositorio repositorio;

    private final FreteMapper mapper;

    public FreteServico(FreteRepositorio repositorio, FreteMapper mapper) {
        this.repositorio = repositorio;
        this.mapper = mapper;
    }


    public Frete salvarFrete(FreteRespostaDTO freteRespostaDTO) {
        Frete entidade = mapper.toEntidade(freteRespostaDTO);
        return repositorio.save(entidade);
    }
}
