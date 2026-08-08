package operacao;

import conta.Conta;
import excecao.ContaBloqueadaException;
import excecao.ValorInvalidoException;

public class Deposito extends Operacao {
    private Conta contaDestino;

    public Deposito(Conta contaDestino, double valor) {
        super(valor, "Depósito em conta");
        this.contaDestino = contaDestino;
    }

    @Override
    public void executar() throws ValorInvalidoException, ContaBloqueadaException {
        contaDestino.depositar(valor);
        contaDestino.adicionarOperacao(this);
    }

    public Conta getContaDestino() { return contaDestino; }
}
