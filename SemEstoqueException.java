package br.icev.vendas.excecoes;

public class SemEstoqueException extends RuntimeException {

    public SemEstoqueException(String mensagem) {
        super(mensagem);
    }
}