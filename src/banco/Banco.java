package banco;

import cliente.Cliente;
import conta.Conta;
import emprestimo.Emprestimo;
import cartao.SolicitacaoCartao;
import pix.ChavePix;

import java.util.ArrayList;
import java.util.List;

public class Banco {
    private String nome;
    private List<Cliente> clientes;
    private List<Conta> contas;
    private List<Emprestimo> emprestimos;
    private List<SolicitacaoCartao> solicitacoesCartao;

    public Banco(String nome) {
        this.nome = nome;
        this.clientes = new ArrayList<>();
        this.contas = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
        this.solicitacoesCartao = new ArrayList<>();
    }

    public String getNome() { return nome; }
    public List<Cliente> getClientes() { return clientes; }
    public List<Conta> getContas() { return contas; }
    public List<Emprestimo> getEmprestimos() { return emprestimos; }
    public List<SolicitacaoCartao> getSolicitacoesCartao() { return solicitacoesCartao; }

    public void adicionarCliente(Cliente cliente) {
        if (cliente != null && !clientes.contains(cliente)) {
            clientes.add(cliente);
        }
    }

    public void adicionarConta(Conta conta) {
        if (conta != null && !contas.contains(conta)) {
            contas.add(conta);
        }
    }

    public void adicionarEmprestimo(Emprestimo emprestimo) {
        if (emprestimo != null) {
            emprestimos.add(emprestimo);
        }
    }

    public void adicionarSolicitacaoCartao(SolicitacaoCartao sol) {
        if (sol != null) {
            solicitacoesCartao.add(sol);
        }
    }

    public Cliente buscarClientePorCpf(String cpf) {
        if (cpf == null) return null;
        for (Cliente cliente : clientes) {
            if (cliente.getCpf() != null && cliente.getCpf().equalsIgnoreCase(cpf.trim())) {
                return cliente;
            }
        }
        return null;
    }

    public Conta buscarConta(String numero) {
        if (numero == null) return null;
        for (Conta conta : contas) {
            if (conta.getNumero() != null && conta.getNumero().equalsIgnoreCase(numero.trim())) {
                return conta;
            }
        }
        return null;
    }

    public ChavePix buscarChavePix(String chave) {
        if (chave == null) return null;
        for (Cliente cliente : clientes) {
            if (cliente.getChavesPix() != null) {
                for (ChavePix cp : cliente.getChavesPix()) {
                    if (cp.getValor() != null && cp.getValor().equalsIgnoreCase(chave.trim())) {
                        return cp;
                    }
                }
            }
        }
        return null;
    }
}
