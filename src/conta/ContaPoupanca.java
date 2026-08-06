package conta;

import cliente.Cliente;

public class ContaPoupanca extends Conta {
    private double taxaRendimento;

    public ContaPoupanca(String numero, Cliente cliente) {
        super(numero, cliente);
        this.taxaRendimento = 0.005;
    }

    // Remova o @Override daqui
    public void renderJuros() {
        this.saldo += this.saldo * taxaRendimento;
    }
}
