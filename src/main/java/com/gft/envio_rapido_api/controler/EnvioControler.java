package com.gft.envio_rapido_api.controler;

import com.gft.envio_rapido_api.dto.envioDTO.EnvioRequisicaoDTO;
import com.gft.envio_rapido_api.dto.envioDTO.EnvioRespostaDTO;
import com.gft.envio_rapido_api.servico.EnvioServico;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/envios")
public class EnvioControler {

    private final EnvioServico servico;

    public EnvioControler(EnvioServico servico) {
        this.servico = servico;
    }

    @PostMapping
    public ResponseEntity<EnvioRespostaDTO> salvarEnvio(@Valid @RequestBody EnvioRequisicaoDTO envioDTO) {
        EnvioRespostaDTO envioNovo = servico.salvarEnvio(envioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(envioNovo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvioRespostaDTO> buscarEnvioPorId(@PathVariable String id) {
        try {
            EnvioRespostaDTO envio = servico.buscarEnvioPorIdDto(id);
            return ResponseEntity.ok(envio);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
