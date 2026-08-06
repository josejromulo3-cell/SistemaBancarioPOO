package main;

import banco.Banco;
import cliente.Cliente;
import conta.Conta;
import conta.ContaCorrente;
import conta.ContaPoupanca;
import gerente.Gerente;
import operacao.Deposito;
import operacao.Saque;
import operacao.Transferencia;

import java.util.Scanner;

/**
 * Gerenciador da interface de menu interativo via terminal.
 */
public class SistemaBancario {
    private Banco banco;
    private Scanner scanner;

    public SistemaBancario() {
        this.banco = new Banco("Banco POO");
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcao = -1;
        do {
            exibirMenu();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                processarOpcao(opcao);
            } catch (NumberFormatException e) {
                System.out.println("\n[!] Opção inválida. Digite um número inteiro.");
            }
        } while (opcao != 0);
    }

    private void exibirMenu() {
        System.out.println("\n=== Sistema Bancário POO ===");
        System.out.println("1. Cadastrar Cliente");
        System.out.println("2. Criar Conta");
        System.out.println("3. Realizar Depósito");
        System.out.println("4. Realizar Saque");
        System.out.println("5. Realizar Transferência");
        System.out.println("6. Consultar Saldo/Extrato");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> cadastrarCliente();
            case 2 -> criarConta();
            case 3 -> realizarDeposito();
            case 4 -> realizarSaque();
            case 5 -> realizarTransferencia();
            case 6 -> consultarSaldo();
            case 0 -> System.out.println("\nEncerrando o Sistema Bancário... Até logo!");
            default -> System.out.println("\n[!] Opção inexistente.");
        }
    }

    private void cadastrarCliente() {
        System.out.println("\n--- Cadastro de Cliente ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Cliente cliente = new Cliente(nome, cpf, email, telefone, senha);
        banco.adicionarCliente(cliente);
        System.out.println("[✓] Cliente cadastrado com sucesso!");
    }

    private void criarConta() {
        System.out.println("\n--- Criar Conta ---");
        System.out.print("CPF do Cliente: ");
        String cpf = scanner.nextLine();
        Cliente cliente = banco.buscarClientePorCpf(cpf);

        if (cliente == null) {
            System.out.println("[!] Cliente não encontrado.");
            return;
        }

        System.out.print("Número da Conta: ");
        String numero = scanner.nextLine();
        System.out.print("Agência: ");
        String agencia = scanner.nextLine();
        System.out.println("Tipo: 1-Corrente | 2-Poupança");
        String tipo = scanner.nextLine();

        Conta conta;
        if ("1".equals(tipo)) {
            conta = new ContaCorrente(numero, agencia, cliente, 500.0); // R$ 500 de limite padrão
        } else {
            conta = new ContaPoupanca(numero, agencia, cliente, 0.005); // 0.5% rendimento
        }

        banco.adicionarConta(conta);
        cliente.adicionarConta(conta);
        System.out.println("[✓] Conta criada com sucesso!");
    }

    private void realizarDeposito() {
        System.out.print("\nNúmero da Conta: ");
        String numero = scanner.nextLine();
        Conta conta = banco.buscarContaPorNumero(numero);

        if (conta != null) {
            System.out.print("Valor do Depósito: R$ ");
            double valor = Double.parseDouble(scanner.nextLine());
            try {
                new Deposito(conta, valor).executar();
                System.out.println("[✓] Depósito realizado!");
            } catch (Exception e) {
                System.out.println("[!] Erro: " + e.getMessage());
            }
        } else {
            System.out.println("[!] Conta não encontrada.");
        }
    }

    private void realizarSaque() {
        System.out.print("\nNúmero da Conta: ");
        String numero = scanner.nextLine();
        Conta conta = banco.buscarContaPorNumero(numero);

        if (conta != null) {
            System.out.print("Valor do Saque: R$ ");
            double valor = Double.parseDouble(scanner.nextLine());
            try {
                new Saque(conta, valor).executar();
                System.out.println("[✓] Saque realizado!");
            } catch (Exception e) {
                System.out.println("[!] Erro: " + e.getMessage());
            }
        } else {
            System.out.println("[!] Conta não encontrada.");
        }
    }

    private void realizarTransferencia() {
        System.out.print("\nConta Origem: ");
        Conta origem = banco.buscarContaPorNumero(scanner.nextLine());
        System.out.print("Conta Destino: ");
        Conta destino = banco.buscarContaPorNumero(scanner.nextLine());

        if (origem != null && destino != null) {
            System.out.print("Valor da Transferência: R$ ");
            double valor = Double.parseDouble(scanner.nextLine());
            try {
                new Transferencia(origem, destino, valor).executar();
                System.out.println("[✓] Transferência realizada!");
            } catch (Exception e) {
                System.out.println("[!] Erro: " + e.getMessage());
            }
        } else {
            System.out.println("[!] Conta de origem ou destino não encontrada.");
        }
    }

    private void consultarSaldo() {
        System.out.print("\nNúmero da Conta: ");
        Conta conta = banco.buscarContaPorNumero(scanner.nextLine());

        if (conta != null) {
            System.out.println("\n--- Extrato da Conta ---");
            System.out.println("Titular: " + conta.getCliente().getNome());
            System.out.println("Saldo Atual: R$ " + String.format("%.2f", conta.getSaldo()));
        } else {
            System.out.println("[!] Conta não encontrada.");
        }
    }
}
