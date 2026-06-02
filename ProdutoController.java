package com.loja.produto.controller;

import com.loja.produto.entity.Produto;
import com.loja.produto.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody Produto produto) {
        return ResponseEntity.ok(service.salvar(produto));
    }

    @PostMapping("/{id}/verificar-estoque")
    public ResponseEntity<Boolean> verificar(@PathVariable Long id,
                                              @RequestParam Integer quantidade) {
        return ResponseEntity.ok(service.verificarEReduzirEstoque(id, quantidade));
    }
}
