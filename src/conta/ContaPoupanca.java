package conta;

import cliente.Cliente;

/**
 * Subclasse de Conta para poupança com taxas de juros/rendimento.
 */
public class ContaPoupanca extends Conta {
    private double taxaJuros;

    public ContaPoupanca(String agencia, Cliente cliente) {
        super(agencia, cliente);
        this.taxaJuros = 0.005; // 0.5% ao mês
    }

    public void calcularRendimento() {
        this.saldo += this.saldo * taxaJuros;
    }

    @Override
    public void aplicarTarifaMensal() {
        // Conta Poupança é isenta de tarifa mensal
    }

    public double getTaxaJuros() { return taxaJuros; }
}
