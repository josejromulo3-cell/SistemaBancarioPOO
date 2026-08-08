package cliente;

import cartao.Cartao;
import conta.Conta;
import interfaces.Autenticavel;
import pessoa.Pessoa;
import pix.ChavePix;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa implements Autenticavel {
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

    @Override
    public boolean autenticar(String senha) {
        return this.senha != null && this.senha.equals(senha);
    }

    public List<Conta> getContas() { return contas; }
    public void adicionarConta(Conta conta) { this.contas.add(conta); }

    public List<Cartao> getCartoes() { return cartoes; }
    public void adicionarCartao(Cartao cartao) { this.cartoes.add(cartao); }

    public List<ChavePix> getChavesPix() { return chavesPix; }
    public void adicionarChavePix(ChavePix chave) { this.chavesPix.add(chave); }
}
