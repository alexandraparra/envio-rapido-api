package com.gft.envio_rapido_api.mapper;

import com.gft.envio_rapido_api.dominio.Frete;
import com.gft.envio_rapido_api.dto.FreteDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface FreteMapper {
    Frete toEntidade(FreteDTO freteDTO);

    FreteDTO toDTO(Frete frete);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void atualizarFrete(@MappingTarget Frete destino, Frete origem);
}
