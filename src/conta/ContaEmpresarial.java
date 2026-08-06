package conta;

import cliente.Cliente;

/**
 * Subclasse de Conta para pessoas jurídicas/empresas.
 */
public class ContaEmpresarial extends Conta {
    private double limiteCredito;
    private double tarifaOperacional;

    public ContaEmpresarial(String agencia, Cliente cliente) {
        super(agencia, cliente);
        this.limiteCredito = 2000.0;
        this.tarifaOperacional = 30.0;
    }

    @Override
    public void aplicarTarifaMensal() {
        this.saldo -= tarifaOperacional;
    }

    public double getLimiteCredito() { return limiteCredito; }
}
