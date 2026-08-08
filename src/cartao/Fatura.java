package cartao;

import interfaces.Pagavel;
import java.util.ArrayList;
import java.util.List;

public class Fatura implements Pagavel {
    private double valorTotal;
    private boolean paga;
    private List<String> itens;

    public Fatura() {
        this.valorTotal = 0.0;
        this.paga = false;
        this.itens = new ArrayList<>();
    }

    public void adicionarLancamento(String descricao, double valor) {
        itens.add(descricao + " - R$ " + String.format("%.2f", valor));
        valorTotal += valor;
        paga = false;
    }

    @Override
    public double getValorAPagar() {
        return valorTotal;
    }

    @Override
    public void processarPagamento() {
        this.paga = true;
        this.valorTotal = 0.0;
    }

    public boolean isPaga() {
        return paga;
    }

    public List<String> getItens() {
        return itens;
    }
}
