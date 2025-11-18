package br.icev.vendas;

import br.icev.vendas.excecoes.QuantidadeInvalidaException;
import br.icev.vendas.excecoes.SemEstoqueException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Carrinho {
    private final Map<Produto, Integer> itens;
    private final Estoque estoque;

    public Carrinho(Estoque estoque) {
        this.estoque = Objects.requireNonNull(estoque, "Estoque não pode ser nulo.");
        this.itens = new HashMap<>();
    }

    /**
     * Adiciona um produto ao carrinho. O CarrinhoTeste usa este método.
     */
    public void adicionar(Produto produto, int quantidade) throws QuantidadeInvalidaException, SemEstoqueException {
        if (quantidade <= 0) {
            throw new QuantidadeInvalidaException("A quantidade deve ser positiva.");
        }
        Objects.requireNonNull(produto, "Produto não pode ser nulo.");
        
        String codigo = produto.getCodigo();
        int quantidadeAtualNoCarrinho = itens.getOrDefault(produto, 0);
        int totalRequerido = quantidadeAtualNoCarrinho + quantidade;
        int estoqueDisponivel = estoque.getDisponivel(codigo);

        if (estoqueDisponivel < totalRequerido) {
             throw new SemEstoqueException("Estoque insuficiente para " + produto.getNome());
        }

        itens.put(produto, totalRequerido);
    }
    
    /**
     * Calcula o valor total com aplicação de política de desconto.
     * Método esperado por CarrinhoTeste (getTotalCom).
     */
    public BigDecimal getTotalCom(PoliticaDesconto politica) {
        BigDecimal totalBruto = BigDecimal.ZERO;
        
        for (Map.Entry<Produto, Integer> entrada : itens.entrySet()) {
            Produto produto = entrada.getKey();
            int quantidade = entrada.getValue();
            
            // Subtotal = precoUnitario * quantidade
            BigDecimal precoTotalItem = produto.getPrecoUnitario().multiply(new BigDecimal(quantidade));
            totalBruto = totalBruto.add(precoTotalItem);
        }
        
        // Aplica o desconto (a política é responsável por aplicar e garantir que não é negativo)
        if (politica != null) {
            return politica.aplicarDesconto(totalBruto);
        }
        
        return totalBruto;
    }
    
    /**
     * Finaliza a compra, removendo os itens do estoque e limpando o carrinho.
     * Necessário para o Checkout.
     */
    public void fecharCompra() throws SemEstoqueException, QuantidadeInvalidaException {
        for (Map.Entry<Produto, Integer> entrada : itens.entrySet()) {
            Produto produto = entrada.getKey();
            int quantidade = entrada.getValue();

            // Usa o método 'reservar' do Estoque
            estoque.reservar(produto.getCodigo(), quantidade);
        }
        itens.clear(); // Limpa o carrinho após a reserva
    }

    public int getQuantidade(Produto produto) {
        return itens.getOrDefault(produto, 0);
    }
    
    /**
     * Retorna o mapa de itens por código/quantidade, necessário para o Checkout.
     */
    public Map<String, Integer> getItensPorCodigo() {
        Map<String, Integer> mapa = new HashMap<>();
        for (Map.Entry<Produto, Integer> entrada : itens.entrySet()) {
            mapa.put(entrada.getKey().getCodigo(), entrada.getValue());
        }
        return mapa;
    }
    
    public boolean estaVazio() {
        return itens.isEmpty();
    }
}