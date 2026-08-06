package cliente;

import conta.Conta;
import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String senha;
    private List<Conta> contas;

    public Cliente(String nome, String cpf, String email, String telefone, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.contas = new ArrayList<>();
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public List<Conta> getContas() { return contas; }

    public void adicionarConta(Conta conta) {
        if (conta != null) {
            Conta existente = buscarContaPorNumero(conta.getNumero());
            if (existente == null) {
                contas.add(conta);
            }
        }
    }

    public Conta buscarContaPorNumero(String numero) {
        if (numero == null) return null;
        String numBuscado = numero.trim();

        for (Conta conta : contas) {
            if (conta != null && conta.getNumero() != null) {
                if (conta.getNumero().trim().equalsIgnoreCase(numBuscado)) {
                    return conta;
                }
            }
        }
        return null;
    }
}
