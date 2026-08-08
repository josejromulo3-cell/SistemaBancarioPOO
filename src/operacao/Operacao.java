package operacao;

import java.time.LocalDateTime;


public abstract class Operacao {
    protected LocalDateTime dataHora;
    protected double valor;
    protected String descricao;

    public Operacao(double valor, String descricao) {
        this.dataHora = LocalDateTime.now();
        this.valor = valor;
        this.descricao = descricao;
    }

    // Método abstrato executado por cada tipo de operação
    public abstract void executar() throws Exception;

    public LocalDateTime getDataHora() { return dataHora; }
    public double getValor() { return valor; }
    public String getDescricao() { return descricao; }
}
