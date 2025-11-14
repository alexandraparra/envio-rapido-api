package com.gft.envio_rapido_api.servico;

import com.gft.envio_rapido_api.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutorizacaoServico implements UserDetailsService {
    @Autowired
    UsuarioRepositorio repositorio;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        var user = repositorio.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + login);
        }
        return user;
    }
}
