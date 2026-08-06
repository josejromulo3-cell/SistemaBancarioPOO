package cartao;

/**
 * Representa a fatura do cartão de crédito.
 */
public class Fatura {
    private double valorTotal;
    private boolean paga;

    public Fatura() {
        this.valorTotal = 0.0;
        this.paga = false;
    }

    public void adicionarDespesa(double valor) {
        this.valorTotal += valor;
        this.paga = false;
    }

    public void quitar() {
        this.valorTotal = 0.0;
        this.paga = true;
    }

    public double getValorTotal() { return valorTotal; }
    public boolean isPaga() { return paga; }
}
