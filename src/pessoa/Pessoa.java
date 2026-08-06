package pessoa;

/**
 * Classe abstrata base para representação de pessoas no sistema.
 * Aplica encapsulamento e serve como superclasse para Cliente e Gerente.
 * 
 * Fonte: Bank Program - Inheritance and Abstraction
 * (https://github.com/Jon-Peppinck/javaBankInheritAbstract)
 */
public abstract class Pessoa {
    private String nome;
    private String cpf;
    private String email;
    private String telefone;

    // Construtor parametrizado
    public Pessoa(String nome, String cpf, String email, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
    }

    // Getters e Setters (Encapsulamento)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
