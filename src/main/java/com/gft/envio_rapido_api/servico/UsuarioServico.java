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

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
        Usuario entidade = mapper.toEntidade(usuarioDTO);
        Usuario salvo = repositorio.save(entidade);
        return mapper.toDTO(salvo);
    }

    public UsuarioDTO alterarUsuario(String id, UsuarioDTO usuarioAtualizado) {
        Usuario usuarioExistente = this.buscarUsuarioPorId(id);
        Usuario dadosAtualizados = mapper.toEntidade(usuarioAtualizado);
        mapper.atualizarUsuario(usuarioExistente, dadosAtualizados);
        return mapper.toDTO(repositorio.save(usuarioExistente));
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
