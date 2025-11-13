package com.gft.envio_rapido_api.servico;

import com.gft.envio_rapido_api.dominio.*;
import com.gft.envio_rapido_api.dto.EnderecoDTO;
import com.gft.envio_rapido_api.dto.envioDTO.EnvioRequisicaoDTO;
import com.gft.envio_rapido_api.dto.envioDTO.EnvioRespostaDTO;
import com.gft.envio_rapido_api.dto.freteDTO.FreteRespostaDTO;
import com.gft.envio_rapido_api.excecao.CepOrigemDestinoIgualException;
import com.gft.envio_rapido_api.excecao.EnderecoInvalidoException;
import com.gft.envio_rapido_api.mapper.EnvioMapper;
import com.gft.envio_rapido_api.repositorio.EnvioRepositorio;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class EnvioServico {
    private final EnvioRepositorio repositorio;
    private final EnvioMapper mapper;
    private final UsuarioServico usuarioServico;
    private final EnderecoServico enderecoServico;
    private final CaixaServico caixaServico;
    private final FreteServico freteServico;

    public EnvioServico(EnvioRepositorio repositorio, EnvioMapper mapper, UsuarioServico usuarioServico, EnderecoServico enderecoServico, CaixaServico caixaServico, FreteServico freteServico) {
        this.repositorio = repositorio;
        this.mapper = mapper;
        this.usuarioServico = usuarioServico;
        this.enderecoServico = enderecoServico;
        this.caixaServico = caixaServico;
        this.freteServico = freteServico;
    }

    @Transactional
    public EnvioRespostaDTO salvarEnvio(EnvioRequisicaoDTO envioDTO) {
        try {
            if (envioDTO.getCepOrigem().equals(envioDTO.getCepDestino()))
                throw new CepOrigemDestinoIgualException("O CEP de origem não pode ser igual ao CEP de destino.");
            Usuario remetente = usuarioServico.resolverRemetente(envioDTO);
            Endereco origem = enderecoServico.salvarEndereco(construirEnderecoOrigem(envioDTO));
            Endereco destino = enderecoServico.salvarEndereco(construirEnderecoDestino(envioDTO));
            Caixa caixaSalva = caixaServico.salvarCaixa(envioDTO.getCaixa());
            FreteRespostaDTO freteResposta = freteServico.calcularFrete(envioDTO, envioDTO.getCaixa());
            Frete freteSalvo = freteServico.salvarFrete(freteResposta);
            Envio envio = mapper.toEntity(envioDTO);
            envio.setRemetente(remetente);
            envio.setEnderecoOrigem(origem);
            envio.setEnderecoDestino(destino);
            envio.setCaixa(caixaSalva);
            envio.setFrete(freteSalvo);
            envio.setMensagem("Cálculo de frete realizado com sucesso");
            return mapper.toRespostaDTO(repositorio.save(envio));
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException("Erro ao salvar envio: dados inválidos.", exception);
        }
    }

    public EnvioRespostaDTO buscarEnvioPorId(String id) {
        Envio envio = repositorio.findById(id).orElseThrow(() -> new NoSuchElementException("Envio não encontrado com o id: " + id));
        return mapper.toRespostaDTO(envio);
    }

    private EnderecoDTO construirEnderecoOrigem(EnvioRequisicaoDTO requisicaoDTO) {
        if (requisicaoDTO.getEndereco() == null || requisicaoDTO.getEndereco().isBlank())
            throw new EnderecoInvalidoException("O endereço de origem é obrigatório.");
        String[] partes = requisicaoDTO.getEndereco().split(",", 2);
        String logradouro = partes[0].trim();
        String complemento = (partes.length > 1) ? partes[1].trim() : "";
        if (logradouro.isBlank())
            throw new EnderecoInvalidoException("O endereço deve conter pelo menos o logradouro.");
        EnderecoDTO origem = new EnderecoDTO();
        origem.setLogradouro(logradouro);
        origem.setComplemento(complemento);
        origem.setCep(requisicaoDTO.getCepOrigem());
        return origem;
    }

    private EnderecoDTO construirEnderecoDestino(EnvioRequisicaoDTO dto) {
        EnderecoDTO destino = new EnderecoDTO();
        destino.setCep(dto.getCepDestino());
        return destino;
    }
}
