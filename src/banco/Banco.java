package banco;

import cliente.Cliente;
import conta.Conta;
import gerente.Gerente;

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

    public String getNome() {
        return nome;
    }

    public void adicionarCliente(Cliente cliente) {
        if (cliente == null) return;

        // Regra de Negócio: Impede cadastrar CPFs duplicados
        if (buscarClientePorCpf(cliente.getCpf()) != null) {
            System.out.println("[!] ERRO: Já existe um cliente cadastrado com o CPF: " + cliente.getCpf());
            return;
        }
        
        if (!clientes.contains(cliente)) {
            clientes.add(cliente);
        }
    }

    public void adicionarConta(Conta conta) {
        if (conta == null) return;

        // Regra de Negócio: Impede criar números de conta duplicados
        if (buscarContaPorNumero(conta.getNumero()) != null) {
            System.out.println("[!] ERRO: Já existe uma conta cadastrada com o número: " + conta.getNumero());
            return;
        }

        if (!contas.contains(conta)) {
            contas.add(conta);
        }
    }

    public void adicionarGerente(Gerente gerente) {
        if (gerente != null && !gerentes.contains(gerente)) {
            gerentes.add(gerente);
        }
    }

    public Cliente buscarClientePorCpf(String cpf) {
        if (cpf == null) return null;
        String cpfLimpo = cpf.trim().replaceAll("[^0-9]", "");

        for (Cliente cliente : clientes) {
            if (cliente.getCpf() != null) {
                String clienteCpfLimpo = cliente.getCpf().trim().replaceAll("[^0-9]", "");
                if (clienteCpfLimpo.equals(cpfLimpo) || cliente.getCpf().trim().equalsIgnoreCase(cpf.trim())) {
                    return cliente;
                }
            }
        }
        return null;
    }

    public Conta buscarContaPorNumero(String numero) {
        if (numero == null) return null;
        String numBuscado = numero.trim();

        for (Conta conta : contas) {
            if (conta.getNumero() != null && conta.getNumero().trim().equalsIgnoreCase(numBuscado)) {
                return conta;
            }
        }
        return null;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public List<Gerente> getGerentes() {
        return gerentes;
    }
}
