package interfaces;

import conta.Conta;
import excecao.SaldoInsuficienteException;
import excecao.ContaBloqueadaException;
import excecao.ValorInvalidoException;

/**
 * Interface que define o contrato para entidades transferíveis.
 * 
 * Fonte: dio-Banco-Digital (https://github.com/Manelima/dio-Banco-Digital)
 */
public interface Transferivel {
    void transferir(Conta destino, double valor) 
            throws SaldoInsuficienteException, ContaBloqueadaException, ValorInvalidoException;
}
