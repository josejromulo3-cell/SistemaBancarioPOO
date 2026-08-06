package operacao;

import conta.Conta;
import pix.ChavePix;
import excecao.ContaBloqueadaException;
import excecao.SaldoInsuficienteException;
import excecao.ValorInvalidoException;

/**
 * Implementação da operação Pix utilizando Chave Pix.
 */
public class Pix extends Operacao {
    private Conta contaOrigem;
    private ChavePix chavePixDestino;

    public Pix(Conta contaOrigem, ChavePix chavePixDestino, double valor) {
        super(valor, "Transferência via PIX (" + chavePixDestino.getChave() + ")");
        this.contaOrigem = contaOrigem;
        this.chavePixDestino = chavePixDestino;
    }

    @Override
    public void executar() throws SaldoInsuficienteException, ContaBloqueadaException, ValorInvalidoException {
        Conta contaDestino = chavePixDestino.getConta();
        contaOrigem.transferir(contaDestino, valor);
        contaOrigem.adicionarOperacao(this);
        contaDestino.adicionarOperacao(this);
    }

    public Conta getContaOrigem() { return contaOrigem; }
    public ChavePix getChavePixDestino() { return chavePixDestino; }
}
