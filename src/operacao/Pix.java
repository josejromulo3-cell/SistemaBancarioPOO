package operacao;

import conta.Conta;
import excecao.ContaBloqueadaException;
import excecao.SaldoInsuficienteException;
import excecao.ValorInvalidoException;
import pix.ChavePix;

public class Pix extends Operacao {
    private Conta contaOrigem;
    private Conta contaDestino;
    private String chaveDestino;

    public Pix(Conta contaOrigem, Conta contaDestino, double valor, String chaveDestino) {
        super(valor, "PIX");
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.chaveDestino = chaveDestino;
    }

    public Pix(Conta contaOrigem, ChavePix chave, double valor) {
        super(valor, "PIX");
        this.contaOrigem = contaOrigem;
        this.contaDestino = chave.getConta();
        this.chaveDestino = chave.getValor();
    }

    @Override
    public void executar() throws SaldoInsuficienteException, ValorInvalidoException, ContaBloqueadaException {
        if (valor <= 0) {
            throw new ValorInvalidoException("O valor do PIX deve ser maior que zero.");
        }
        if (contaOrigem.getSaldo() < valor) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar o PIX.", contaOrigem.getSaldo());
        }
        contaOrigem.sacar(valor);
        contaDestino.depositar(valor);
    }
}
