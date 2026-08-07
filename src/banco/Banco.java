package banco;

import cliente.Cliente;
import conta.Conta;
import gerente.Gerente;
import pix.ChavePix;

import java.util.ArrayList;
import java.util.List;

public class Banco {
    private String nome;
    private List<Cliente> clientes;
    private List<Conta> contas;
    private List<Gerente> gerentes;

    public Banco(String nome) {
        this.nome = nome;
        this.clientes = new ArrayList<>();
        this.contas = new ArrayList<>();
        this.gerentes = new ArrayList<>();
    }

    public String getNome() { return nome; }

    public void adicionarCliente(Cliente cliente) {
        if (cliente == null) return;
        if (buscarClientePorCpf(cliente.getCpf()) != null) {
            System.out.println("[!] ERRO: Já existe um cliente com o CPF: " + cliente.getCpf());
            return;
        }
        if (!clientes.contains(cliente)) clientes.add(cliente);
    }

    public void adicionarConta(Conta conta) {
        if (conta == null) return;
        if (buscarContaPorNumero(conta.getNumero()) != null) {
            System.out.println("[!] ERRO: Já existe uma conta com o número: " + conta.getNumero());
            return;
        }
        if (!contas.contains(conta)) contas.add(conta);
    }

    public void adicionarGerente(Gerente gerente) {
        if (gerente != null && !gerentes.contains(gerente)) gerentes.add(gerente);
    }

    public Cliente buscarClientePorCpf(String cpf) {
        if (cpf == null) return null;
        String cpfLimpo = cpf.trim().replaceAll("[^0-9]", "");
        for (Cliente cliente : clientes) {
            if (cliente.getCpf() != null) {
                String cCpfLimpo = cliente.getCpf().trim().replaceAll("[^0-9]", "");
                if (cCpfLimpo.equals(cpfLimpo) || cliente.getCpf().trim().equalsIgnoreCase(cpf.trim())) {
                    return cliente;
                }
            }
        }
        return null;
    }

    public Conta buscarContaPorNumero(String numero) {
        if (numero == null) return null;
        for (Conta conta : contas) {
            if (conta.getNumero() != null && conta.getNumero().trim().equalsIgnoreCase(numero.trim())) {
                return conta;
            }
        }
        return null;
    }

    // --- NOVO: Busca por Chave Pix ---
    public Conta buscarContaPorChavePix(String chave) {
        if (chave == null) return null;
        String chaveLimpa = chave.trim().toLowerCase();
        for (Cliente cliente : clientes) {
            if (cliente.getChavesPix() != null) {
                for (ChavePix cp : cliente.getChavesPix()) {
                    if (cp.getValor() != null && cp.getValor().trim().toLowerCase().equals(chaveLimpa)) {
                        return cp.getConta();
                    }
                }
            }
        }
        return null;
    }

    public List<Cliente> getClientes() { return clientes; }
    public List<Conta> getContas() { return contas; }
    public List<Gerente> getGerentes() { return gerentes; }
}
