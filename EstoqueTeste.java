package br.icev.vendas;

import br.icev.vendas.excecoes.QuantidadeInvalidaException;
import br.icev.vendas.excecoes.SemEstoqueException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** NÃO ALTERE ESTES TESTES. Implemente as classes de produção para fazê-los passar. */
class EstoqueTeste {

    @Test
    void adicionaEConsultaEstoque() throws QuantidadeInvalidaException {
        Estoque est = new Estoque();
        est.adicionarEstoque("SKU", 10);
        assertEquals(10, est.getDisponivel("SKU"));
    }

    @Test
    void naoAceitaQuantidadeZeroOuNegativa() {
        Estoque est = new Estoque();
        assertThrows(QuantidadeInvalidaException.class, () -> est.adicionarEstoque("SKU", 0));
        assertThrows(QuantidadeInvalidaException.class, () -> est.adicionarEstoque("SKU", -5));
    }

    @Test
    void reservarReduzEstoqueELimita() throws QuantidadeInvalidaException, SemEstoqueException {
        Estoque est = new Estoque();
        est.adicionarEstoque("SKU", 5);
        est.reservar("SKU", 3);
        assertEquals(2, est.getDisponivel("SKU"));
        assertThrows(SemEstoqueException.class, () -> est.reservar("SKU", 3));
    }
}
