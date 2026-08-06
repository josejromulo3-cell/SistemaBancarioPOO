package pix;

import conta.Conta;

/**
 * Representa uma chave Pix cadastrada e vinculada a uma conta bancária.
 */
public class ChavePix {
    private String chave;
    private TipoChavePix tipo;
    private Conta conta;

    public ChavePix(String chave, TipoChavePix tipo, Conta conta) {
        this.chave = chave;
        this.tipo = tipo;
        this.conta = conta;
    }

    public String getChave() {
        return chave;
    }

    public TipoChavePix getTipo() {
        return tipo;
    }

    public Conta getConta() {
        return conta;
    }
}
