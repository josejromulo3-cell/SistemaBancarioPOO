package main;

import banco.Banco;
import cliente.Cliente;
import conta.*;
import gerente.Gerente;
import operacao.*;
import pix.ChavePix;
import pix.TipoChavePix;
import cartao.*;
import emprestimo.Emprestimo;

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
        try { conta1.depositar(1500.0); } catch (Exception ignored) {}

        cliente1.adicionarConta(conta1);
        banco.adicionarCliente(cliente1);
        banco.adicionarConta(conta1);

        // Adiciona chave Pix padrão
        ChavePix chave1 = new ChavePix("romulo@email.com", TipoChavePix.EMAIL, conta1);
        cliente1.adicionarChavePix(chave1);
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
        System.out.println("2. Criar Conta (Corrente, Poupança, Salário, Empresa, Univ.)");
        System.out.println("3. Realizar Depósito");
        System.out.println("4. Realizar Saque");
        System.out.println("5. Realizar Transferência Tradicional");
        System.out.println("6. Cadastrar Chave Pix");
        System.out.println("7. Realizar Pix");
        System.out.println("8. Solicitar Cartão de Crédito/Débito");
        System.out.println("9. Simular / Solicitar Empréstimo");
        System.out.println("10. Consultar Saldo / Extrato Completo");
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
            case 6 -> cadastrarChavePix();
            case 7 -> realizarPix();
            case 8 -> solicitarCartao();
            case 9 -> solicitarEmprestimo();
            case 10 -> consultarSaldo();
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

        if (banco.buscarContaPorNumero(numero) != null) {
            System.out.println("[!] ERRO: Já existe uma conta cadastrada com este número!");
            return;
        }

        System.out.println("Escolha o tipo de conta:");
        System.out.println("1 - Conta Corrente");
        System.out.println("2 - Conta Poupança");
        System.out.println("3 - Conta Salário");
        System.out.println("4 - Conta Empresarial");
        System.out.println("5 - Conta Universitária");
        String tipo = scanner.nextLine().trim();

        Conta conta = switch (tipo) {
            case "2" -> new ContaPoupanca(numero, cliente);
            case "3" -> new ContaSalario(numero, cliente);
            case "4" -> new ContaEmpresarial(numero, cliente);
            case "5" -> new ContaUniversitaria(numero, cliente);
            default -> new ContaCorrente(numero, cliente);
        };

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
                System.out.println("[✓] Depósito realizado! Novo saldo: R$ " + String.format("%.2f", conta.getSaldo()));
            } catch (Exception e) {
                System.out.println("[!] Erro: " + e.getMessage());
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
                System.out.println("[✓] Saque realizado! Novo saldo: R$ " + String.format("%.2f", conta.getSaldo()));
            } catch (Exception e) {
                System.out.println("[!] Erro: " + e.getMessage());
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
                System.out.println("[✓] Transferência realizada com sucesso!");
            } catch (Exception e) {
                System.out.println("[!] Erro: " + e.getMessage());
            }
        } else {
            System.out.println("[!] Conta de origem ou destino não encontrada.");
        }
    }

    private void cadastrarChavePix() {
        System.out.println("\n--- Cadastrar Chave Pix ---");
        System.out.print("Número da Conta vinculada: ");
        Conta conta = banco.buscarContaPorNumero(scanner.nextLine().trim());

        if (conta == null) {
            System.out.println("[!] Conta não encontrada.");
            return;
        }

        System.out.println("Tipo de Chave: 1 - CPF | 2 - Email | 3 - Telefone");
        String t = scanner.nextLine().trim();
        TipoChavePix tipoEnum = switch (t) {
            case "2" -> TipoChavePix.EMAIL;
            case "3" -> TipoChavePix.TELEFONE;
            default -> TipoChavePix.CPF;
        };

        System.out.print("Informe o Valor da Chave Pix: ");
        String valorChave = scanner.nextLine().trim();

        if (banco.buscarContaPorChavePix(valorChave) != null) {
            System.out.println("[!] ERRO: Esta chave Pix já está cadastrada no sistema!");
            return;
        }

        ChavePix chave = new ChavePix(valorChave, tipoEnum, conta);
        conta.getCliente().adicionarChavePix(chave);
        System.out.println("[✓] Chave Pix '" + valorChave + "' cadastrada com sucesso!");
    }

    private void realizarPix() {
        System.out.println("\n--- Transferência PIX ---");
        System.out.print("Número da Conta Origem: ");
        Conta origem = banco.buscarContaPorNumero(scanner.nextLine().trim());

        if (origem == null) {
            System.out.println("[!] Conta de origem não encontrada.");
            return;
        }

        System.out.print("Informe a Chave Pix do Destinatário: ");
        String chave = scanner.nextLine().trim();
        Conta destino = banco.buscarContaPorChavePix(chave);

        if (destino == null) {
            System.out.println("[!] Nenhuma conta encontrada para a Chave Pix informada.");
            return;
        }

        if (origem.getNumero().equalsIgnoreCase(destino.getNumero())) {
            System.out.println("[!] ERRO: Não é possível fazer PIX para a própria conta!");
            return;
        }

        System.out.print("Valor do PIX: R$ ");
        try {
            double valor = Double.parseDouble(scanner.nextLine().trim());
            new Pix(origem, destino, valor, chave).executar();
            System.out.println("[✓] PIX de R$ " + String.format("%.2f", valor) + " enviado com sucesso para " + destino.getCliente().getNome() + "!");
        } catch (Exception e) {
            System.out.println("[!] Erro na operação PIX: " + e.getMessage());
        }
    }

    private void solicitarCartao() {
        System.out.println("\n--- Solicitar Cartão ---");
        System.out.print("CPF do Cliente: ");
        Cliente cliente = banco.buscarClientePorCpf(scanner.nextLine().trim());

        if (cliente == null) {
            System.out.println("[!] Cliente não encontrado.");
            return;
        }

        System.out.println("Tipo de Cartão: 1 - Débito | 2 - Crédito");
        String tipo = scanner.nextLine().trim();

        if ("2".equals(tipo)) {
            CartaoCredito cc = new CartaoCredito("4000 1234 5678 9010", "12/28", "123", 2000.0);
            cliente.adicionarCartao(cc);
            System.out.println("[✓] Cartão de Crédito aprovado com limite de R$ 2.000,00!");
        } else {
            CartaoDebito cd = new CartaoDebito("5000 1234 5678 9010", "12/28", "321");
            cliente.adicionarCartao(cd);
            System.out.println("[✓] Cartão de Débito gerado com sucesso!");
        }
    }

    private void solicitarEmprestimo() {
        System.out.println("\n--- Empréstimo Bancário ---");
        System.out.print("Número da Conta: ");
        Conta conta = banco.buscarContaPorNumero(scanner.nextLine().trim());

        if (conta == null) {
            System.out.println("[!] Conta não encontrada.");
            return;
        }

        System.out.print("Valor do Empréstimo Desejado: R$ ");
        try {
            double valor = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Quantidade de parcelas (1x a 48x): ");
            int parcelas = Integer.parseInt(scanner.nextLine().trim());

            Emprestimo emp = new Emprestimo(conta, valor, parcelas);
            emp.aprovar();
            conta.depositar(valor);

            System.out.println("[✓] Empréstimo de R$ " + String.format("%.2f", valor) + " APROVADO e creditado na conta!");
        } catch (Exception e) {
            System.out.println("[!] Erro na solicitação: " + e.getMessage());
        }
    }

    private void consultarSaldo() {
        System.out.print("\nNúmero da Conta: ");
        Conta conta = banco.buscarContaPorNumero(scanner.nextLine().trim());

        if (conta != null) {
            Cliente c = conta.getCliente();
            System.out.println("\n=================================");
            System.out.println("       EXTRATO E DADOS         ");
            System.out.println("=================================");
            System.out.println("Titular: " + c.getNome() + " (CPF: " + c.getCpf() + ")");
            System.out.println("Número da Conta: " + conta.getNumero() + " (" + conta.getClass().getSimpleName() + ")");
            System.out.println("Saldo Atual: R$ " + String.format("%.2f", conta.getSaldo()));
            
            if (c.getChavesPix() != null && !c.getChavesPix().isEmpty()) {
                System.out.println("\nChaves Pix do Cliente:");
                for (ChavePix cp : c.getChavesPix()) {
                    System.out.println(" - " + cp.getTipo() + ": " + cp.getValor());
                }
            }
            System.out.println("=================================");
        } else {
            System.out.println("[!] Conta não encontrada.");
        }
    }
}
