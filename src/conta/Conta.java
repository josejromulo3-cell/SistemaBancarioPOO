package conta;

import cliente.Cliente;
import excecao.ContaBloqueadaException;
import excecao.SaldoInsuficienteException;
import excecao.ValorInvalidoException;
import interfaces.Transferivel;
import operacao.Operacao;

import java.util.ArrayList;
import java.util.List;

public abstract class Conta implements Transferivel {
    protected String numero;
    protected double saldo;
    protected Cliente cliente;
    protected boolean bloqueada;
    protected boolean ativa;
    protected List<Operacao> operacoes;

    public Conta(String numero, Cliente cliente) {
        this.numero = numero;
        this.cliente = cliente;
        this.saldo = 0.0;
        this.bloqueada = false;
        this.ativa = true;
        this.operacoes = new ArrayList<>();
    }

    public String getNumero() { return numero; }
    public double getSaldo() { return saldo; }
    public Cliente getCliente() { return cliente; }
    public boolean isBloqueada() { return bloqueada; }
    public void setBloqueada(boolean bloqueada) { this.bloqueada = bloqueada; }
    public boolean isAtiva() { return ativa; }
    public void setAtiva(boolean ativa) { this.ativa = ativa; }
    public List<Operacao> getOperacoes() { return operacoes; }

    public void adicionarOperacao(Operacao operacao) {
        if (operacao != null) {
            this.operacoes.add(operacao);
        }
    }

    public void depositar(double valor) throws ValorInvalidoException, ContaBloqueadaException {
        if (isBloqueada() || !isAtiva()) throw new ContaBloqueadaException("Conta inativa/bloqueada.");
        if (valor <= 0) throw new ValorInvalidoException("Valor deve ser maior que zero.");
        this.saldo += valor;
    }

    public void sacar(double valor) throws SaldoInsuficienteException, ValorInvalidoException, ContaBloqueadaException {
        if (isBloqueada() || !isAtiva()) throw new ContaBloqueadaException("Conta inativa/bloqueada.");
        if (valor <= 0) throw new ValorInvalidoException("Valor do saque deve ser maior que zero.");
        if (saldo < valor) throw new SaldoInsuficienteException("Saldo insuficiente.", saldo);
        this.saldo -= valor;
    }

    @Override
    public void transferir(Conta destino, double valor) throws SaldoInsuficienteException, ValorInvalidoException, ContaBloqueadaException {
        if (destino == null) throw new ValorInvalidoException("Conta destino inválida.");
        this.sacar(valor);
        destino.depositar(valor);
    }
}
