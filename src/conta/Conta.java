package conta;

import cliente.Cliente;
import operacao.Operacao;
import util.GeradorNumeroConta;
import excecao.*;
import interfaces.Transferivel;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata base para representação de contas bancárias.
 * Aplica abstração, encapsulamento e implementa a interface Transferivel.
 * 
 * Fonte: Bank Program - Inheritance and Abstraction
 * (https://github.com/Jon-Peppinck/javaBankInheritAbstract)
 */
public abstract class Conta implements Transferivel {
    protected String numero;
    protected String agencia;
    protected double saldo;
    protected boolean statusAtivo;
    protected Cliente cliente;
    protected List<Operacao> historico;

    public Conta(String agencia, Cliente cliente) {
        this.numero = GeradorNumeroConta.gerar();
        this.agencia = agencia;
        this.cliente = cliente;
        this.saldo = 0.0;
        this.statusAtivo = true;
        this.historico = new ArrayList<>();
    }

    public void depositar(double valor) throws ValorInvalidoException, ContaBloqueadaException {
        if (!statusAtivo) {
            throw new ContaBloqueadaException("Conta bloqueada para depósitos.");
        }
        if (valor <= 0) {
            throw new ValorInvalidoException("O valor do depósito deve ser maior que zero.");
        }
        this.saldo += valor;
    }

    public void sacar(double valor) throws SaldoInsuficienteException, ContaBloqueadaException, ValorInvalidoException {
        if (!statusAtivo) {
            throw new ContaBloqueadaException("Conta bloqueada para saques.");
        }
        if (valor <= 0) {
            throw new ValorInvalidoException("O valor do saque deve ser maior que zero.");
        }
        if (valor > saldo) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar o saque.", saldo);
        }
        this.saldo -= valor;
    }

    @Override
    public void transferir(Conta destino, double valor) 
            throws SaldoInsuficienteException, ContaBloqueadaException, ValorInvalidoException {
        if (destino == null) {
            throw new ValorInvalidoException("Conta destino é inválida.");
        }
        this.sacar(valor);
        destino.depositar(valor);
    }

    // Método abstrato a ser implementado de acordo com a regra de cada tipo de conta
    public abstract void aplicarTarifaMensal();

    public void bloquear() {
        this.statusAtivo = false;
    }

    public void desbloquear() {
        this.statusAtivo = true;
    }

    public void adicionarOperacao(Operacao operacao) {
        if (operacao != null) {
            this.historico.add(operacao);
        }
    }

    // Getters e Setters
    public String getNumero() { return numero; }
    public String getAgencia() { return agencia; }
    public double getSaldo() { return saldo; }
    public boolean isStatusAtivo() { return statusAtivo; }
    public Cliente getCliente() { return cliente; }
    public List<Operacao> getHistorico() { return historico; }
}
