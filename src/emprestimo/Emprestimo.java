package emprestimo;

import cliente.Cliente;
import conta.Conta;
import gerente.Gerente;
import excecao.ContaBloqueadaException;
import excecao.ValorInvalidoException;

/**
 * Representa uma solicitação e controle de Empréstimo no banco.
 */
public class Emprestimo {
    private double valor;
    private int parcelas;
    private double taxaJuros;
    private boolean aprovado;
    private Cliente cliente;
    private Conta contaDestino;
    private Gerente gerenteAprovador;

    public Emprestimo(Cliente cliente, Conta contaDestino, double valor, int parcelas, double taxaJuros) {
        this.cliente = cliente;
        this.contaDestino = contaDestino;
        this.valor = valor;
        this.parcelas = parcelas;
        this.taxaJuros = taxaJuros;
        this.aprovado = false;
    }

    /**
     * Calcula o valor total a ser pago com juros simples/compostos.
     */
    public double calcularValorTotal() {
        return valor * (1 + (taxaJuros * parcelas));
    }

    /**
     * Aprova o empréstimo e credita o valor na conta do cliente.
     */
    public void aprovar(Gerente gerente) {
        this.aprovado = true;
        this.gerenteAprovador = gerente;
        try {
            this.contaDestino.depositar(this.valor);
        } catch (ValorInvalidoException | ContaBloqueadaException e) {
            System.err.println("Erro ao creditar valor do empréstimo na conta: " + e.getMessage());
        }
    }

    // Getters e Setters
    public double getValor() { return valor; }
    public int getParcelas() { return parcelas; }
    public double getTaxaJuros() { return taxaJuros; }
    public boolean isAprovado() { return aprovado; }
    public Cliente getCliente() { return cliente; }
    public Conta getContaDestino() { return contaDestino; }
    public Gerente getGerenteAprovador() { return gerenteAprovador; }
}
