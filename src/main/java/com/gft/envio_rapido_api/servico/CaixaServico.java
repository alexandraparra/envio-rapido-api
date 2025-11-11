package com.gft.envio_rapido_api.servico;

import com.gft.envio_rapido_api.dominio.Caixa;
import com.gft.envio_rapido_api.repositorio.CaixaRepositorio;
import org.springframework.stereotype.Service;

@Service
public class CaixaServico {

    private final CaixaRepositorio repositorio;

    public CaixaServico(CaixaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Caixa salvarCaixa(Caixa caixa) {
        return repositorio.save(caixa);
    }
}
