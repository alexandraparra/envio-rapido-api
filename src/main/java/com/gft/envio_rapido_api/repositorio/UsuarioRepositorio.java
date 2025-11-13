package com.gft.envio_rapido_api.repositorio;

import com.gft.envio_rapido_api.dominio.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepositorio extends MongoRepository<Usuario, String> {

    UserDetails findByLogin(String login);
}
