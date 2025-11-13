package com.gft.envio_rapido_api.controler;

import com.gft.envio_rapido_api.dto.usuarioDTO.AutenticacaoDTO;
import com.gft.envio_rapido_api.dto.usuarioDTO.CadastroDTO;
import com.gft.envio_rapido_api.dto.usuarioDTO.LoginRespostaDTO;
import com.gft.envio_rapido_api.servico.AutenticacaoServico;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AutenticacaoControler {
    private final AutenticacaoServico servico;

    public AutenticacaoControler(AutenticacaoServico servico) {
        this.servico = servico;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRespostaDTO> login(@Valid @RequestBody AutenticacaoDTO data) {
        LoginRespostaDTO resposta = servico.autenticar(data);
        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Void> cadastrar(@Valid @RequestBody CadastroDTO data) {
        servico.cadastrar(data);
        return ResponseEntity.status(201).build();
    }
}