package pix;

import conta.Conta;

public class ChavePix {
    private String valor;
    private TipoChavePix tipo;
    private Conta conta;

    public ChavePix(String valor, TipoChavePix tipo, Conta conta) {
        this.valor = valor;
        this.tipo = tipo;
        this.conta = conta;
    }

    public String getValor() { return valor; }
    public TipoChavePix getTipo() { return tipo; }
    public Conta getConta() { return conta; }
}
