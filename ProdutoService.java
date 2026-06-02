package com.loja.produto.service;

import com.loja.produto.entity.Produto;
import com.loja.produto.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public boolean verificarEReduzirEstoque(Long produtoId, Integer quantidade) {
        return repository.findById(produtoId).map(p -> {
            if (p.getEstoque() >= quantidade) {
                p.setEstoque(p.getEstoque() - quantidade);
                repository.save(p);
                return true;
            }
            return false;
        }).orElse(false);
    }

    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }
}
