package excecao;

public class SaldoInsuficienteException extends Exception {
    private double saldoAtual;

    public SaldoInsuficienteException(String mensagem, double saldoAtual) {
        super(mensagem);
        this.saldoAtual = saldoAtual;
    }

    public double getSaldoAtual() {
        return saldoAtual;
    }
}
