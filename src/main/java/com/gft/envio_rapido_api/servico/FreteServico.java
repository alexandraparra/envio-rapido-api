package com.gft.envio_rapido_api.servico;

import com.gft.envio_rapido_api.dominio.Frete;
import com.gft.envio_rapido_api.dto.FreteDTO;
import com.gft.envio_rapido_api.mapper.FreteMapper;
import com.gft.envio_rapido_api.repositorio.FreteRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreteServico {

    private final FreteRepositorio repositorio;

    private final FreteMapper mapper;

    public FreteServico(FreteRepositorio repositorio, FreteMapper mapper) {
        this.repositorio = repositorio;
        this.mapper = mapper;
    }

    public List<FreteDTO> listarFretes() {
        List<FreteDTO> freteList = repositorio.findAll().stream().map(mapper::toDTO).toList();
        return freteList;
    }

    public FreteDTO salvarFrete(FreteDTO freteDTO) {
        Frete entidade = mapper.toEntidade(freteDTO);
        Frete salvo = repositorio.save(entidade);
        return mapper.toDTO(salvo);
    }

    public FreteDTO alterarFrete(String id, FreteDTO freteAtualizado) {
        Frete freteExistente = this.buscarFretePorId(id);
        Frete dadosAtualizados = mapper.toEntidade(freteAtualizado);
        mapper.atualizarFrete(freteExistente, dadosAtualizados);
        return mapper.toDTO(repositorio.save(freteExistente));
    }

    public FreteDTO buscarFretePorIdDto(String id) {
        Frete frete = this.buscarFretePorId(id);
        return mapper.toDTO(frete);
    }

    public Frete buscarFretePorId(String id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Frete com id: " + id + " não encontrado"));
    }

    public void deletar(String id) {
        Frete frete = this.buscarFretePorId(id);
        repositorio.delete(frete);
    }
}
