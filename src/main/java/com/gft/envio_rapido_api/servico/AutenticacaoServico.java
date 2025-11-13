package com.gft.envio_rapido_api.servico;

import com.gft.envio_rapido_api.configuracao.seguridade.ServicoToken;
import com.gft.envio_rapido_api.dominio.Usuario;
import com.gft.envio_rapido_api.dto.usuarioDTO.AutenticacaoDTO;
import com.gft.envio_rapido_api.dto.usuarioDTO.CadastroDTO;
import com.gft.envio_rapido_api.dto.usuarioDTO.LoginRespostaDTO;
import com.gft.envio_rapido_api.enums.UsuarioPapel;
import com.gft.envio_rapido_api.excecao.LoginInvalidoException;
import com.gft.envio_rapido_api.excecao.UsuarioJaExisteException;
import com.gft.envio_rapido_api.repositorio.UsuarioRepositorio;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoServico {
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepositorio repository;
    private final ServicoToken servicoToken;

    public AutenticacaoServico(AuthenticationManager authenticationManager, UsuarioRepositorio repository, ServicoToken servicoToken) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.servicoToken = servicoToken;
    }

    public LoginRespostaDTO autenticar(AutenticacaoDTO data) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var auth = authenticationManager.authenticate(authToken);
            var token = servicoToken.gerarToken((Usuario) auth.getPrincipal());
            return new LoginRespostaDTO(token);
        } catch (AuthenticationException e) {
            throw new LoginInvalidoException("Login ou senha inválidos.");
        }
    }

    public void cadastrar(CadastroDTO data) {
        if (repository.findByLogin(data.login()) != null) {
            throw new UsuarioJaExisteException("Já existe um usuário com este login.");
        }
        String senhaCriptografada = new BCryptPasswordEncoder().encode(data.password());
        UsuarioPapel papel = UsuarioPapel.valueOf(data.role());
        Usuario novo = new Usuario(data.login(), senhaCriptografada, papel);
        repository.save(novo);
    }
}