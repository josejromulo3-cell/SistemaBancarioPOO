package emprestimo;

import conta.Conta;
import gerente.Gerente;

public class Emprestimo {
    public enum StatusEmprestimo { PENDENTE, APROVADO, RECUSADO }

    private static int contadorId = 1;
    private int id;
    private Conta conta;
    private double valor;
    private int parcelas;
    private StatusEmprestimo status;

    public Emprestimo(Conta conta, double valor, int parcelas) {
        this.id = contadorId++;
        this.conta = conta;
        this.valor = valor;
        this.parcelas = parcelas;
        this.status = StatusEmprestimo.PENDENTE;
    }

    public int getId() { return id; }
    public Conta getConta() { return conta; }
    public double getValor() { return valor; }
    public int getParcelas() { return parcelas; }
    public StatusEmprestimo getStatus() { return status; }

    public void aprovar(Gerente gerente) throws Exception {
        this.status = StatusEmprestimo.APROVADO;
        this.conta.depositar(this.valor);
    }

    public void recusar(Gerente gerente) {
        this.status = StatusEmprestimo.RECUSADO;
    }
}
