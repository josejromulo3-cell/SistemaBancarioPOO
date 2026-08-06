package conta;

import cliente.Cliente;
import operacao.Operacao;
import excecao.ContaBloqueadaException;
import excecao.SaldoInsuficienteException;
import excecao.ValorInvalidoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Conta {
    private String numero;
    protected double saldo;
    private Cliente cliente;
    protected boolean ativa;
    private List<Operacao> historicoOperacoes;

    public Conta(String numero, Cliente cliente) {
        this.numero = numero;
        this.cliente = cliente;
        this.saldo = 0.0;
        this.ativa = true;
        this.historicoOperacoes = new ArrayList<>();
    }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public double getSaldo() { return saldo; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public boolean isAtiva() { return ativa; }
    public void setAtiva(boolean ativa) { this.ativa = ativa; }

    public boolean isStatusAtivo() { return ativa; }

    public void adicionarOperacao(Operacao operacao) {
        if (operacao != null) {
            this.historicoOperacoes.add(operacao);
        }
    }

    public List<Operacao> getHistoricoOperacoes() {
        return historicoOperacoes;
    }

    public void depositar(double valor) throws ValorInvalidoException, ContaBloqueadaException {
        if (!ativa) throw new ContaBloqueadaException("Conta está bloqueada.");
        if (valor <= 0) throw new ValorInvalidoException("O valor do depósito deve ser maior que zero.");
        this.saldo += valor;
    }

    public void sacar(double valor) throws ValorInvalidoException, SaldoInsuficienteException, ContaBloqueadaException {
        if (!ativa) throw new ContaBloqueadaException("Conta está bloqueada.");
        if (valor <= 0) throw new ValorInvalidoException("O valor do saque deve ser maior que zero.");
        if (valor > saldo) throw new SaldoInsuficienteException("Saldo insuficiente.", saldo);
        this.saldo -= valor;
    }

    public void transferir(Conta destino, double valor) throws ValorInvalidoException, SaldoInsuficienteException, ContaBloqueadaException {
        if (destino == null) {
            throw new ValorInvalidoException("Conta de destino inválida.");
        }
        this.sacar(valor);
        destino.depositar(valor);
    }

    // --- Métodos abstratos/base para permitir @Override nas subclasses ---
    public void cobrarTarifaMensal() throws SaldoInsuficienteException, ValorInvalidoException, ContaBloqueadaException {}
    public void cobrarTarifaOperacional() throws SaldoInsuficienteException, ValorInvalidoException, ContaBloqueadaException {}
    public void debitarTarifaMensal() throws SaldoInsuficienteException, ValorInvalidoException, ContaBloqueadaException {}
    public void debitarTarifa() throws SaldoInsuficienteException, ValorInvalidoException, ContaBloqueadaException {}
    public void aplicarRendimento() {}
    public void renderJuros() {}
    public void processar() {}
    public void debitarTarifaManutencao() throws SaldoInsuficienteException, ValorInvalidoException, ContaBloqueadaException {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conta conta = (Conta) o;
        return Objects.equals(numero != null ? numero.trim() : null, 
                              conta.numero != null ? conta.numero.trim() : null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero != null ? numero.trim() : null);
    }
}
