package br.icev.vendas.excecoes;

public class ErroPagamentoException extends RuntimeException {

    public ErroPagamentoException(String mensagem) {
        super(mensagem);
    }
}