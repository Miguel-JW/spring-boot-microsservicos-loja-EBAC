package com.loja.notificacao.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificacoes")
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long pedidoId;

    @Column(nullable = false)
    private String mensagem;

    @Column(nullable = false)
    private LocalDateTime enviadoEm;

    public Notificacao() {}
    public Notificacao(Long pedidoId, String mensagem) {
        this.pedidoId = pedidoId;
        this.mensagem = mensagem;
        this.enviadoEm = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getPedidoId() { return pedidoId; }
    public String getMensagem() { return mensagem; }
    public LocalDateTime getEnviadoEm() { return enviadoEm; }
}
