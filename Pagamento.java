package com.loja.pagamento.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamentos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long pedidoId;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private String status; // APROVADO | RECUSADO

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    public Pagamento() {}
    public Pagamento(Long pedidoId, BigDecimal valor, String status) {
        this.pedidoId = pedidoId;
        this.valor = valor;
        this.status = status;
        this.criadoEm = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getPedidoId() { return pedidoId; }
    public BigDecimal getValor() { return valor; }
    public String getStatus() { return status; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
}
