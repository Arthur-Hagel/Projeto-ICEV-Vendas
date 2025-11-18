package br.icev.vendas;

import java.math.BigDecimal;

public interface PoliticaDesconto {
    
    /**
     * Calcula o valor final (já com o desconto aplicado) com base no valor bruto.
     * @param valorBruto O valor total antes de qualquer desconto.
     * @return O valor líquido (valor final) após a aplicação da política.
     */
    BigDecimal aplicarDesconto(BigDecimal valorBruto);
}