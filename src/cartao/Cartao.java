package cartao;

import cliente.Cliente;
import conta.Conta;
import util.GeradorCartao;

/**
 * Classe abstrata base para a hierarquia de Cartões.
 */
public abstract class Cartao {
    protected String numero;
    protected String nomeTitular;
    protected String validade;
    protected String cvv;
    protected String senha;
    protected boolean statusAtivo;
    protected Cliente cliente;
    protected Conta contaVinculada;

    public Cartao(Cliente cliente, Conta contaVinculada, String senha) {
        this.numero = GeradorCartao.gerarNumero();
        this.nomeTitular = cliente.getNome().toUpperCase();
        this.validade = GeradorCartao.gerarValidade();
        this.cvv = GeradorCartao.gerarCvv();
        this.senha = senha;
        this.statusAtivo = true;
        this.cliente = cliente;
        this.contaVinculada = contaVinculada;
    }

    public boolean validarSenha(String senha) {
        return this.senha != null && this.senha.equals(senha);
    }

    public void bloquear() {
        this.statusAtivo = false;
    }

    public void desbloquear() {
        this.statusAtivo = true;
    }

    // Getters e Setters
    public String getNumero() { return numero; }
    public String getNomeTitular() { return nomeTitular; }
    public String getValidade() { return validade; }
    public String getCvv() { return cvv; }
    public boolean isStatusAtivo() { return statusAtivo; }
    public Cliente getCliente() { return cliente; }
    public Conta getContaVinculada() { return contaVinculada; }
}
