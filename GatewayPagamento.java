package br.icev.vendas;

import br.icev.vendas.excecoes.ErroPagamentoException;
import java.math.BigDecimal;

public interface GatewayPagamento {
    
    /**
     * Processa a cobrança de um determinado valor.
     * @param valor O valor a ser cobrado.
     * @return O código de autorização da transação.
     * @throws ErroPagamentoException Se a cobrança falhar.
     */
    String cobrar(BigDecimal valor) throws ErroPagamentoException;
}