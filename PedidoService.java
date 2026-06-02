package com.loja.pedido.service;

import com.loja.pedido.dto.CheckoutRequest;
import com.loja.pedido.entity.Pedido;
import com.loja.pedido.repository.PedidoRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final RestTemplate restTemplate;

    @Value("${produto.service.url}")
    private String produtoUrl;

    @Value("${pagamento.service.url}")
    private String pagamentoUrl;

    @Value("${notificacao.service.url}")
    private String notificacaoUrl;

    public PedidoService(PedidoRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    // Fluxo completo: chama Produto → Pagamento → Notificação
    public Pedido checkout(CheckoutRequest req) {
        Pedido pedido = repository.save(
            new Pedido(req.getProdutoId(), req.getQuantidade(), req.getValorTotal())
        );

        boolean estoqueOk = verificarEstoque(req.getProdutoId(), req.getQuantidade());
        if (!estoqueOk) {
            pedido.setStatus("FALHOU - SEM ESTOQUE");
            return repository.save(pedido);
        }

        String statusPagamento = processarPagamento(pedido.getId(), req.getValorTotal());
        if (!"APROVADO".equals(statusPagamento)) {
            pedido.setStatus("FALHOU - PAGAMENTO RECUSADO");
            return repository.save(pedido);
        }

        enviarNotificacao(pedido.getId(), "Pedido #" + pedido.getId() + " confirmado com sucesso!");

        pedido.setStatus("CONFIRMADO");
        return repository.save(pedido);
    }

    @CircuitBreaker(name = "produto", fallbackMethod = "fallbackEstoque")
    public boolean verificarEstoque(Long produtoId, Integer quantidade) {
        String url = produtoUrl + "/produtos/" + produtoId + "/verificar-estoque?quantidade=" + quantidade;
        Boolean resultado = restTemplate.postForObject(url, null, Boolean.class);
        return Boolean.TRUE.equals(resultado);
    }

    public boolean fallbackEstoque(Long produtoId, Integer quantidade, Throwable t) {
        System.out.println("[FALLBACK] Produto Service indisponível: " + t.getMessage());
        return false;
    }

    @CircuitBreaker(name = "pagamento", fallbackMethod = "fallbackPagamento")
    public String processarPagamento(Long pedidoId, java.math.BigDecimal valor) {
        String url = pagamentoUrl + "/pagamentos?pedidoId=" + pedidoId + "&valor=" + valor;
        var response = restTemplate.postForObject(url, null, java.util.Map.class);
        return response != null ? (String) response.get("status") : "RECUSADO";
    }

    public String fallbackPagamento(Long pedidoId, java.math.BigDecimal valor, Throwable t) {
        System.out.println("[FALLBACK] Pagamento Service indisponível: " + t.getMessage());
        return "RECUSADO";
    }

    @CircuitBreaker(name = "notificacao", fallbackMethod = "fallbackNotificacao")
    public void enviarNotificacao(Long pedidoId, String mensagem) {
        String url = notificacaoUrl + "/notificacoes?pedidoId=" + pedidoId + "&mensagem=" + mensagem;
        restTemplate.postForObject(url, null, Object.class);
    }

    public void fallbackNotificacao(Long pedidoId, String mensagem, Throwable t) {
        System.out.println("[FALLBACK] Notificacao Service indisponível. Notificação não enviada para pedido #" + pedidoId);
    }
}
