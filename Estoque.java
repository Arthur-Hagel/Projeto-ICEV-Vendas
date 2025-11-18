package br.icev.vendas;

import br.icev.vendas.excecoes.QuantidadeInvalidaException;
import br.icev.vendas.excecoes.SemEstoqueException;
import java.util.HashMap;
import java.util.Map;

public class Estoque {

    // O inventário é mapeado pelo código do Produto
    private final Map<String, Integer> inventario;

    public Estoque() {
        this.inventario = new HashMap<>();
    }

    /**
     * Adiciona uma quantidade de estoque ao SKU (exigido por 'adicionaEConsultaEstoque').
     */
    public void adicionarEstoque(String codigo, int quantidade) throws QuantidadeInvalidaException {
        if (quantidade <= 0) {
            // Exigido por 'naoAceitaQuantidadeZeroOuNegativa'
            throw new QuantidadeInvalidaException("A quantidade a adicionar deve ser positiva.");
        }
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("Código não pode ser nulo ou vazio.");
        }

        int quantidadeAtual = inventario.getOrDefault(codigo, 0);
        inventario.put(codigo, quantidadeAtual + quantidade);
    }

    /**
     * Consulta a quantidade disponível para um SKU (exigido por 'adicionaEConsultaEstoque').
     */
    public int getDisponivel(String codigo) {
        if (codigo == null) return 0;
        return inventario.getOrDefault(codigo, 0);
    }

    /**
     * Remove (reserva) uma quantidade do estoque (exigido por 'reservarReduzEstoqueELimita').
     */
    public void reservar(String codigo, int quantidade) throws QuantidadeInvalidaException, SemEstoqueException {
        if (quantidade <= 0) {
            throw new QuantidadeInvalidaException("A quantidade a reservar deve ser positiva.");
        }
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("Código não pode ser nulo ou vazio.");
        }

        int quantidadeAtual = getDisponivel(codigo);

        if (quantidadeAtual < quantidade) {
            // Exigido por 'reservarReduzEstoqueELimita'
            throw new SemEstoqueException("Estoque insuficiente para reservar " + quantidade + " unidades de " + codigo);
        }

        inventario.put(codigo, quantidadeAtual - quantidade);
    }
}