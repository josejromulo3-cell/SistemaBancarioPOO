package conta;

import cliente.Cliente;

public class ContaEmpresarial extends Conta {
    private double tarifaOperacional;

    public ContaEmpresarial(String numero, Cliente cliente) {
        super(numero, cliente);
        this.tarifaOperacional = 10.0;
    }

    public double getTarifaOperacional() { return tarifaOperacional; }

    // Remova a linha @Override se ela estiver acima desse método
    public void cobrarTarifaOperacional() {
        this.saldo -= tarifaOperacional;
    }
}
