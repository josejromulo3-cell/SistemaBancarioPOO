package emprestimo;

import cliente.Cliente;
import conta.Conta;
import gerente.Gerente;

public class Emprestimo {
    private Cliente cliente;
    private Conta conta;
    private double valor;
    private int parcelas;
    private double taxaJuros;
    private boolean aprovado;

    public Emprestimo(Cliente cliente, Conta conta, double valor, int parcelas, double taxaJuros) {
        this.cliente = cliente;
        this.conta = conta;
        this.valor = valor;
        this.parcelas = parcelas;
        this.taxaJuros = taxaJuros;
        this.aprovado = false;
    }

    public Emprestimo(Conta conta, double valor, int parcelas) {
        this(conta.getCliente(), conta, valor, parcelas, 0.02);
    }

    public void aprovar() {
        this.aprovado = true;
    }

    public void aprovar(Gerente gerente) {
        this.aprovado = true;
    }

    public boolean isAprovado() { return aprovado; }
    public double getValor() { return valor; }
}
