package cartao;

import cliente.Cliente;
import conta.Conta;

public class CartaoCredito extends Cartao {
    private double limite;

    public CartaoCredito(Cliente cliente, Conta conta, String numero) {
        super(cliente, conta, numero);
        this.limite = 1000.0;
    }

    public CartaoCredito(Cliente cliente, String numero, String validade, String cvv, double limite) {
        super(numero, validade, cvv, cliente);
        this.limite = limite;
    }

    public CartaoCredito(String numero, String validade, String cvv, double limite) {
        super(numero, validade, cvv, null);
        this.limite = limite;
    }

    public double getLimite() { return limite; }
}
