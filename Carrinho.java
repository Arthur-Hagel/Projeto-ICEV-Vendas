package br.icev.vendas;

import br.icev.vendas.excecoes.QuantidadeInvalidaException;
import br.icev.vendas.excecoes.SemEstoqueException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Carrinho {
    private final Map<Produto, Integer> itens;
    private final Estoque estoque;

    public Carrinho(Estoque estoque) {
        if (estoque == null) {
            throw new IllegalArgumentException("Estoque não pode ser nulo.");
        }
        this.itens = new HashMap<>();
        this.estoque = estoque;
    }

    /**
     * Adiciona um produto ao carrinho, verificando a disponibilidade no estoque.
     * Este é o método principal que os testes usam para adicionar itens.
     * @param produto O produto a ser adicionado.
     * @param quantidade A quantidade a ser adicionada.
     */
    public void adicionar(Produto produto, int quantidade) throws QuantidadeInvalidaException, SemEstoqueException {
        if (quantidade <= 0) {
            throw new QuantidadeInvalidaException("A quantidade deve ser positiva.");
        }
        if (produto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo.");
        }

        String codigo = produto.getCodigo();
        int quantidadeAtualNoCarrinho = itens.getOrDefault(produto, 0);
        int totalRequerido = quantidadeAtualNoCarrinho + quantidade;
        int estoqueDisponivel = estoque.getDisponivel(codigo);

        if (estoqueDisponivel < totalRequerido) {
             throw new SemEstoqueException("Estoque insuficiente para adicionar " + quantidade + " unidades de " + produto.getNome());
        }

        itens.put(produto, totalRequerido);
    }
    
    /**
     * Calcula o valor total dos itens no carrinho aplicando uma política de desconto.
     * Método esperado por CarrinhoTeste (getTotalCom).
     * @param politica A política de desconto a ser aplicada. Pode ser nulo.
     * @return O valor total final em BigDecimal.
     */
    public BigDecimal getTotalCom(PoliticaDesconto politica) {
        BigDecimal totalBruto = BigDecimal.ZERO;
        
        for (Map.Entry<Produto, Integer> entrada : itens.entrySet()) {
            Produto produto = entrada.getKey();
            int quantidade = entrada.getValue();
            
            // Calcula o subtotal do item: precoUnitario * quantidade
            BigDecimal precoTotalItem = produto.getPrecoUnitario().multiply(new BigDecimal(quantidade));
            totalBruto = totalBruto.add(precoTotalItem);
        }
        
        // Aplica o desconto se uma política for fornecida
        if (politica != null) {
            // A política deve retornar o valor líquido (já com o desconto aplicado)
            return politica.aplicarDesconto(totalBruto);
        }
        
        return totalBruto;
    }
    
    /**
     * Finaliza a compra, reservando (removendo) os itens do estoque e limpando o carrinho.
     * Necessário para o CheckoutTeste.
     */
    public void fecharCompra() throws SemEstoqueException, QuantidadeInvalidaException {
        for (Map.Entry<Produto, Integer> entrada : itens.entrySet()) {
            Produto produto = entrada.getKey();
            int quantidade = entrada.getValue();

            // O Estoque.reservar() remove os itens do inventário
            estoque.reservar(produto.getCodigo(), quantidade);
        }
        itens.clear(); // Limpa o carrinho
    }

    /**
     * Retorna a quantidade de um produto específico no carrinho.
     */
    public int getQuantidade(Produto produto) {
        return itens.getOrDefault(produto, 0);
    }
    
    /**
     * Retorna o mapeamento dos itens (código/quantidade) para o CheckoutTeste.
     */
    public Map<String, Integer> getItensPorCodigo() {
        Map<String, Integer> mapa = new HashMap<>();
        for (Map.Entry<Produto, Integer> entrada : itens.entrySet()) {
            mapa.put(entrada.getKey().getCodigo(), entrada.getValue());
        }
        return mapa;
    }
    
    // Método auxiliar 'p' parece ser um helper do teste. Não é implementado aqui,
    // mas o teste provavelmente chama adicionar(Produto, int) indiretamente.

    // Sobrescrita do toString() para auxiliar no teste (se houver)
    @Override
    public String toString() {
        return "Carrinho com " + itens.size() + " produtos diferentes.";
    }
}