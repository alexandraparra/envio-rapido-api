package com.gft.envio_rapido_api.servico;

import com.gft.envio_rapido_api.dominio.Caixa;
import com.gft.envio_rapido_api.dto.CaixaDTO;
import com.gft.envio_rapido_api.mapper.CaixaMapper;
import com.gft.envio_rapido_api.repositorio.CaixaRepositorio;
import org.springframework.stereotype.Service;

@Service
public class CaixaServico {

    private final CaixaRepositorio repositorio;

    private final CaixaMapper mapper;

    public CaixaServico(CaixaRepositorio repositorio, CaixaMapper mapper) {
        this.repositorio = repositorio;
        this.mapper = mapper;
    }

    public Caixa salvarCaixa(CaixaDTO caixaDTO) {
        try {
            return repositorio.save(mapper.toEntidade(caixaDTO));
        } catch (RuntimeException exception) {
            throw new RuntimeException("Erro ao salvar caixa: dados inválidos ", exception);
        }
    }
}
