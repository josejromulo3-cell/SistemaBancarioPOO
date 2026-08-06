package banco;

import cliente.Cliente;
import conta.Conta;
import gerente.Gerente;
import emprestimo.Emprestimo;
import pix.ChavePix;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe principal de gerenciamento das entidades e operações do Banco.
 */
public class Banco {
    private String nome;
    private List<Cliente> clientes;
    private List<Conta> contas;
    private List<Gerente> gerentes;
    private List<Emprestimo> emprestimos;
    private List<ChavePix> chavesPix;

    public Banco(String nome) {
        this.nome = nome;
        this.clientes = new ArrayList<>();
        this.contas = new ArrayList<>();
        this.gerentes = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
        this.chavesPix = new ArrayList<>();
    }

    // --- MÉTODOS DE CADASTRO E ADIÇÃO ---

    public void adicionarCliente(Cliente cliente) {
        if (cliente != null) {
            this.clientes.add(cliente);
        }
    }

    public void adicionarConta(Conta conta) {
        if (conta != null) {
            this.contas.add(conta);
        }
    }

    public void adicionarGerente(Gerente gerente) {
        if (gerente != null) {
            this.gerentes.add(gerente);
        }
    }

    public void adicionarEmprestimo(Emprestimo emprestimo) {
        if (emprestimo != null) {
            this.emprestimos.add(emprestimo);
        }
    }

    public void cadastrarChavePix(ChavePix chavePix) {
        if (chavePix != null) {
            this.chavesPix.add(chavePix);
        }
    }

    // --- MÉTODOS DE BUSCA E PESQUISA ---

    public Cliente buscarClientePorCpf(String cpf) {
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                return c;
            }
        }
        return null;
    }

    public Conta buscarContaPorNumero(String numero) {
        for (Conta c : contas) {
            if (c.getNumero().equals(numero)) {
                return c;
            }
        }
        return null;
    }

    public ChavePix buscarChavePix(String chave) {
        for (ChavePix pix : chavesPix) {
            if (pix.getChave().equals(chave)) {
                return pix;
            }
        }
        return null;
    }

    public Gerente buscarGerentePorMatricula(String matricula) {
        for (Gerente g : gerentes) {
            if (g.getMatricula().equals(matricula)) {
                return g;
            }
        }
        return null;
    }

    // --- GETTERS ---

    public String getNome() { return nome; }
    public List<Cliente> getClientes() { return clientes; }
    public List<Conta> getContas() { return contas; }
    public List<Gerente> getGerentes() { return gerentes; }
    public List<Emprestimo> getEmprestimos() { return emprestimos; }
    public List<ChavePix> getChavesPix() { return chavesPix; }
}
