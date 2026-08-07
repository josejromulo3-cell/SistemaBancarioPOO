package conta;

import cliente.Cliente;

public class ContaUniversitaria extends Conta {
    private double limiteChequeEspecial;

    public ContaUniversitaria(String numero, Cliente cliente, double limite) {
        super(numero, cliente);
        this.limiteChequeEspecial = limite;
    }

    public ContaUniversitaria(String numero, Cliente cliente) {
        this(numero, cliente, 200.0);
    }

    public double getLimiteChequeEspecial() { return limiteChequeEspecial; }
}
