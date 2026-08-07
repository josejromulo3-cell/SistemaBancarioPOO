package cartao;

import cliente.Cliente;
import conta.Conta;

public class CartaoDebito extends Cartao {

    public CartaoDebito(Cliente cliente, Conta conta, String numero) {
        super(cliente, conta, numero);
    }

    public CartaoDebito(Cliente cliente, String numero, String validade, String cvv) {
        super(numero, validade, cvv, cliente);
    }

    public CartaoDebito(String numero, String validade, String cvv) {
        super(numero, validade, cvv, null);
    }
}
