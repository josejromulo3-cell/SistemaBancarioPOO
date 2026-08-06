package conta;

import cliente.Cliente;
import excecao.*;

/**
 * Subclasse de Conta para universitários com teto de movimentação.
 */
public class ContaUniversitaria extends Conta {
    private double limiteMaximoMovimentacaoMensal;
    private double totalMovimentadoMes;

    public ContaUniversitaria(String agencia, Cliente cliente) {
        super(agencia, cliente);
        this.limiteMaximoMovimentacaoMensal = 2000.0;
        this.totalMovimentadoMes = 0.0;
    }

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException, ContaBloqueadaException, ValorInvalidoException {
        if (totalMovimentadoMes + valor > limiteMaximoMovimentacaoMensal) {
            throw new ValorInvalidoException("Operação excede o limite máximo de movimentação mensal.");
        }
        super.sacar(valor);
        totalMovimentadoMes += valor;
    }

    @Override
    public void aplicarTarifaMensal() {
        this.totalMovimentadoMes = 0.0; // Reseta o acumulador mensal
    }
}
