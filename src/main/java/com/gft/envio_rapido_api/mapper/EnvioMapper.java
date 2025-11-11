package com.gft.envio_rapido_api.mapper;

import com.gft.envio_rapido_api.dominio.Envio;
import com.gft.envio_rapido_api.dto.envioDTO.EnvioRequisicaoDTO;
import com.gft.envio_rapido_api.dto.envioDTO.EnvioRespostaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnvioMapper {

    Envio toEntidade(EnvioRequisicaoDTO envioDTO);

    EnvioRespostaDTO toDTO(Envio envio);
}
