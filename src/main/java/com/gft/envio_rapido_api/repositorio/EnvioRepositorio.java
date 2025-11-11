package com.gft.envio_rapido_api.repositorio;

import com.gft.envio_rapido_api.dominio.Envio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnvioRepositorio extends MongoRepository<Envio, String> {
}
