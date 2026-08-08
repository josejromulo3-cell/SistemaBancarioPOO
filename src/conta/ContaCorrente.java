package conta;

import cliente.Cliente;
import excecao.ContaBloqueadaException;
import excecao.SaldoInsuficienteException;
import excecao.ValorInvalidoException;
import interfaces.Tributavel;

public class ContaCorrente extends Conta implements Tributavel {
    private double limite;

    public ContaCorrente(String numero, Cliente cliente) {
        super(numero, cliente);
        this.limite = 500.0;
    }

    public double getLimite() { return limite; }

    @Override
    public double calculaTributo() {
       
        return this.saldo * 0.01;
    }

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException, ValorInvalidoException, ContaBloqueadaException {
        if (isBloqueada() || !isAtiva()) throw new ContaBloqueadaException("Conta inativa ou bloqueada.");
        if (valor <= 0) throw new ValorInvalidoException("Valor de saque inválido.");
        if (saldo + limite < valor) throw new SaldoInsuficienteException("Saldo e limite insuficientes.", saldo + limite);
        this.saldo -= valor;
    }
}
