package conta;

import cliente.Cliente;
import excecao.ContaBloqueadaException;
import excecao.SaldoInsuficienteException;
import excecao.ValorInvalidoException;

public class ContaCorrente extends Conta {
    private double limiteChequeEspecial;
    private double tarifaMensal;

    // Construtor completo
    public ContaCorrente(String numero, Cliente cliente, double limiteChequeEspecial, double tarifaMensal) {
        super(numero, cliente);
        this.limiteChequeEspecial = limiteChequeEspecial;
        this.tarifaMensal = tarifaMensal;
    }

    // Construtor simplificado (usado pelo menu)
    public ContaCorrente(String numero, Cliente cliente) {
        this(numero, cliente, 500.0, 15.0);
    }

    public double getLimiteChequeEspecial() { return limiteChequeEspecial; }
    public void setLimiteChequeEspecial(double limite) { this.limiteChequeEspecial = limite; }

    public double getTarifaMensal() { return tarifaMensal; }
    public void setTarifaMensal(double tarifaMensal) { this.tarifaMensal = tarifaMensal; }

    @Override
    public void sacar(double valor) throws ValorInvalidoException, SaldoInsuficienteException, ContaBloqueadaException {
        if (!isAtiva()) {
            throw new ContaBloqueadaException("Conta está bloqueada.");
        }
        if (valor <= 0) {
            throw new ValorInvalidoException("O valor do saque deve ser maior que zero.");
        }
        if (saldo + limiteChequeEspecial < valor) {
            throw new SaldoInsuficienteException("Saldo e limite de cheque especial insuficientes.", saldo);
        }
        this.saldo -= valor;
    }

    @Override
    public void debitarTarifaMensal() throws SaldoInsuficienteException, ValorInvalidoException, ContaBloqueadaException {
        this.sacar(tarifaMensal);
    }

    @Override
    public void cobrarTarifaMensal() throws SaldoInsuficienteException, ValorInvalidoException, ContaBloqueadaException {
        this.sacar(tarifaMensal);
    }
}
