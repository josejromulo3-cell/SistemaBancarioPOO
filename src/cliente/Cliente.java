package cliente;

import pessoa.Pessoa;
import conta.Conta;
import cartao.Cartao;
import pix.ChavePix;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa {
    private String senha;
    private List<Conta> contas;
    private List<Cartao> cartoes;
    private List<ChavePix> chavesPix;

    public Cliente(String nome, String cpf, String email, String telefone, String senha) {
        super(nome, cpf, email, telefone);
        this.senha = senha;
        this.contas = new ArrayList<>();
        this.cartoes = new ArrayList<>();
        this.chavesPix = new ArrayList<>();
    }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public List<Conta> getContas() { return contas; }
    public void adicionarConta(Conta conta) {
        if (conta != null && !contas.contains(conta)) contas.add(conta);
    }

    public List<Cartao> getCartoes() { return cartoes; }
    public void adicionarCartao(Cartao cartao) {
        if (cartao != null && !cartoes.contains(cartao)) cartoes.add(cartao);
    }

    public List<ChavePix> getChavesPix() { return chavesPix; }
    public void adicionarChavePix(ChavePix chave) {
        if (chave != null && !chavesPix.contains(chave)) chavesPix.add(chave);
    }
}
