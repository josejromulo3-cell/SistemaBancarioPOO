package cartao;

import cliente.Cliente;
import conta.Conta;
import excecao.SaldoInsuficienteException;
import excecao.ContaBloqueadaException;
import excecao.ValorInvalidoException;

/**
 * Cartão na modalidade Crédito.
 */
public class CartaoCredito extends Cartao {
    private double limiteTotal;
    private double limiteDisponivel;
    private Fatura faturaAtual;

    public CartaoCredito(Cliente cliente, Conta contaVinculada, String senha, double limiteTotal) {
        super(cliente, contaVinculada, senha);
        this.limiteTotal = limiteTotal;
        this.limiteDisponivel = limiteTotal;
        this.faturaAtual = new Fatura();
    }

    public void realizarCompra(double valor) throws ContaBloqueadaException, ValorInvalidoException, SaldoInsuficienteException {
        if (!statusAtivo) {
            throw new ContaBloqueadaException("Cartão de crédito bloqueado.");
        }
        if (valor <= 0) {
            throw new ValorInvalidoException("Valor da compra deve ser positivo.");
        }
        if (valor > limiteDisponivel) {
            throw new SaldoInsuficienteException("Limite de crédito insuficiente.", limiteDisponivel);
        }

        this.limiteDisponivel -= valor;
        this.faturaAtual.adicionarDespesa(valor);
    }

    public void pagarFatura() throws SaldoInsuficienteException, ContaBloqueadaException, ValorInvalidoException {
        double valorFatura = faturaAtual.getValorTotal();
        if (valorFatura > 0) {
            contaVinculada.sacar(valorFatura);
            limiteDisponivel += valorFatura;
            faturaAtual.quitar();
        }
    }

    public double getLimiteTotal() { return limiteTotal; }
    public double getLimiteDisponivel() { return limiteDisponivel; }
    public Fatura getFaturaAtual() { return faturaAtual; }
}
