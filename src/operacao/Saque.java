package operacao;

import conta.Conta;
import excecao.ContaBloqueadaException;
import excecao.SaldoInsuficienteException;
import excecao.ValorInvalidoException;

public class Saque extends Operacao {
    private Conta contaOrigem;

    public Saque(Conta contaOrigem, double valor) {
        super(valor, "Saque em conta");
        this.contaOrigem = contaOrigem;
    }

    @Override
    public void executar() throws SaldoInsuficienteException, ContaBloqueadaException, ValorInvalidoException {
        contaOrigem.sacar(valor);
        contaOrigem.adicionarOperacao(this);
    }

    public Conta getContaOrigem() { return contaOrigem; }
}
