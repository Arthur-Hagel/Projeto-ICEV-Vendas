package br.icev.vendas;

import org.junit.jupiter.api.Test;

import br.icev.vendas.excecoes.QuantidadeInvalidaException;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

/** NÃO ALTERE ESTES TESTES. Implemente as classes de produção para fazê-los passar. */
class ProdutoTeste {

    @Test
    void criaProdutoValido() throws QuantidadeInvalidaException {
        Produto p = new Produto("SKU-1", "Camiseta", new BigDecimal("49.90"));
        assertEquals("SKU-1", p.getCodigo());
        assertEquals("Camiseta", p.getNome());
        assertEquals(new BigDecimal("49.90"), p.getPrecoUnitario());
    }

    @Test
    void naoPermitePrecoNegativoOuNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Produto("X", "Y", new BigDecimal("-1.00")));
        assertThrows(NullPointerException.class,
                () -> new Produto("X", "Y", null));
    }

    @Test
    void igualdadePorCodigo() throws QuantidadeInvalidaException {
        Produto a = new Produto("ABC", "Qualquer", new BigDecimal("10.00"), 1);
        Produto b = new Produto("ABC", "Outro", new BigDecimal("99.99"), 1);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
