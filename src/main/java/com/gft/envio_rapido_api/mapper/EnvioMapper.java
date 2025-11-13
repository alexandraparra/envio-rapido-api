package com.gft.envio_rapido_api.mapper;

import com.gft.envio_rapido_api.dominio.Envio;
import com.gft.envio_rapido_api.dto.envioDTO.EnvioRequisicaoDTO;
import com.gft.envio_rapido_api.dto.envioDTO.EnvioRespostaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnvioMapper {

    @Mapping(target = "nomeRemetente", source = "remetente.nome")
    @Mapping(target = "endereco", source = "enderecoOrigem.logradouro")
    @Mapping(target = "caixa.larguraCaixa", source = "caixa.larguraCaixa")
    @Mapping(target = "caixa.alturaCaixa", source = "caixa.alturaCaixa")
    @Mapping(target = "caixa.comprimentoCaixa", source = "caixa.comprimentoCaixa")
    @Mapping(target = "caixa.pesoCaixa", source = "caixa.pesoCaixa")
    @Mapping(target = "cepOrigem", source = "enderecoOrigem.cep")
    @Mapping(target = "cepDestino", source = "enderecoDestino.cep")
    EnvioRequisicaoDTO toDTO(Envio envio);

    @Mapping(target = "nomeRemetente", source = "remetente.nome")
    @Mapping(target = "cepOrigem", source = "enderecoOrigem.cep")
    @Mapping(target = "cepDestino", source = "enderecoDestino.cep")
    EnvioRespostaDTO toRespostaDTO(Envio envio);

    @Mapping(target = "remetente", ignore = true)
    @Mapping(target = "caixa.larguraCaixa", source = "caixa.larguraCaixa")
    @Mapping(target = "caixa.alturaCaixa", source = "caixa.alturaCaixa")
    @Mapping(target = "caixa.comprimentoCaixa", source = "caixa.comprimentoCaixa")
    @Mapping(target = "caixa.pesoCaixa", source = "caixa.pesoCaixa")
    @Mapping(target = "enderecoOrigem.cep", source = "cepOrigem")
    @Mapping(target = "enderecoDestino.cep", source = "cepDestino")
    Envio toEntity(EnvioRequisicaoDTO envioRequisicaoDTO);
}
