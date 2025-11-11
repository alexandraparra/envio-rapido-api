package com.gft.envio_rapido_api.servico;

import com.gft.envio_rapido_api.dominio.Usuario;
import com.gft.envio_rapido_api.dto.UsuarioDTO;
import com.gft.envio_rapido_api.mapper.UsuarioMapper;
import com.gft.envio_rapido_api.repositorio.UsuarioRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServico {

    private final UsuarioRepositorio repositorio;

    private final UsuarioMapper mapper;

    public UsuarioServico(UsuarioRepositorio repositorio, UsuarioMapper mapper) {
        this.repositorio = repositorio;
        this.mapper = mapper;
    }

    public List<UsuarioDTO> listarUsuarios() {
        List<UsuarioDTO> usuarioList = repositorio.findAll().stream().map(mapper::toDTO).toList();
        return usuarioList;
    }

    public UsuarioDTO salvarUsuario(Usuario usuario) {
        Usuario salvo = repositorio.save(usuario);
        return mapper.toDTO(salvo);
    }

    public UsuarioDTO alterarUsuario(String id, Usuario usuario) {
        Usuario usuarioExistente = this.buscarUsuarioPorId(id);
        mapper.atualizarUsuario(usuarioExistente, usuario);
        Usuario usuarioSalvo = repositorio.save(usuarioExistente);
        return mapper.toDTO(usuarioSalvo);
    }

    public UsuarioDTO buscarUsuarioPorIdDto(String id) {
        Usuario usuario = this.buscarUsuarioPorId(id);
        return mapper.toDTO(usuario);
    }

    public Usuario buscarUsuarioPorId(String id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário com id: " + id + " não encontrado"));
    }

    public void deletar(String id) {
        Usuario usuario = this.buscarUsuarioPorId(id);
        repositorio.delete(usuario);
    }
}
