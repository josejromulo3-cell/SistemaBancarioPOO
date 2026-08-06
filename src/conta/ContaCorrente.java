package conta;

import cliente.Cliente;
import excecao.*;

/**
 * Subclasse de Conta com suporte a Cheque Especial e tarifa mensal.
 */
public class ContaCorrente extends Conta {
    private double limiteChequeEspecial;
    private double tarifaMensal;

    public ContaCorrente(String agencia, Cliente cliente) {
        super(agencia, cliente);
        this.limiteChequeEspecial = 500.0;
        this.tarifaMensal = 15.0;
    }

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException, ContaBloqueadaException, ValorInvalidoException {
        if (!statusAtivo) {
            throw new ContaBloqueadaException("Conta bloqueada.");
        }
        if (valor <= 0) {
            throw new ValorInvalidoException("Valor de saque inválido.");
        }
        if (saldo + limiteChequeEspecial < valor) {
            throw new SaldoInsuficienteException("Saldo e limite de cheque especial insuficientes.", saldo);
        }
        this.saldo -= valor;
    }

    @Override
    public void aplicarTarifaMensal() {
        this.saldo -= tarifaMensal;
    }

    public double getLimiteChequeEspecial() { return limiteChequeEspecial; }
    public void setLimiteChequeEspecial(double limite) { this.limiteChequeEspecial = limite; }
}
