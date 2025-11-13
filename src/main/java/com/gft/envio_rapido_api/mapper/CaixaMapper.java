package com.gft.envio_rapido_api.mapper;

import com.gft.envio_rapido_api.dominio.Caixa;
import com.gft.envio_rapido_api.dto.CaixaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CaixaMapper {
    Caixa toEntidade(CaixaDTO caixaDTO);

    CaixaDTO toDTO(Caixa caixa);
}
