package cartao;

import cliente.Cliente;
import conta.Conta;

/**
 * Cartão Virtual temporário/dinâmico para transações online.
 */
public class CartaoVirtual extends Cartao {
    private boolean temporario;

    public CartaoVirtual(Cliente cliente, Conta contaVinculada, String senha) {
        super(cliente, contaVinculada, senha);
        this.temporario = true;
    }

    public void regerarDados() {
        this.numero = util.GeradorCartao.gerarNumero();
        this.cvv = util.GeradorCartao.gerarCvv();
        this.validade = util.GeradorCartao.gerarValidade();
    }

    public boolean isTemporario() { return temporario; }
}
