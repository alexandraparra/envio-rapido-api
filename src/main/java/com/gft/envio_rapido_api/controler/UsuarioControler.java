package com.gft.envio_rapido_api.controler;

import com.gft.envio_rapido_api.dto.UsuarioDTO;
import com.gft.envio_rapido_api.servico.UsuarioServico;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControler {

    private final UsuarioServico servico;

    public UsuarioControler(UsuarioServico servico) {
        this.servico = servico;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuarios = servico.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioNovo = servico.salvarUsuario(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNovo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> alterarUsuario(@PathVariable String id, @Valid @RequestBody UsuarioDTO usuarioAtualizado) {
        try {
            UsuarioDTO usuario = servico.alterarUsuario(id, usuarioAtualizado);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        try {
            servico.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorId(@PathVariable String id) {
        try {
            UsuarioDTO usuario = servico.buscarUsuarioPorIdDto(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
