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

public class SistemaBancario {
    private Banco banco;
    private Scanner scanner;

    public SistemaBancario() {
        this.banco = new Banco("Banco POO");
        this.scanner = new Scanner(System.in);
        carregarDadosIniciais();
    }

    private void carregarDadosIniciais() {
        Gerente gerente = new Gerente("Carlos Gerente", "000.000.000-00", "carlos@banco.com", "(83) 99999-0000", "GER-101");
        banco.adicionarGerente(gerente);

        Cliente cliente1 = new Cliente("José Rômulo", "111.111.111-11", "romulo@email.com", "(83) 98888-1111", "1234");
        Conta conta1 = new ContaCorrente("1001-1", cliente1);
        
        try {
            conta1.depositar(1500.0);
        } catch (Exception ignored) {}

        cliente1.adicionarConta(conta1);
        banco.adicionarCliente(cliente1);
        banco.adicionarConta(conta1);

        Cliente cliente2 = new Cliente("Mariana", "222.222.222-22", "mariana@email.com", "(83) 98888-2222", "4321");
        Conta conta2 = new ContaPoupanca("2002-2", cliente2);
        
        try {
            conta2.depositar(2500.0);
        } catch (Exception ignored) {}

        cliente2.adicionarConta(conta2);
        banco.adicionarCliente(cliente2);
        banco.adicionarConta(conta2);
    }

    public void iniciar() {
        int opcao = -1;
        do {
            exibirMenu();
            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
                processarOpcao(opcao);
            } catch (NumberFormatException e) {
                System.out.println("\n[!] Opção inválida. Digite um número inteiro.");
            }
        } while (opcao != 0);
    }

    private void exibirMenu() {
        System.out.println("\n=================================");
        System.out.println("      SISTEMA BANCÁRIO POO       ");
        System.out.println("=================================");
        System.out.println("1. Cadastrar Cliente");
        System.out.println("2. Criar Conta");
        System.out.println("3. Realizar Depósito");
        System.out.println("4. Realizar Saque");
        System.out.println("5. Realizar Transferência");
        System.out.println("6. Consultar Saldo / Extrato");
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
        String nome = scanner.nextLine().trim();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine().trim();

        // Validação no Menu
        if (banco.buscarClientePorCpf(cpf) != null) {
            System.out.println("[!] ERRO: Já existe um cliente cadastrado com este CPF!");
            return;
        }

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine().trim();
        System.out.print("Senha: ");
        String senha = scanner.nextLine().trim();

        Cliente cliente = new Cliente(nome, cpf, email, telefone, senha);
        banco.adicionarCliente(cliente);
        System.out.println("[✓] Cliente " + nome + " cadastrado com sucesso!");
    }

    private void criarConta() {
        System.out.println("\n--- Criar Conta ---");
        System.out.print("CPF do Cliente: ");
        String cpf = scanner.nextLine().trim();
        Cliente cliente = banco.buscarClientePorCpf(cpf);

        if (cliente == null) {
            System.out.println("[!] Cliente não encontrado com o CPF fornecido.");
            return;
        }

        System.out.print("Número da Conta: ");
        String numero = scanner.nextLine().trim();

        // Validação no Menu
        if (banco.buscarContaPorNumero(numero) != null) {
            System.out.println("[!] ERRO: Já existe uma conta cadastrada com este número!");
            return;
        }

        System.out.println("Tipo: 1 - Conta Corrente | 2 - Conta Poupança");
        String tipo = scanner.nextLine().trim();

        Conta conta;
        if ("1".equals(tipo)) {
            conta = new ContaCorrente(numero, cliente);
        } else {
            conta = new ContaPoupanca(numero, cliente);
        }

        banco.adicionarConta(conta);
        cliente.adicionarConta(conta);
        System.out.println("[✓] Conta " + numero + " criada com sucesso para " + cliente.getNome() + "!");
    }

    private void realizarDeposito() {
        System.out.print("\nNúmero da Conta: ");
        String numero = scanner.nextLine().trim();
        Conta conta = banco.buscarContaPorNumero(numero);

        if (conta != null) {
            System.out.print("Valor do Depósito: R$ ");
            try {
                double valor = Double.parseDouble(scanner.nextLine().trim());
                new Deposito(conta, valor).executar();
                System.out.println("[✓] Depósito realizado com sucesso! Novo saldo: R$ " + String.format("%.2f", conta.getSaldo()));
            } catch (NumberFormatException e) {
                System.out.println("[!] Valor inválido. Digite um número decimal válido.");
            } catch (Exception e) {
                System.out.println("[!] Erro no depósito: " + e.getMessage());
            }
        } else {
            System.out.println("[!] Conta não encontrada.");
        }
    }

    private void realizarSaque() {
        System.out.print("\nNúmero da Conta: ");
        String numero = scanner.nextLine().trim();
        Conta conta = banco.buscarContaPorNumero(numero);

        if (conta != null) {
            System.out.print("Valor do Saque: R$ ");
            try {
                double valor = Double.parseDouble(scanner.nextLine().trim());
                new Saque(conta, valor).executar();
                System.out.println("[✓] Saque realizado com sucesso! Novo saldo: R$ " + String.format("%.2f", conta.getSaldo()));
            } catch (NumberFormatException e) {
                System.out.println("[!] Valor inválido. Digite um número decimal válido.");
            } catch (Exception e) {
                System.out.println("[!] Erro no saque: " + e.getMessage());
            }
        } else {
            System.out.println("[!] Conta não encontrada.");
        }
    }

    private void realizarTransferencia() {
        System.out.print("\nConta Origem: ");
        Conta origem = banco.buscarContaPorNumero(scanner.nextLine().trim());
        System.out.print("Conta Destino: ");
        Conta destino = banco.buscarContaPorNumero(scanner.nextLine().trim());

        if (origem != null && destino != null) {
            System.out.print("Valor da Transferência: R$ ");
            try {
                double valor = Double.parseDouble(scanner.nextLine().trim());
                new Transferencia(origem, destino, valor).executar();
                System.out.println("[✓] Transferência de R$ " + valor + " realizada com sucesso!");
            } catch (NumberFormatException e) {
                System.out.println("[!] Valor inválido. Digite um número decimal válido.");
            } catch (Exception e) {
                System.out.println("[!] Erro na transferência: " + e.getMessage());
            }
        } else {
            System.out.println("[!] Conta de origem ou destino não encontrada.");
        }
    }

    private void consultarSaldo() {
        System.out.print("\nNúmero da Conta: ");
        Conta conta = banco.buscarContaPorNumero(scanner.nextLine().trim());

        if (conta != null) {
            System.out.println("\n--- Extrato da Conta " + conta.getNumero() + " ---");
            System.out.println("Titular: " + conta.getCliente().getNome());
            System.out.println("Saldo Atual: R$ " + String.format("%.2f", conta.getSaldo()));
        } else {
            System.out.println("[!] Conta não encontrada.");
        }
    }
}
