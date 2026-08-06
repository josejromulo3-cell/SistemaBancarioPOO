package conta;

import cliente.Cliente;
import excecao.*;

/**
 * Subclasse de Conta direcionada para salário, com limite mensal de saques.
 */
public class ContaSalario extends Conta {
    private int saquesPermitidos;
    private int saquesRealizados;

    public ContaSalario(String agencia, Cliente cliente) {
        super(agencia, cliente);
        this.saquesPermitidos = 2;
        this.saquesRealizados = 0;
    }

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException, ContaBloqueadaException, ValorInvalidoException {
        if (saquesRealizados >= saquesPermitidos) {
            throw new ValorInvalidoException("Limite mensal de saques gratuitos atingido.");
        }
        super.sacar(valor);
        saquesRealizados++;
    }

    @Override
    public void aplicarTarifaMensal() {
        this.saquesRealizados = 0; // Reseta a contagem de saques ao mês
    }
}
