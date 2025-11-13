package com.gft.envio_rapido_api.mapper;

import com.gft.envio_rapido_api.dominio.Usuario;
import com.gft.envio_rapido_api.dto.UsuarioDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario toEntidade(UsuarioDTO usuarioDTO);

    UsuarioDTO toDTO(Usuario usuario);
}
