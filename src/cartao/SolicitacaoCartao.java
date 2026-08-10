package cartao;

import cliente.Cliente;
import conta.Conta;

public class SolicitacaoCartao {
    private static int contador = 1;
    private int id;
    private Cliente cliente;
    private Conta conta;
    private double limiteSolicitado;
    private boolean ehVirtual;
    private StatusSolicitacao status;

    public enum StatusSolicitacao {
        PENDENTE, APROVADO, RECUSADO
    }

    public SolicitacaoCartao(Cliente cliente, Conta conta, double limiteSolicitado, boolean ehVirtual) {
        this.id = contador++;
        this.cliente = cliente;
        this.conta = conta;
        this.limiteSolicitado = limiteSolicitado;
        this.ehVirtual = ehVirtual;
        this.status = StatusSolicitacao.PENDENTE;
    }

    public SolicitacaoCartao(Cliente cliente, Conta conta, double limiteSolicitado) {
        this(cliente, conta, limiteSolicitado, false);
    }

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Conta getConta() { return conta; }
    public double getLimiteSolicitado() { return limiteSolicitado; }
    public boolean isEhVirtual() { return ehVirtual; }
    public StatusSolicitacao getStatus() { return status; }

    public void aprovar() {
        this.status = StatusSolicitacao.APROVADO;
        if (ehVirtual) {
            CartaoVirtual cv = new CartaoVirtual(cliente, limiteSolicitado);
            cliente.adicionarCartao(cv);
        } else {
            // Ajustado para usar o construtor existente: (Cliente, Conta, String)
            CartaoCredito cc = new CartaoCredito(cliente, conta, String.valueOf(limiteSolicitado));
            cliente.adicionarCartao(cc);
        }
    }

    public void recusar() {
        this.status = StatusSolicitacao.RECUSADO;
    }
}
