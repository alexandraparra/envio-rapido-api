package com.gft.envio_rapido_api.configuracao.seguridade;

import com.gft.envio_rapido_api.repositorio.UsuarioRepositorio;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class Filtro extends OncePerRequestFilter {
    @Autowired
    ServicoToken servicoToken;
    @Autowired
    UsuarioRepositorio repositorio;

    @Override
    protected void doFilterInternal(HttpServletRequest requisicao, HttpServletResponse resposta, FilterChain filtro) throws ServletException, IOException {
        var token = this.recoverToken(requisicao);
        if (token != null) {
            var login = servicoToken.validarToken(token);
            UserDetails usuario = repositorio.findByLogin(login);
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filtro.doFilter(requisicao, resposta);
    }

    private String recoverToken(HttpServletRequest requisicao) {
        var authHeader = requisicao.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}