package com.loja.pedido.controller;

import com.loja.pedido.dto.CheckoutRequest;
import com.loja.pedido.entity.Pedido;
import com.loja.pedido.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    // Fluxo completo: Pedido → Produto → Pagamento → Notificação
    @PostMapping("/checkout")
    public ResponseEntity<Pedido> checkout(@RequestBody CheckoutRequest request) {
        return ResponseEntity.ok(service.checkout(request));
    }
}
