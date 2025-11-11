package com.gft.envio_rapido_api.mapper;

import com.gft.envio_rapido_api.dominio.Usuario;
import com.gft.envio_rapido_api.dto.UsuarioDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario toEntidade(UsuarioDTO usuarioDTO);

    UsuarioDTO toDTO(Usuario usuario);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void atualizarUsuario(@MappingTarget Usuario destino, Usuario origem);
}
