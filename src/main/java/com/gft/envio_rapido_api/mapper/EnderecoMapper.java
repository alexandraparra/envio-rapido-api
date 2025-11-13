package com.gft.envio_rapido_api.mapper;

import com.gft.envio_rapido_api.dominio.Endereco;
import com.gft.envio_rapido_api.dto.EnderecoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {
    Endereco toEntidade(EnderecoDTO enderecoDTO);

    EnderecoDTO toDTO(Endereco endereco);
}
