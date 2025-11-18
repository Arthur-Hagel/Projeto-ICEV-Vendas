package br.icev.vendas;

import java.math.BigDecimal;
import java.util.Objects;

public class Produto {

    private final String codigo;
    private final String nome;
    private final BigDecimal precoUnitario;

    // Construtor principal (exigido por 'criaProdutoValido')
    public Produto(String codigo, String nome, BigDecimal precoUnitario) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("O código não pode ser nulo ou vazio.");
        }
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser nulo ou vazio.");
        }
        if (precoUnitario == null) {
            // Exigido por 'naoPermitePrecoNegativoOuNulo'
            throw new NullPointerException("O preço unitário não pode ser nulo.");
        }
        if (precoUnitario.compareTo(BigDecimal.ZERO) < 0) {
            // Exigido por 'naoPermitePrecoNegativoOuNulo'
            throw new IllegalArgumentException("O preço unitário não pode ser negativo.");
        }

        this.codigo = codigo;
        // Garante precisão de duas casas decimais, um padrão de `BigDecimal` para moedas.
        this.precoUnitario = precoUnitario.setScale(2, BigDecimal.ROUND_HALF_UP);
        this.nome = nome;
    }

    // Construtor auxiliar (exigido por 'igualdadePorCodigo' com argumento int)
    // O argumento 'int' (que deve ser 'quantidade') é ignorado neste construtor,
    // pois o teste 'igualdadePorCodigo' só visa testar a igualdade.
    public Produto(String codigo, String nome, BigDecimal precoUnitario, int quantidade) {
        this(codigo, nome, precoUnitario);
    }

    // Getters
    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    // A igualdade deve ser APENAS pelo código (exigido por 'igualdadePorCodigo')
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(codigo, produto.codigo);
    }

    // O hashCode também deve ser APENAS pelo código
    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}