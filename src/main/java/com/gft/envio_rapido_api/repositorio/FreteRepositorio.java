package com.gft.envio_rapido_api.repositorio;

import com.gft.envio_rapido_api.dominio.Frete;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FreteRepositorio extends MongoRepository<Frete, String> {
}
