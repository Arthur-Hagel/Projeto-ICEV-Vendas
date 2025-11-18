package br.icev.vendas;

import java.math.BigDecimal;
import java.util.Map;

public class Pedido {

    public enum Status {
        PENDENTE,
        PAGO,
        CANCELADO
    }
    
    private final Map<String, Integer> itens;
    private final BigDecimal total;
    private final String autorizacaoPagamento;
    private final Status status;

    public Pedido(Map<String, Integer> itens, BigDecimal total, String autorizacaoPagamento, Status status) {
        this.itens = itens;
        this.total = total;
        this.autorizacaoPagamento = autorizacaoPagamento;
        this.status = status;
    }

    // Getters para checagem em testes (necessários para o CheckoutTeste)

    public BigDecimal getTotal() {
        return total;
    }

    public Status getStatus() {
        return status;
    }

    public String getAutorizacaoPagamento() {
        return autorizacaoPagamento;
    }
}