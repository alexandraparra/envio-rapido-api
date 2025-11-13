package com.gft.envio_rapido_api.repositorio;

import com.gft.envio_rapido_api.dominio.Endereco;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnderecoRepositorio extends MongoRepository<Endereco, String> {
}
