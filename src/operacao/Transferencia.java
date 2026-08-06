package operacao;

import conta.Conta;
import excecao.ContaBloqueadaException;
import excecao.SaldoInsuficienteException;
import excecao.ValorInvalidoException;

/**
 * Implementação da operação de Transferência entre contas.
 */
public class Transferencia extends Operacao {
    private Conta contaOrigem;
    private Conta contaDestino;

    public Transferencia(Conta contaOrigem, Conta contaDestino, double valor) {
        super(valor, "Transferência entre contas");
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
    }

    @Override
    public void executar() throws SaldoInsuficienteException, ContaBloqueadaException, ValorInvalidoException {
        contaOrigem.transferir(contaDestino, valor);
        contaOrigem.adicionarOperacao(this);
        contaDestino.adicionarOperacao(this);
    }

    public Conta getContaOrigem() { return contaOrigem; }
    public Conta getContaDestino() { return contaDestino; }
}
