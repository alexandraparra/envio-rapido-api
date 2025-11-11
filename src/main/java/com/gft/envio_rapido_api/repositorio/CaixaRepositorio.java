package com.gft.envio_rapido_api.repositorio;

import com.gft.envio_rapido_api.dominio.Caixa;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CaixaRepositorio extends MongoRepository<Caixa, String> {
}
