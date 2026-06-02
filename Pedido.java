package com.loja.pedido.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long produtoId;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    private String status; // PROCESSANDO | CONFIRMADO | FALHOU

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    public Pedido() {}
    public Pedido(Long produtoId, Integer quantidade, BigDecimal valorTotal) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.status = "PROCESSANDO";
        this.criadoEm = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getProdutoId() { return produtoId; }
    public Integer getQuantidade() { return quantidade; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public String getStatus() { return status; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setStatus(String status) { this.status = status; }
}
