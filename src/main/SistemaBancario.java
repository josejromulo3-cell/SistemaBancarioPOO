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
import util.GeradorNumeroConta;

import java.util.Scanner;

public class SistemaBancario {
    private Banco banco;
    private Scanner scanner;
    private Gerente gerentePadrao;

    public SistemaBancario() {
        this.banco = new Banco("Banco Central POO");
        this.scanner = new Scanner(System.in);
        this.gerentePadrao = new Gerente("Carlos Andrade", "111.222.333-44", "gerente@bancopoo.com", "83 98888-7777", "GER-001");
    }

    public void iniciar() {
        int opcao = -1;
        while (opcao != 0) {
            exibirMenu();
            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
                switch (opcao) {
                    case 1 -> cadastrarCliente();
                    case 2 -> criarConta();
                    case 3 -> realizarDeposito();
                    case 4 -> realizarSaque();
                    case 5 -> realizarTransferencia();
                    case 6 -> cadastrarChavePix();
                    case 7 -> realizarPix();
                    case 8 -> realizarPixNoCredito();
                    case 9 -> solicitarCartao();
                    case 10 -> consultarFatura();
                    case 11 -> solicitarEmprestimo();
                    case 12 -> consultarExtrato();
                    case 13 -> areaGerente();
                    case 0 -> System.out.println("\n Obrigado por utilizar o Sistema Bancário POO. Até logo!");
                    default -> System.out.println("\n Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("\n Erro no processamento: " + e.getMessage());
            }
            if (opcao != 0) {
                System.out.println("\nPressione ENTER para continuar...");
                scanner.nextLine();
            }
        }
    }

    private void exibirMenu() {
        System.out.println("             SISTEMA BANCÁRIO POO            ");
         System.out.println("1. Cadastrar Cliente");
        System.out.println("2. Criar Conta (Número Gerado Automático )");
        System.out.println("3. Realizar Depósito");
        System.out.println("4. Realizar Saque");
        System.out.println("5. Realizar Transferência Tradicional");
        System.out.println("6. Cadastrar Chave PIX");
        System.out.println("7. Realizar PIX (Débito em Conta)");
        System.out.println("8. Realizar PIX no Crédito (Cartão de Crédito )");
        System.out.println("9. Solicitar Cartão de Crédito");
        System.out.println("10. Consultar Fatura do Cartão de Crédito");
        System.out.println("11. Solicitar Empréstimo (Aprovação via Gerente)");
        System.out.println("12. Consultar Saldo, Extrato e Dados da Conta ");
        System.out.println("13. Área do Gerente (Painel Administrativo) ");
        System.out.println("0. Sair");
        System.out.println("--------------------------------------------------");
        System.out.print("Escolha uma opção: ");
    }

    private void cadastrarCliente() {
        System.out.println("\n--- CADASTRO DE CLIENTE ---");
        System.out.print("Nome completo: ");
        String nome = scanner.nextLine().trim();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine().trim();
        System.out.print("E-mail: ");
        String email = scanner.nextLine().trim();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine().trim();
        System.out.print("Crie uma Senha: ");
        String senha = scanner.nextLine().trim();

        if (banco.buscarClientePorCpf(cpf) != null) {
            System.out.println("\n Erro: Já existe um cliente cadastrado com este CPF.");
            return;
        }

        Cliente cliente = new Cliente(nome, cpf, email, telefone, senha);
        banco.adicionarCliente(cliente);
        System.out.println("\n Cliente " + nome + " cadastrado com SUCESSO!");
    }

    private void criarConta() {
        System.out.println("\n---  CRIAÇÃO DE CONTA ---");
        System.out.print("Digite o CPF do titular cadastrado: ");
        String cpf = scanner.nextLine().trim();
        Cliente cliente = banco.buscarClientePorCpf(cpf);

        if (cliente == null) {
            System.out.println("\n Erro: Cliente não encontrado para o CPF informado. Cadastre o cliente primeiro!");
            return;
        }

        System.out.println("Selecione o tipo de conta:");
        System.out.println("1. Conta Corrente | 2. Poupança | 3. Salário | 4. Empresa | 5. Universitária");
        int tipo = Integer.parseInt(scanner.nextLine().trim());

        String numeroGerado = GeradorNumeroConta.gerarNumero();

        Conta novaConta = switch (tipo) {
            case 1 -> new ContaCorrente(numeroGerado, cliente);
            case 2 -> new ContaPoupanca(numeroGerado, cliente);
            case 3 -> new ContaSalario(numeroGerado, cliente);
            case 4 -> new ContaEmpresarial(numeroGerado, cliente);
            case 5 -> new ContaUniversitaria(numeroGerado, cliente);
            default -> null;
        };

        if (novaConta == null) {
            System.out.println("\n Erro: Tipo de conta inválido.");
            return;
        }

        banco.adicionarConta(novaConta);
        cliente.adicionarConta(novaConta);
        System.out.println("\n Conta criada com SUCESSO!");
        System.out.println(" Titular: " + cliente.getNome());
        System.out.println(" Número da Conta Gerado: " + numeroGerado);
    }

    private void realizarDeposito() {
        System.out.println("\n---  DEPÓSITO ---");
        System.out.print("Número da conta: ");
        Conta conta = banco.buscarConta(scanner.nextLine().trim());
        if (conta == null) {
            System.out.println("\n Erro: Conta não encontrada no sistema.");
            return;
        }

        System.out.print("Valor do depósito: R$ ");
        double valor = Double.parseDouble(scanner.nextLine().trim());

        try {
            Deposito op = new Deposito(conta, valor);
            op.executar();
            System.out.println("\n Depósito realizado com SUCESSO!");
            System.out.println(" Novo Saldo da Conta (" + conta.getNumero() + "): R$ " + String.format("%.2f", conta.getSaldo()));
        } catch (Exception e) {
            System.out.println("\n Erro no depósito: " + e.getMessage());
        }
    }

    private void realizarSaque() {
        System.out.println("\n---  SAQUE ---");
        System.out.print("Número da conta: ");
        Conta conta = banco.buscarConta(scanner.nextLine().trim());
        if (conta == null) {
            System.out.println("\n Erro: Conta não encontrada no sistema.");
            return;
        }

        System.out.print("Valor do saque: R$ ");
        double valor = Double.parseDouble(scanner.nextLine().trim());

        try {
            Saque op = new Saque(conta, valor);
            op.executar();
            System.out.println("\n Saque efetuado com SUCESSO!");
            System.out.println(" Saldo atualizado da Conta (" + conta.getNumero() + "): R$ " + String.format("%.2f", conta.getSaldo()));
        } catch (Exception e) {
            System.out.println("\n Erro no saque: " + e.getMessage());
        }
    }

    private void realizarTransferencia() {
        System.out.println("\n  TRANSFERÊNCIA TRADICIONAL ");
        System.out.print("Digite o número da conta de ORIGEM: ");
        Conta origem = banco.buscarConta(scanner.nextLine().trim());
        if (origem == null) {
            System.out.println("\n Erro: Conta de origem não existe.");
            return;
        }

        System.out.print("Digite o número da conta de DESTINO: ");
        Conta destino = banco.buscarConta(scanner.nextLine().trim());
        if (destino == null) {
            System.out.println("\n Erro: Conta de destino não existe.");
            return;
        }

        System.out.print("Valor da transferência: R$ ");
        double valor = Double.parseDouble(scanner.nextLine().trim());

        try {
            Transferencia op = new Transferencia(origem, destino, valor);
            op.executar();
            System.out.println("\n Transferência concluída com SUCESSO!");
            System.out.println(" Saldo Origem (" + origem.getNumero() + "): R$ " + String.format("%.2f", origem.getSaldo()));
        } catch (Exception e) {
            System.out.println("\n Erro na transferência: " + e.getMessage());
        }
    }

    private void cadastrarChavePix() {
        System.out.println("\n CADASTRO DE CHAVE PIX ");
        System.out.print("Número da conta vinculada: ");
        Conta conta = banco.buscarConta(scanner.nextLine().trim());
        if (conta == null) {
            System.out.println("\n Erro: Conta não encontrada no sistema.");
            return;
        }

        System.out.print("Informe a chave PIX (CPF, E-mail, Celular ou Aleatória): ");
        String valorChave = scanner.nextLine().trim();

        if (banco.buscarChavePix(valorChave) != null) {
            System.out.println("\n Erro: Esta chave PIX já está cadastrada no sistema.");
            return;
        }

        ChavePix chave = new ChavePix(valorChave, TipoChavePix.EMAIL, conta);
        conta.getCliente().adicionarChavePix(chave);
        System.out.println("\n Chave PIX '" + valorChave + "' cadastrada com SUCESSO para o cliente " + conta.getCliente().getNome() + "!");
    }

    private void realizarPix() {
        System.out.println("\n  PIX (DÉBITO EM CONTA) ");
        System.out.print("Digite o número da CONTA DE ORIGEM: ");
        Conta origem = banco.buscarConta(scanner.nextLine().trim());
        if (origem == null) {
            System.out.println("\n Erro: Conta de origem não encontrada no banco.");
            return;
        }

        System.out.print("Digite a CHAVE PIX DO DESTINATÁRIO: ");
        String chave = scanner.nextLine().trim();
        ChavePix chavePixDestino = banco.buscarChavePix(chave);

        if (chavePixDestino == null) {
            System.out.println("\n Erro: A chave PIX informada NÃO EXISTE ou não possui conta cadastrada!");
            return;
        }

        Conta destino = chavePixDestino.getConta();
        if (destino == null) {
            System.out.println("\n Erro: A conta vinculada a esta chave PIX não existe.");
            return;
        }

        System.out.println(" Destinatário Encontrado: " + destino.getCliente().getNome() + " (Conta: " + destino.getNumero() + ")");
        System.out.print("Digite o VALOR do Pix: R$ ");
        double valor = Double.parseDouble(scanner.nextLine().trim());

        try {
            Pix op = new Pix(origem, destino, valor, chave);
            op.executar();
            System.out.println("\n PIX REALIZADO COM SUCESSO!");
            System.out.println(" Saldo atualizado da Conta Origem: R$ " + String.format("%.2f", origem.getSaldo()));
        } catch (Exception e) {
            System.out.println("\n Erro na execução do Pix: " + e.getMessage());
        }
    }

    private void realizarPixNoCredito() {
        System.out.println("\n PIX NO CRÉDITO ");
        System.out.print("Digite o CPF do Titular do Cartão: ");
        Cliente cliente = banco.buscarClientePorCpf(scanner.nextLine().trim());

        if (cliente == null) {
            System.out.println("\n Erro: Cliente não encontrado.");
            return;
        }

        if (cliente.getContas().isEmpty()) {
            System.out.println("\n Erro: O cliente não possui conta cadastrada.");
            return;
        }

        CartaoCredito cc = null;
        for (Cartao c : cliente.getCartoes()) {
            if (c instanceof CartaoCredito) {
                cc = (CartaoCredito) c;
                break;
            }
        }

        if (cc == null) {
            System.out.println("\n Erro: Cliente não possui cartão de crédito aprovado.");
            return;
        }

        System.out.print("Digite a CHAVE PIX DO DESTINATÁRIO: ");
        String chave = scanner.nextLine().trim();
        ChavePix chavePixDestino = banco.buscarChavePix(chave);

        if (chavePixDestino == null) {
            System.out.println("\n Erro: A chave PIX de destino NÃO EXISTE no sistema.");
            return;
        }

        System.out.print("Valor do Pix no Crédito: R$ ");
        double valor = Double.parseDouble(scanner.nextLine().trim());

        try {
            cc.realizarPixNoCredito(chave, valor);
            System.out.println("\n PIX no Crédito REALIZADO com sucesso!");
            System.out.println(" Lançado na fatura do cartão " + cc.getNumero());
            System.out.println(" Limite Disponível no Cartão: R$ " + String.format("%.2f", cc.getLimiteDisponivel()));
        } catch (Exception e) {
            System.out.println("\n Erro no Pix Crédito: " + e.getMessage());
        }
    }

    private void solicitarCartao() {
        System.out.println("\n SOLICITAR CARTÃO DE CRÉDITO ");
        System.out.print("Digite o CPF do Titular: ");
        Cliente cliente = banco.buscarClientePorCpf(scanner.nextLine().trim());

        if (cliente == null) {
            System.out.println("\n Erro: Cliente não cadastrado no banco.");
            return;
        }

        if (cliente.getContas().isEmpty()) {
            System.out.println("\n Erro: O cliente NÃO possui nenhuma conta bancária cadastrada!");
            System.out.println(" É obrigatório ter ao menos uma conta no banco para solicitar cartão.");
            return;
        }

        System.out.println("Contas vinculadas ao cliente:");
        for (Conta c : cliente.getContas()) {
            System.out.println(" • Conta Número: " + c.getNumero());
        }

        System.out.print("Digite o número da conta para vincular o cartão: ");
        Conta conta = banco.buscarConta(scanner.nextLine().trim());

        if (conta == null || !cliente.getContas().contains(conta)) {
            System.out.println("\n Erro: Conta inválida ou não pertence a este cliente.");
            return;
        }

        System.out.print("Informe o Limite de Crédito desejado: R$ ");
        double limite = Double.parseDouble(scanner.nextLine().trim());

        SolicitacaoCartao solicitacao = new SolicitacaoCartao(cliente, conta, limite);
        banco.adicionarSolicitacaoCartao(solicitacao);

        System.out.println("\n Solicitação enviada com SUCESSO!");
        System.out.println(" Status: AGUARDANDO APROVAÇÃO DO GERENTE (ID do pedido: #" + solicitacao.getId() + ")");
    }

    private void consultarFatura() {
        System.out.println("\n CONSULTA DE FATURA ");
        System.out.print("CPF do Titular: ");
        Cliente cliente = banco.buscarClientePorCpf(scanner.nextLine().trim());

        if (cliente == null) {
            System.out.println("\n Erro: Cliente não encontrado.");
            return;
        }

        boolean encontrouCartao = false;
        for (Cartao c : cliente.getCartoes()) {
            if (c instanceof CartaoCredito cc) {
                encontrouCartao = true;
                System.out.println("\n Cartão de Crédito Final: " + cc.getNumero());
                System.out.println(" Limite Total: R$ " + String.format("%.2f", cc.getLimite()));
                System.out.println(" Limite Disponível: R$ " + String.format("%.2f", cc.getLimiteDisponivel()));
                System.out.println(" LANÇAMENTOS NA FATURA ");
                if (cc.getFatura().getItens().isEmpty()) {
                    System.out.println(" (Nenhum lançamento registrado nesta fatura)");
                } else {
                    for (String item : cc.getFatura().getItens()) {
                        System.out.println("  • " + item);
                    }
                }
                System.out.println(" TOTAL A PAGAR NA FATURA: R$ " + String.format("%.2f", cc.getFatura().getValorAPagar()));
            }
        }

        if (!encontrouCartao) {
            System.out.println("\n Nenhum cartão de crédito ativo/aprovado para este cliente.");
        }
    }

    private void solicitarEmprestimo() {
        System.out.println("\nSOLICITAÇÃO DE EMPRÉSTIMO");
        System.out.print("Digite o número da Conta: ");
        Conta conta = banco.buscarConta(scanner.nextLine().trim());

        if (conta == null) {
            System.out.println("\n Erro: Conta não encontrada.");
            return;
        }

        System.out.print("Valor do empréstimo solicitado: R$ ");
        double valor = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Quantidade de parcelas: ");
        int parcelas = Integer.parseInt(scanner.nextLine().trim());

        Emprestimo emp = new Emprestimo(conta, valor, parcelas);
        banco.adicionarEmprestimo(emp);

        System.out.println("\n Solicitação de Empréstimo #" + emp.getId() + " registrada com SUCESSO!");
        System.out.println(" O valor só será creditado na conta após a APROVAÇÃO DO GERENTE.");
    }

    private void consultarExtrato() {
        System.out.println("\n DADOS DA CONTA E EXTRATO COMPLETO ");
        System.out.print("Digite o número da Conta: ");
        Conta conta = banco.buscarConta(scanner.nextLine().trim());

        if (conta == null) {
            System.out.println("\n Erro: Conta não encontrada no sistema.");
            return;
        }

        Cliente titular = conta.getCliente();
        System.out.println(" DADOS DO TITULAR");
        System.out.println("   Nome: " + titular.getNome());
        System.out.println("   CPF: " + titular.getCpf());
        System.out.println("   E-mail: " + titular.getEmail());
        System.out.println("   Telefone: " + titular.getTelefone());
        System.out.println(" DADOS DA CONTA");
        System.out.println("   Número da Conta: " + conta.getNumero());
        System.out.println("   Tipo: " + conta.getClass().getSimpleName());
        System.out.println("   Status: " + (conta.isBloqueada() ? " BLOQUEADA" : " ATIVA"));
        System.out.println("   Saldo Atual: R$ " + String.format("%.2f", conta.getSaldo()));

        if (conta instanceof ContaCorrente cc) {
            System.out.println("   Limite Especial: R$ " + String.format("%.2f", cc.getLimite()));
        }

        System.out.println("--------------------------------------------------");
        System.out.println("  CHAVES PIX CADASTRADAS:");
        if (titular.getChavesPix().isEmpty()) {
            System.out.println("   (Nenhuma chave Pix vinculada)");
        } else {
            for (ChavePix cp : titular.getChavesPix()) {
                if (cp.getConta().getNumero().equalsIgnoreCase(conta.getNumero())) {
                    System.out.println("   • " + cp.getValor() + " [" + cp.getTipo() + "]");
                }
            }
        }

        System.out.println(" HISTÓRICO DE OPERAÇÕES (EXTRATO):");
        if (conta.getOperacoes().isEmpty()) {
            System.out.println("   (Nenhuma operação realizada nesta conta até o momento)");
        } else {
            for (Operacao op : conta.getOperacoes()) {
                System.out.println("   • " + op.getDescricao() + " | Valor: R$ " + String.format("%.2f", op.getValor()));
            }
        }
    }

    private void areaGerente() {
        System.out.print("\n Digite a senha do Gerente (padrão: admin123): ");
        String senha = scanner.nextLine().trim();

        if (!gerentePadrao.autenticar(senha)) {
            System.out.println("\n Senha incorreta! Acesso negado à Área do Gerente.");
            return;
        }

        int subOpcao = -1;
        while (subOpcao != 0) {
            System.out.println("          PAINEL ADMINISTRATIVO DO GERENTE       ");
            System.out.println("         Gerente: " + gerentePadrao.getNome());
            
            System.out.println("1. Aprovar/Recusar Empréstimos Pendentes (" + contarEmprestimosPendentes() + " pendentes)");
            System.out.println("2. Aprovar/Recusar Cartões de Crédito Pendentes (" + contarCartoesPendentes() + " pendentes)");
            System.out.println("3. Bloquear / Desbloquear Conta");
            System.out.println("4. Relatório Geral de Contas do Banco");
            System.out.println("0. Voltar ao Menu Principal");
            
            System.out.print("Escolha uma opção: ");

            try {
                subOpcao = Integer.parseInt(scanner.nextLine().trim());
                switch (subOpcao) {
                    case 1 -> gerenciarEmprestimos();
                    case 2 -> gerenciarCartoes();
                    case 3 -> alterarStatusConta();
                    case 4 -> exibirRelatorioBanco();
                    case 0 -> System.out.println("Saindo do painel do gerente...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private long contarEmprestimosPendentes() {
        return banco.getEmprestimos().stream().filter(e -> e.getStatus() == Emprestimo.StatusEmprestimo.PENDENTE).count();
    }

    private long contarCartoesPendentes() {
        return banco.getSolicitacoesCartao().stream().filter(s -> s.getStatus() == SolicitacaoCartao.StatusSolicitacao.PENDENTE).count();
    }

    private void gerenciarEmprestimos() {
        System.out.println("\nGERENCIAR EMPRÉSTIMOS PENDENTES ");
        var pendentes = banco.getEmprestimos().stream()
                .filter(e -> e.getStatus() == Emprestimo.StatusEmprestimo.PENDENTE)
                .toList();

        if (pendentes.isEmpty()) {
            System.out.println("Nenhum empréstimo pendente de aprovação.");
            return;
        }

        for (Emprestimo e : pendentes) {
            System.out.println("ID: #" + e.getId() + " | Cliente: " + e.getConta().getCliente().getNome() +
                    " | Conta: " + e.getConta().getNumero() + " | Valor: R$ " + e.getValor() + " | Parcelas: " + e.getParcelas());
            System.out.print("Deseja APROVAR (A) ou RECUSAR (R)? [A/R]: ");
            String dec = scanner.nextLine().trim();

            if (dec.equalsIgnoreCase("A")) {
                try {
                    e.aprovar(gerentePadrao);
                    System.out.println("Empréstimo #" + e.getId() + " APROVADO! Valor creditado na conta.");
                } catch (Exception ex) {
                    System.out.println("Erro ao aprovar: " + ex.getMessage());
                }
            } else {
                e.recusar(gerentePadrao);
                System.out.println("Empréstimo #" + e.getId() + " RECUSADO.");
            }
        }
    }

    private void gerenciarCartoes() {
        System.out.println("\n GERENCIAR CARTÕES PENDENTES ");
        var pendentes = banco.getSolicitacoesCartao().stream()
                .filter(s -> s.getStatus() == SolicitacaoCartao.StatusSolicitacao.PENDENTE)
                .toList();

        if (pendentes.isEmpty()) {
            System.out.println("Nenhuma solicitação de cartão pendente.");
            return;
        }

        for (SolicitacaoCartao s : pendentes) {
            System.out.println("ID: #" + s.getId() + " | Cliente: " + s.getCliente().getNome() +
                    " | Conta: " + s.getConta().getNumero() + " | Limite Desejado: R$ " + s.getLimiteSolicitado());
            System.out.print("Deseja APROVAR (A) ou RECUSAR (R)? [A/R]: ");
            String dec = scanner.nextLine().trim();

            if (dec.equalsIgnoreCase("A")) {
                s.aprovar();
                System.out.println("Solicitação #" + s.getId() + " APROVADA! Cartão gerado e associado ao cliente.");
            } else {
                s.recusar();
                System.out.println("Solicitação #" + s.getId() + " RECUSADA.");
            }
        }
    }

    private void alterarStatusConta() {
        System.out.println("\n BLOQUEAR / DESBLOQUEAR CONTA ");
        System.out.print("Digite o número da Conta: ");
        Conta conta = banco.buscarConta(scanner.nextLine().trim());

        if (conta == null) {
            System.out.println("Conta não encontrada.");
            return;
        }

        System.out.println("Status atual da conta: " + (conta.isBloqueada() ? "BLOQUEADA" : "ATIVA"));
        System.out.print("Deseja alterar para (1 - Bloquear / 2 - Desbloquear): ");
        int op = Integer.parseInt(scanner.nextLine().trim());

        if (op == 1) {
            conta.setBloqueada(true);
            System.out.println("Conta " + conta.getNumero() + " BLOQUEADA pelo Gerente!");
        } else if (op == 2) {
            conta.setBloqueada(false);
            System.out.println("Conta " + conta.getNumero() + " DESBLOQUEADA pelo Gerente!");
        }
    }

    private void exibirRelatorioBanco() {
        System.out.println("\n RELATÓRIO GERAL DO BANCO ");
        System.out.println("Total de Clientes Cadastrados: " + banco.getClientes().size());
        System.out.println("Total de Contas Abertas: " + banco.getContas().size());

        double saldoTotal = 0.0;
        for (Conta c : banco.getContas()) {
            saldoTotal += c.getSaldo();
            System.out.println(" • Conta: " + c.getNumero() + " | Titular: " + c.getCliente().getNome() +
                    " | Tipo: " + c.getClass().getSimpleName() + " | Saldo: R$ " + String.format("%.2f", c.getSaldo()) +
                    " | Status: " + (c.isBloqueada() ? "BLOQUEADA" : "ATIVA"));
        }
        
        System.out.println(" TOTAL CUSTODIADO NO BANCO: R$ " + String.format("%.2f", saldoTotal));
    }

    private ChavePix buscarContaPorPix(String chave) {
        return banco.buscarChavePix(chave);
    }
}
