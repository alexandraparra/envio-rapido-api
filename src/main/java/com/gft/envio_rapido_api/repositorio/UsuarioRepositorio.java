package com.gft.envio_rapido_api.repositorio;

import com.gft.envio_rapido_api.dominio.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepositorio extends MongoRepository<Usuario, String> {
}
