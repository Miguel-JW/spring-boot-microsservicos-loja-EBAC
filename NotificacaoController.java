package com.loja.notificacao.controller;

import com.loja.notificacao.entity.Notificacao;
import com.loja.notificacao.service.NotificacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    private final NotificacaoService service;

    public NotificacaoController(NotificacaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Notificacao> enviar(@RequestParam Long pedidoId,
                                               @RequestParam String mensagem) {
        return ResponseEntity.ok(service.enviar(pedidoId, mensagem));
    }
}
