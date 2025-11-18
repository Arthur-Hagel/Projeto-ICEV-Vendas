package br.icev.vendas;

import java.text.NumberFormat;
import java.util.Locale;

public class UtilDinheiro {
    public static String formatar(double valor) {
        NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return formato.format(valor);
    }
}