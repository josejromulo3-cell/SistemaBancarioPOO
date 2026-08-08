package cartao;

import cliente.Cliente;
import conta.Conta;
import excecao.SaldoInsuficienteException;
import excecao.ValorInvalidoException;

public class CartaoCredito extends Cartao {
    private double limite;
    private double limiteDisponivel;
    private Fatura fatura;

    public CartaoCredito(Cliente cliente, Conta conta, String numero) {
        super(cliente, conta, numero);
        this.limite = 1000.0;
        this.limiteDisponivel = 1000.0;
        this.fatura = new Fatura();
    }

    public CartaoCredito(String numero, String validade, String cvv, double limite) {
        super(numero, validade, cvv, null);
        this.limite = limite;
        this.limiteDisponivel = limite;
        this.fatura = new Fatura();
    }

    public double getLimite() { return limite; }
    public double getLimiteDisponivel() { return limiteDisponivel; }
    public Fatura getFatura() { return fatura; }

    public void realizarCompra(String descricao, double valor) throws SaldoInsuficienteException, ValorInvalidoException {
        if (valor <= 0) throw new ValorInvalidoException("Valor inválido.");
        if (valor > limiteDisponivel) throw new SaldoInsuficienteException("Limite de crédito insuficiente no cartão.", limiteDisponivel);

        limiteDisponivel -= valor;
        fatura.adicionarLancamento("Compra: " + descricao, valor);
    }

    public void realizarPixNoCredito(String chaveDestino, double valor) throws SaldoInsuficienteException, ValorInvalidoException {
        if (valor <= 0) throw new ValorInvalidoException("Valor do Pix deve ser maior que zero.");
        double taxa = valor * 0.03; 
        double valorTotalPix = valor + taxa;

        if (valorTotalPix > limiteDisponivel) {
            throw new SaldoInsuficienteException("Limite insuficiente para Pix no Crédito (incluindo taxa de 3%).", limiteDisponivel);
        }

        limiteDisponivel -= valorTotalPix;
        fatura.adicionarLancamento("Pix no Crédito (Chave: " + chaveDestino + ") + Taxa", valorTotalPix);
    }
}
