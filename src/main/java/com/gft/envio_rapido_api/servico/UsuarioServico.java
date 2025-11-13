package com.gft.envio_rapido_api.servico;

import com.gft.envio_rapido_api.dominio.Usuario;
import com.gft.envio_rapido_api.dto.envioDTO.EnvioRequisicaoDTO;
import com.gft.envio_rapido_api.repositorio.UsuarioRepositorio;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServico {
    private final UsuarioRepositorio repositorio;

    public UsuarioServico(UsuarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Usuario salvarUsuarioSimples(String nomeRemetente) {
        try {
            Usuario usuario = new Usuario();
            usuario.setNome(nomeRemetente);
            return repositorio.save(usuario);
        } catch (DataIntegrityViolationException exception) {
            throw new RuntimeException("Erro ao salvar usuário: dados inaválidos ", exception);
        }
    }

    public UserDetails buscarUsuarioPorLogin(String nomeRemetente) {
        return repositorio.findByLogin(nomeRemetente);
    }

    public Usuario resolverRemetente(EnvioRequisicaoDTO dto) {
        if (dto.getNomeRemetente() != null && !dto.getNomeRemetente().isBlank())
            return this.salvarUsuarioSimples(dto.getNomeRemetente().trim());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal()))
            throw new RuntimeException("Usuário não autenticado.");
        String login = auth.getName();
        Usuario usuario = (Usuario) this.buscarUsuarioPorLogin(login);
        if (usuario == null) throw new RuntimeException("Usuário autenticado não encontrado: " + login);
        return usuario;
    }
}
