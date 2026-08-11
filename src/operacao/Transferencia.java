package operacao;

import conta.Conta;
import excecao.ContaBloqueadaException;
import excecao.SaldoInsuficienteException;
import excecao.ValorInvalidoException;

public class Transferencia {
    private Conta origem;
    private Conta destino;
    private double valor;

    public Transferencia(Conta origem, Conta destino, double valor) {
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
    }

    public void executar() throws ValorInvalidoException, SaldoInsuficienteException, ContaBloqueadaException {
        if (origem == null || destino == null) {
            throw new ValorInvalidoException("Conta de origem ou destino inválida.");
        }

        
        if (origem.getNumero().trim().equalsIgnoreCase(destino.getNumero().trim())) {
            throw new ValorInvalidoException("A conta de destino não pode ser igual à conta de origem.");
        }

        origem.transferir(destino, valor);
    }
}
