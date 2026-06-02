package com.loja.pagamento.service;

import com.loja.pagamento.entity.Pagamento;
import com.loja.pagamento.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PagamentoService {

    private final PagamentoRepository repository;

    public PagamentoService(PagamentoRepository repository) {
        this.repository = repository;
    }

    public Pagamento processar(Long pedidoId, BigDecimal valor) {
        // Simula aprovação (valor < 10000 aprovado)
        String status = valor.compareTo(new BigDecimal("10000")) < 0 ? "APROVADO" : "RECUSADO";
        return repository.save(new Pagamento(pedidoId, valor, status));
    }
}
