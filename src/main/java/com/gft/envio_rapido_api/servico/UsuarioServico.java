package com.gft.envio_rapido_api.servico;

import com.gft.envio_rapido_api.dominio.Usuario;
import com.gft.envio_rapido_api.repositorio.UsuarioRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServico {
    
    private final UsuarioRepositorio repositorio;

    public UsuarioServico(UsuarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<Usuario> listarUsuarios() {
        return repositorio.findAll();
    }

    public Usuario salvarUsuario(Usuario usuario) {
        return repositorio.save(usuario);
    }

    public Usuario alterarUsuario(String id, Usuario usuarioAtualizado) {
        Usuario usuarioSalvo = this.buscarUsuarioPorId(id);

        usuarioSalvo.setNome(usuarioAtualizado.getNome());
        usuarioSalvo.setLogin(usuarioAtualizado.getLogin());
        usuarioSalvo.setSenha(usuarioAtualizado.getSenha());
        usuarioSalvo.setPapel(usuarioAtualizado.getPapel());

        return repositorio.save(usuarioSalvo);
    }

    public Usuario buscarUsuarioPorId(String id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário com id: " + id + "não encontrado"));
    }

    public void deletar(String id) {
        Usuario usuarioSalvo = this.buscarUsuarioPorId(id);
        repositorio.delete(usuarioSalvo);
    }
}
