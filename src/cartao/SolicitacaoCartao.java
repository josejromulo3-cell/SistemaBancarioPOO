package cartao;

import cliente.Cliente;
import conta.Conta;

public class SolicitacaoCartao {
    public enum StatusSolicitacao { PENDENTE, APROVADA, RECUSADA }

    private static int contadorId = 1;
    private int id;
    private Cliente cliente;
    private Conta conta;
    private double limiteSolicitado;
    private StatusSolicitacao status;

    public SolicitacaoCartao(Cliente cliente, Conta conta, double limiteSolicitado) {
        this.id = contadorId++;
        this.cliente = cliente;
        this.conta = conta;
        this.limiteSolicitado = limiteSolicitado;
        this.status = StatusSolicitacao.PENDENTE;
    }

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Conta getConta() { return conta; }
    public double getLimiteSolicitado() { return limiteSolicitado; }
    public StatusSolicitacao getStatus() { return status; }

    public void aprovar() {
        this.status = StatusSolicitacao.APROVADA;
        String numCartao = "4000 " + (1000 + (int)(Math.random()*9000)) + " " + (1000 + (int)(Math.random()*9000)) + " " + (1000 + (int)(Math.random()*9000));
        CartaoCredito cc = new CartaoCredito(cliente, conta, numCartao);
        cliente.adicionarCartao(cc);
    }

    public void recusar() {
        this.status = StatusSolicitacao.RECUSADA;
    }
}
