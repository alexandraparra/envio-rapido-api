package com.gft.envio_rapido_api.servico;

import com.gft.envio_rapido_api.dominio.Endereco;
import com.gft.envio_rapido_api.dto.EnderecoDTO;
import com.gft.envio_rapido_api.mapper.EnderecoMapper;
import com.gft.envio_rapido_api.repositorio.EnderecoRepositorio;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class EnderecoServico {
    private final EnderecoRepositorio repositorio;
    private final EnderecoMapper mapper;
    private final ViaCepService viaCepService;

    public EnderecoServico(EnderecoRepositorio repositorio, EnderecoMapper mapper, ViaCepService viaCepService) {
        this.repositorio = repositorio;
        this.mapper = mapper;
        this.viaCepService = viaCepService;
    }

    public Endereco salvarEndereco(EnderecoDTO enderecoDTO) {
        try {
            EnderecoDTO viaCepEndereco = viaCepService.buscarEnderecoPorCep(enderecoDTO.getCep());
            if (enderecoDTO.getLogradouro() == null) enderecoDTO.setLogradouro(viaCepEndereco.getLogradouro());
            if (enderecoDTO.getComplemento() == null) enderecoDTO.setComplemento(viaCepEndereco.getComplemento());
            enderecoDTO.setBairro(viaCepEndereco.getBairro());
            enderecoDTO.setLocalidade(viaCepEndereco.getLocalidade());
            enderecoDTO.setUf(viaCepEndereco.getUf());
            Endereco endereco = repositorio.save(mapper.toEntidade(enderecoDTO));
            return endereco;
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException("Erro ao salvar endereço: dados inaválidos ", exception);
        }
    }
}
