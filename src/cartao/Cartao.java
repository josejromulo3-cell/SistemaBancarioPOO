package cartao;

import cliente.Cliente;
import conta.Conta;

public abstract class Cartao {
    protected String numero;
    protected String validade;
    protected String cvv;
    protected Cliente cliente;
    protected Conta conta;

    public Cartao(Cliente cliente, Conta conta, String numero) {
        this.cliente = cliente;
        this.conta = conta;
        this.numero = numero;
    }

    public Cartao(String numero, String validade, String cvv, Cliente cliente) {
        this.numero = numero;
        this.validade = validade;
        this.cvv = cvv;
        this.cliente = cliente;
    }

    public Cartao(String numero, String validade, String cvv, Cliente cliente, Conta conta) {
        this.numero = numero;
        this.validade = validade;
        this.cvv = cvv;
        this.cliente = cliente;
        this.conta = conta;
    }

    public String getNumero() { return numero; }
    public String getValidade() { return validade; }
    public String getCvv() { return cvv; }
    public Cliente getCliente() { return cliente; }
    public Conta getConta() { return conta; }
}
