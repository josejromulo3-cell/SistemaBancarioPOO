package cliente;

import pessoa.Pessoa;
import conta.Conta;
import cartao.Cartao;
import pix.ChavePix;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um Cliente do banco.
 * Aplica Herança (extends Pessoa) e Composição através de Collections (List).
 * 
 * Fonte: DIO - Banco Digital (https://github.com/Manelima/dio-Banco-Digital)
 */
public class Cliente extends Pessoa {
    private String senha;
    private List<Conta> contas;
    private List<Cartao> cartoes;
    private List<ChavePix> chavesPix;

    // Construtor
    public Cliente(String nome, String cpf, String email, String telefone, String senha) {
        // Chamada obrigatória ao construtor da superclasse Pessoa
        super(nome, cpf, email, telefone); 
        this.senha = senha;
        
        // Inicialização de ArrayLists vazias para evitar NullPointerException
        this.contas = new ArrayList<>();
        this.cartoes = new ArrayList<>();
        this.chavesPix = new ArrayList<>();
    }

    // Método de autenticação simples de senha
    public boolean verificarSenha(String senha) {
        return this.senha != null && this.senha.equals(senha);
    }

    // Gerenciamento das listas (Contas)
    public List<Conta> getContas() {
        return contas;
    }

    public void adicionarConta(Conta conta) {
        if (conta != null) {
            this.contas.add(conta);
        }
    }

    // Gerenciamento das listas (Cartões)
    public List<Cartao> getCartoes() {
        return cartoes;
    }

    public void adicionarCartao(Cartao cartao) {
        if (cartao != null) {
            this.cartoes.add(cartao);
        }
    }

    // Gerenciamento das listas (Pix)
    public List<ChavePix> getChavesPix() {
        return chavesPix;
    }

    public void adicionarChavePix(ChavePix chavePix) {
        if (chavePix != null) {
            this.chavesPix.add(chavePix);
        }
    }

    public void removerChavePix(String chave) {
        this.chavesPix.removeIf(p -> p.getChave().equals(chave));
    }

    // Getter e Setter da senha
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
