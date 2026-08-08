package interfaces;

import conta.Conta;
import excecao.ContaBloqueadaException;
import excecao.SaldoInsuficienteException;
import excecao.ValorInvalidoException;

public interface Transferivel {
    void transferir(Conta destino, double valor) throws SaldoInsuficienteException, ValorInvalidoException, ContaBloqueadaException;
}
