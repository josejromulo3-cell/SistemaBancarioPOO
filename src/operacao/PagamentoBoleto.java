package operacao;

import conta.Conta;
import excecao.ContaBloqueadaException;
import excecao.SaldoInsuficienteException;
import excecao.ValorInvalidoException;

public class PagamentoBoleto extends Operacao {
    private Conta contaOrigem;
    private String codigoBarras;

    public PagamentoBoleto(Conta contaOrigem, String codigoBarras, double valor) {
        super(valor, "Pagamento de Boleto: " + codigoBarras);
        this.contaOrigem = contaOrigem;
        this.codigoBarras = codigoBarras;
    }

    @Override
    public void executar() throws SaldoInsuficienteException, ContaBloqueadaException, ValorInvalidoException {
        if (codigoBarras == null || codigoBarras.trim().isEmpty()) {
            throw new ValorInvalidoException("Código de barras inválido.");
        }
        contaOrigem.sacar(valor);
        contaOrigem.adicionarOperacao(this);
    }

    public Conta getContaOrigem() { return contaOrigem; }
    public String getCodigoBarras() { return codigoBarras; }
}
