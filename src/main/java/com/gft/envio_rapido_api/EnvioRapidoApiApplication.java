package com.gft.envio_rapido_api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition
@SpringBootApplication
public class EnvioRapidoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnvioRapidoApiApplication.class, args);
	}

}
