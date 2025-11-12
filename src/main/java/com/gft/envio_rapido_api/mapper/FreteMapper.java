package com.gft.envio_rapido_api.mapper;

import com.gft.envio_rapido_api.dominio.Frete;
import com.gft.envio_rapido_api.dto.freteDTO.FreteRespostaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FreteMapper {

    Frete toEntidade(FreteRespostaDTO freteRespostaDTO);

    FreteRespostaDTO toDTO(Frete frete);
}
