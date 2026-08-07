package banco;

import cliente.Cliente;
import conta.Conta;

import java.util.ArrayList;
import java.util.List;

public class Banco {
    private String nome;
    private List<Cliente> clientes;
    private List<Conta> contas;

    public Banco(String nome) {
        this.nome = nome;
        this.clientes = new ArrayList<>();
        this.contas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Conta> getContas() {
        return contas;
    }

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

    public Cliente buscarClientePorCpf(String cpf) {
        if (cpf == null) return null;
        for (Cliente cliente : clientes) {
            if (cliente.getCpf() != null && cliente.getCpf().equalsIgnoreCase(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    public Conta buscarConta(String numero) {
        if (numero == null) return null;
        for (Conta conta : contas) {
            if (conta.getNumero() != null && conta.getNumero().equalsIgnoreCase(numero)) {
                return conta;
            }
        }
        return null;
    }
}
