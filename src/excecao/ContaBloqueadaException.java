package excecao;

public class ContaBloqueadaException extends Exception {
    public ContaBloqueadaException(String mensagem) {
        super(mensagem);
    }
}
