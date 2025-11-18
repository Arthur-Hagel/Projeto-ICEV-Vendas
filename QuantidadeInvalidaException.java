package br.icev.vendas.excecoes;

public class QuantidadeInvalidaException extends RuntimeException {

    public QuantidadeInvalidaException(String mensagem) {
        super(mensagem);
    }
}