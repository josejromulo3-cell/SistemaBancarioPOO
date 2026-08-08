package gerente;

import interfaces.Autenticavel;
import pessoa.Pessoa;

public class Gerente extends Pessoa implements Autenticavel {
    private String matricula;
    private String senha;

    public Gerente(String nome, String cpf, String email, String telefone, String matricula) {
        super(nome, cpf, email, telefone);
        this.matricula = matricula;
        this.senha = "admin123";
    }

    public String getMatricula() { return matricula; }

    @Override
    public boolean autenticar(String senha) {
        return this.senha != null && this.senha.equals(senha);
    }
}
