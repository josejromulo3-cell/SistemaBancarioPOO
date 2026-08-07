package conta;

import cliente.Cliente;

public class ContaSalario extends Conta {
    private int saquesGratuitosRestantes;

    public ContaSalario(String numero, Cliente cliente, int saquesGratuitos) {
        super(numero, cliente);
        this.saquesGratuitosRestantes = saquesGratuitos;
    }

    public ContaSalario(String numero, Cliente cliente) {
        this(numero, cliente, 3);
    }

    public int getSaquesGratuitosRestantes() { return saquesGratuitosRestantes; }
}
