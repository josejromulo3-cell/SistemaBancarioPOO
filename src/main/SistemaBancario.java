package main;

import banco.Banco;
import cliente.Cliente;
import conta.*;
import gerente.Gerente;
import pix.ChavePix;
import pix.TipoChavePix;
import operacao.*;
import cartao.*;
import emprestimo.Emprestimo;
import excecao.*;

import java.util.Scanner;

public class SistemaBancario {
    private Banco banco;
    private Scanner scanner;
    private Gerente gerentePadrao;

    public SistemaBancario() {
        this.banco = new Banco("Banco Central POO");
        this.scanner = new Scanner(System.in);
        // Gerente padrão do sistema para operações administrativas
        this.gerentePadrao = new Gerente("Carlos Andrade", "111.222.333-44", "gerente@bancopoo.com", "83 98888-7777", "GER-001");
    }

    public void iniciar() {
        int opcao = -1;
        while (opcao != 0) {
            exibirMenu();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
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
                    case 10 -> consultarExtrato();
                    case 11 -> areaGerente();
                    case 0 -> System.out.println("\n Obrigado por utilizar o Sistema Bancário POO. Até logo!");
                    default -> System.out.println("\n Opção inválida! Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n Erro: Digite apenas números válidos.");
            } catch (Exception e) {
                System.out.println("\n Ocorreu um erro inesperado: " + e.getMessage());
            }
            if (opcao != 0) {
                System.out.println("\nPressione ENTER para continuar...");
                scanner.nextLine();
            }
        }
    }

    private void exibirMenu() {
        System.out.println("\n==================================================");
        System.out.println("            🏦 SISTEMA BANCÁRIO POO 🏦           ");
        System.out.println("==================================================");
        System.out.println("1. Cadastrar Cliente");
        System.out.println("2. Criar Conta");
        System.out.println("3. Realizar Depósito");
        System.out.println("4. Realizar Saque");
        System.out.println("5. Realizar Transferência Tradicional");
        System.out.println("6. Cadastrar Chave PIX");
        System.out.println("7. Realizar PIX");
        System.out.println("8. Solicitar Cartão (Crédito/Débito)");
        System.out.println("9. Simular / Solicitar Empréstimo");
        System.out.println("10. Consultar Saldo e Extrato");
        System.out.println("11. Área do Gerente (Painel Administrativo) 👔");
        System.out.println("0. Sair");
        System.out.println("--------------------------------------------------");
        System.out.print("Escolha uma opção: ");
    }

    private void cadastrarCliente() {
        System.out.println("\n--- 👤 CADASTRO DE CLIENTE ---");
        System.out.print("Nome completo: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Crie uma Senha: ");
        String senha = scanner.nextLine();

        if (banco.buscarClientePorCpf(cpf) != null) {
            System.out.println("\n Erro: Já existe um cliente cadastrado com este CPF.");
            return;
        }

        Cliente cliente = new Cliente(nome, cpf, email, telefone, senha);
        banco.adicionarCliente(cliente);
        System.out.println("\n Cliente " + nome + " cadastrado com sucesso!");
    }

    private void criarConta() {
        System.out.println("\n--- 💳 CRIAÇÃO DE CONTA ---");
        System.out.print("Digite o CPF do titular: ");
        String cpf = scanner.nextLine();

        Cliente cliente = banco.buscarClientePorCpf(cpf);
        if (cliente == null) {
            System.out.println("\n Erro: Cliente não encontrado. Cadastre o cliente primeiro.");
            return;
        }

        System.out.println("Selecione o tipo de conta:");
        System.out.println("1. Conta Corrente");
        System.out.println("2. Conta Poupança");
        System.out.println("3. Conta Salário");
        System.out.println("4. Conta Empresarial");
        System.out.println("5. Conta Universitária");
        System.out.print("Opção: ");
        int tipo = Integer.parseInt(scanner.nextLine());

        System.out.print("Digite o número para a nova conta: ");
        String numero = scanner.nextLine();

        if (banco.buscarConta(numero) != null) {
            System.out.println("\n Erro: Já existe uma conta com esse número.");
            return;
        }

        Conta novaConta = null;
        switch (tipo) {
            case 1 -> novaConta = new ContaCorrente(numero, cliente);
            case 2 -> novaConta = new ContaPoupanca(numero, cliente);
            case 3 -> novaConta = new ContaSalario(numero, cliente);
            case 4 -> novaConta = new ContaEmpresarial(numero, cliente);
            case 5 -> novaConta = new ContaUniversitaria(numero, cliente);
            default -> System.out.println("\n Tipo de conta inválido.");
        }

        if (novaConta != null) {
            banco.adicionarConta(novaConta);
            cliente.adicionarConta(novaConta);
            System.out.println("\n Conta número " + numero + " criada com sucesso para " + cliente.getNome() + "!");
        }
    }

    private void realizarDeposito() {
        System.out.println("\n--- 💵 DEPÓSITO ---");
        System.out.print("Número da conta: ");
        String numero = scanner.nextLine();
        Conta conta = banco.buscarConta(numero);

        if (conta == null) {
            System.out.println("\n Conta não encontrada.");
            return;
        }

        System.out.print("Valor a depositar: R$ ");
        double valor = Double.parseDouble(scanner.nextLine());

        try {
            Deposito op = new Deposito(conta, valor);
            op.executar();
            System.out.println("\n Depósito efetuado com sucesso! Saldo atual: R$ " + conta.getSaldo());
        } catch (Exception e) {
            System.out.println("\n Erro ao depositar: " + e.getMessage());
        }
    }

    private void realizarSaque() {
        System.out.println("\n--- 🏧 SAQUE ---");
        System.out.print("Número da conta: ");
        String numero = scanner.nextLine();
        Conta conta = banco.buscarConta(numero);

        if (conta == null) {
            System.out.println("\n Conta não encontrada.");
            return;
        }

        System.out.print("Valor a sacar: R$ ");
        double valor = Double.parseDouble(scanner.nextLine());

        try {
            Saque op = new Saque(conta, valor);
            op.executar();
            System.out.println("\n Saque efetuado com sucesso! Saldo atual: R$ " + conta.getSaldo());
        } catch (Exception e) {
            System.out.println("\n Erro ao sacar: " + e.getMessage());
        }
    }

    private void realizarTransferencia() {
        System.out.println("\n--- 🔄 TRANSFERÊNCIA TRADICIONAL ---");
        System.out.print("Número da conta de ORIGEM: ");
        Conta origem = banco.buscarConta(scanner.nextLine());

        System.out.print("Número da conta de DESTINO: ");
        Conta destino = banco.buscarConta(scanner.nextLine());

        if (origem == null || destino == null) {
            System.out.println("\n Uma ou ambas as contas não foram encontradas.");
            return;
        }

        System.out.print("Valor da transferência: R$ ");
        double valor = Double.parseDouble(scanner.nextLine());

        try {
            Transferencia op = new Transferencia(origem, destino, valor);
            op.executar();
            System.out.println("\n Transferência realizada com sucesso!");
            System.out.println("Saldo origem: R$ " + origem.getSaldo());
        } catch (Exception e) {
            System.out.println("\n Erro na transferência: " + e.getMessage());
        }
    }

    private void cadastrarChavePix() {
        System.out.println("\n--- 🔑 CADASTRO DE CHAVE PIX ---");
        System.out.print("Número da conta a vincular: ");
        Conta conta = banco.buscarConta(scanner.nextLine());

        if (conta == null) {
            System.out.println("\n Conta não encontrada.");
            return;
        }

        System.out.println("Selecione o Tipo de Chave:");
        System.out.println("1. CPF");
        System.out.println("2. E-mail");
        System.out.println("3. Telefone");
        System.out.println("4. Aleatória");
        System.out.print("Opção: ");
        int opTipo = Integer.parseInt(scanner.nextLine());

        TipoChavePix tipo = switch (opTipo) {
            case 1 -> TipoChavePix.CPF;
            case 2 -> TipoChavePix.EMAIL;
            case 3 -> TipoChavePix.TELEFONE;
            default -> TipoChavePix.ALEATORIA;
        };

        System.out.print("Digite o valor da chave (ex: email, telefone ou cpf): ");
        String valorChave = scanner.nextLine();

        ChavePix chave = new ChavePix(valorChave, tipo, conta);
        conta.getCliente().adicionarChavePix(chave);
        System.out.println("\n Chave PIX (" + valorChave + ") cadastrada com sucesso!");
    }

    private void realizarPix() {
        System.out.println("\n--- ⚡ PAGAMENTO / TRANSFERÊNCIA PIX ---");
        System.out.print("Número da sua conta (ORIGEM): ");
        Conta origem = banco.buscarConta(scanner.nextLine());

        if (origem == null) {
            System.out.println("\n Conta de origem não encontrada.");
            return;
        }

        System.out.print("Digite a chave PIX do DESTINATÁRIO: ");
        String chave = scanner.nextLine();

        System.out.print("Valor do PIX: R$ ");
        double valor = Double.parseDouble(scanner.nextLine());

        // Busca o cliente/conta vinculada à chave
        Conta destino = null;
        for (Conta c : banco.getContas()) {
            if (c.getCliente() != null && c.getCliente().getChavesPix() != null) {
                for (ChavePix cp : c.getCliente().getChavesPix()) {
                    if (cp.getValor().equalsIgnoreCase(chave)) {
                        destino = cp.getConta();
                        break;
                    }
                }
            }
        }

        if (destino == null) {
            System.out.println("\n Chave PIX não encontrada no sistema.");
            return;
        }

        try {
            Pix op = new Pix(origem, destino, valor, chave);
            op.executar();
            System.out.println("\n PIX realizado com sucesso para o titular da chave!");
            System.out.println("Novo saldo origem: R$ " + origem.getSaldo());
        } catch (Exception e) {
            System.out.println("\n Erro ao realizar PIX: " + e.getMessage());
        }
    }

    private void solicitarCartao() {
        System.out.println("\n--- 💳 SOLICITAÇÃO DE CARTÃO ---");
        System.out.print("CPF do titular: ");
        Cliente cliente = banco.buscarClientePorCpf(scanner.nextLine());

        if (cliente == null) {
            System.out.println("\n Cliente não encontrado.");
            return;
        }

        System.out.println("Qual cartão deseja solicitar?");
        System.out.println("1. Cartão de Crédito");
        System.out.println("2. Cartão de Débito");
        System.out.print("Opção: ");
        int op = Integer.parseInt(scanner.nextLine());

        if (op == 1) {
            CartaoCredito cc = new CartaoCredito("4000 1234 5678 9010", "12/28", "123", 2000.0);
            cliente.adicionarCartao(cc);
            System.out.println("\n Cartão de Crédito com limite de R$ 2000,00 gerado com sucesso!");
        } else {
            CartaoDebito cd = new CartaoDebito("5000 1234 5678 9010", "12/28", "321");
            cliente.adicionarCartao(cd);
            System.out.println("\n Cartão de Débito gerado e vinculado ao cliente!");
        }
    }

    private void solicitarEmprestimo() {
        System.out.println("\n--- 🏦 EMPRÉSTIMO BANCÁRIO ---");
        System.out.print("Número da Conta: ");
        Conta conta = banco.buscarConta(scanner.nextLine());

        if (conta == null) {
            System.out.println("\n Conta não encontrada.");
            return;
        }

        System.out.print("Valor do empréstimo pretendido: R$ ");
        double valor = Double.parseDouble(scanner.nextLine());

        System.out.print("Quantidade de parcelas: ");
        int parcelas = Integer.parseInt(scanner.nextLine());

        Emprestimo emp = new Emprestimo(conta, valor, parcelas);
        System.out.println("\n Solicitando aprovação com o gerente...");
        emp.aprovar(gerentePadrao);

        if (emp.isAprovado()) {
            try {
                conta.depositar(valor);
                System.out.println("\n Empréstimo APROVADO pelo Gerente " + gerentePadrao.getNome() + "!");
                System.out.println("R$ " + valor + " foram creditados no seu saldo. Saldo Atual: R$ " + conta.getSaldo());
            } catch (Exception e) {
                System.out.println("Erro ao depositar empréstimo: " + e.getMessage());
            }
        }
    }

    private void consultarExtrato() {
        System.out.println("\n--- 📋 CONSULTA DE SALDO E DADOS DA CONTA ---");
        System.out.print("Número da Conta: ");
        Conta conta = banco.buscarConta(scanner.nextLine());

        if (conta == null) {
            System.out.println("\n Conta não encontrada.");
            return;
        }

        System.out.println("==========================================");
        System.out.println("Titular: " + conta.getCliente().getNome());
        System.out.println("CPF: " + conta.getCliente().getCpf());
        System.out.println("Número da Conta: " + conta.getNumero());
        System.out.println("Tipo da Conta: " + conta.getClass().getSimpleName());
        System.out.println("Saldo Atual: R$ " + conta.getSaldo());
        System.out.println("Conta Bloqueada: " + (conta.isBloqueada() ? "SIM" : "NÃO"));

        Cliente c = conta.getCliente();
        if (c.getChavesPix() != null && !c.getChavesPix().isEmpty()) {
            System.out.println("\nChaves Pix Cadastradas:");
            for (ChavePix cp : c.getChavesPix()) {
                System.out.println(" - " + cp.getTipo() + ": " + cp.getValor());
            }
        }
        System.out.println("==========================================");
    }

    private void areaGerente() {
        System.out.println("\n==========================================");
        System.out.println("      👔 PAINEL ADMINISTRATIVO DO GERENTE   ");
        System.out.println("==========================================");
        System.out.println("Gerente Responsável: " + gerentePadrao.getNome());
        System.out.println("Matrícula: " + gerentePadrao.getMatricula());
        System.out.println("1. Relatório Geral (Clientes, Contas e Saldos)");
        System.out.println("2. Bloquear / Desbloquear Conta de Cliente");
        System.out.print("Escolha uma opção: ");
        
        int op = Integer.parseInt(scanner.nextLine());
        if (op == 1) {
            System.out.println("\n--- RELATÓRIO DE CONTAS NO BANCO ---");
            if (banco.getContas().isEmpty()) {
                System.out.println("Nenhuma conta registrada até o momento.");
            } else {
                for (Conta c : banco.getContas()) {
                    System.out.println("• Conta: " + c.getNumero() + " | Tipo: " + c.getClass().getSimpleName() + 
                                       " | Cliente: " + c.getCliente().getNome() + " (CPF: " + c.getCliente().getCpf() + ")" +
                                       " | Saldo: R$ " + c.getSaldo() + " | Bloqueada: " + c.isBloqueada());
                }
            }
        } else if (op == 2) {
            System.out.print("Digite o número da conta que deseja alterar o status: ");
            Conta c = banco.buscarConta(scanner.nextLine());
            if (c != null) {
                c.setBloqueada(!c.isBloqueada());
                System.out.println("\n Status atualizado! A conta " + c.getNumero() + " agora está: " + 
                                   (c.isBloqueada() ? "BLOQUEADA 🚫" : "DESBLOQUEADA 🟢"));
            } else {
                System.out.println("\n Conta não encontrada.");
            }
        } else {
            System.out.println("\n Opção inválida do gerente.");
        }
    }
}
