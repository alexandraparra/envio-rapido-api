package com.gft.envio_rapido_api.mapper;

import com.gft.envio_rapido_api.dominio.Caixa;
import com.gft.envio_rapido_api.dto.CaixaDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CaixaMapper {
    Caixa toEntidade(CaixaDTO caixaDTO);

    CaixaDTO toDTO(Caixa caixa);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void atualizarCaixa(@MappingTarget Caixa destino, Caixa origem);
}
