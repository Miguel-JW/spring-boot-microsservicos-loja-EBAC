package com.loja.notificacao.service;

import com.loja.notificacao.entity.Notificacao;
import com.loja.notificacao.repository.NotificacaoRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoService {

    private final NotificacaoRepository repository;

    public NotificacaoService(NotificacaoRepository repository) {
        this.repository = repository;
    }

    public Notificacao enviar(Long pedidoId, String mensagem) {
        return repository.save(new Notificacao(pedidoId, mensagem));
    }
}
