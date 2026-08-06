package gerente;

import pessoa.Pessoa;
import emprestimo.Emprestimo;

/**
 * Representa um Gerente do banco.
 * Herda de Pessoa e adiciona atributos e responsabilidades de gestão.
 * 
 * Fonte: Loan Management System (https://github.com/KooWeiHao/loan-management-system)
 */
public class Gerente extends Pessoa {
    private String matricula;

    // Construtor
    public Gerente(String nome, String cpf, String email, String telefone, String matricula) {
        super(nome, cpf, email, telefone);
        this.matricula = matricula;
    }

    // Método de negócio para aprovação de empréstimo (será detalhado na Issue #7)
    public boolean aprovarEmprestimo(Emprestimo emprestimo) {
        if (emprestimo != null) {
            emprestimo.aprovar(this);
            return true;
        }
        return false;
    }

    // Getter e Setter
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
