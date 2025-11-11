package com.gft.envio_rapido_api.servico;

import com.gft.envio_rapido_api.dominio.Caixa;
import com.gft.envio_rapido_api.dto.CaixaDTO;
import com.gft.envio_rapido_api.mapper.CaixaMapper;
import com.gft.envio_rapido_api.repositorio.CaixaRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaixaServico {

    private final CaixaRepositorio repositorio;

    private final CaixaMapper mapper;

    public CaixaServico(CaixaRepositorio repositorio, CaixaMapper mapper) {
        this.repositorio = repositorio;
        this.mapper = mapper;
    }

    public List<Caixa> listarCaixas() {
        return repositorio.findAll();
    }

    public Caixa salvarCaixa(CaixaDTO caixaDTO) {
        Caixa entidade = mapper.toEntidade(caixaDTO);
        return repositorio.save(entidade);
    }

    public Caixa alterarCaixa(String id, CaixaDTO caixaAtualizado) {
        Caixa caixaExistente = this.buscarCaixaPorId(id);
        Caixa dadosAtualizados = mapper.toEntidade(caixaAtualizado);
        mapper.atualizarCaixa(caixaExistente, dadosAtualizados);
        return repositorio.save(caixaExistente);
    }

    public Caixa buscarCaixaPorId(String id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Caixa com id: " + id + " não encontrada"));
    }

    public void deletar(String id) {
        Caixa caixa = this.buscarCaixaPorId(id);
        repositorio.delete(caixa);
    }
}
