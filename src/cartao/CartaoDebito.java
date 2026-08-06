package cartao;

import cliente.Cliente;
import conta.Conta;
import excecao.SaldoInsuficienteException;
import excecao.ContaBloqueadaException;
import excecao.ValorInvalidoException;

/**
 * Cartão na modalidade Débito (debita direto do saldo da conta).
 */
public class CartaoDebito extends Cartao {

    public CartaoDebito(Cliente cliente, Conta contaVinculada, String senha) {
        super(cliente, contaVinculada, senha);
    }

    public void realizarCompra(double valor) throws SaldoInsuficienteException, ContaBloqueadaException, ValorInvalidoException {
        if (!statusAtivo) {
            throw new ContaBloqueadaException("Cartão de débito bloqueado.");
        }
        // O débito consome direto o saldo da conta associada
        contaVinculada.sacar(valor);
    }
}
