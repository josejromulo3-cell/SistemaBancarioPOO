package conta;

import cliente.Cliente;
import excecao.ContaBloqueadaException;
import excecao.SaldoInsuficienteException;
import excecao.ValorInvalidoException;

public class ContaCorrente extends Conta {
    private double limite;

    public ContaCorrente(String numero, Cliente cliente) {
        super(numero, cliente);
        this.limite = 500.0;
    }

    public ContaCorrente(String numero, Cliente cliente, double limite) {
        super(numero, cliente);
        this.limite = limite;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException, ValorInvalidoException, ContaBloqueadaException {
        if (isBloqueada() || !isAtiva()) {
            throw new ContaBloqueadaException("Conta inativa ou bloqueada.");
        }
        if (valor <= 0) {
            throw new ValorInvalidoException("O valor do saque deve ser maior que zero.");
        }
        if (saldo + limite < valor) {
            throw new SaldoInsuficienteException("Saldo e limite insuficientes para realizar o saque.", saldo + limite);
        }
        this.saldo -= valor;
    }
}
