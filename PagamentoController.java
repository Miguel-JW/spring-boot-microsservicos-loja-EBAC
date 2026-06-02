package com.loja.pagamento.controller;

import com.loja.pagamento.entity.Pagamento;
import com.loja.pagamento.service.PagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService service;

    public PagamentoController(PagamentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Pagamento> processar(@RequestParam Long pedidoId,
                                                @RequestParam BigDecimal valor) {
        return ResponseEntity.ok(service.processar(pedidoId, valor));
    }
}
