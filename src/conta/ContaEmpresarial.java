package conta;

import cliente.Cliente;

public class ContaEmpresarial extends Conta {
    private double tarifaOperacional;

    public ContaEmpresarial(String numero, Cliente cliente, double tarifaOperacional) {
        super(numero, cliente);
        this.tarifaOperacional = tarifaOperacional;
    }

    public ContaEmpresarial(String numero, Cliente cliente) {
        this(numero, cliente, 10.0);
    }

    public double getTarifaOperacional() { return tarifaOperacional; }

    public void cobrarTarifaOperacional() {
        this.saldo -= tarifaOperacional;
    }
}
