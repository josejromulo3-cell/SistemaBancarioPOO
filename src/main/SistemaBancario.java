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
                    case 10 -> gerarCartaoVirtual();
                    case 11 -> consultarCartoes();
                    case 12 -> consultarFatura();
                    case 13 -> solicitarEmprestimo();
                    case 14 -> consultarExtrato();
                    case 15 -> areaGerente();
                    case 0 -> System.out.println("Obrigado por utilizar o Sistema Bancario POO. Ate logo!");
                    default -> System.out.println("Opcao invalida!");
                }
            } catch (Exception e) {
                System.out.println("Erro no processamento: " + e.getMessage());
            }
            if (opcao != 0) {
                System.out.println("Pressione ENTER para continuar...");
                scanner.nextLine();
            }
        }
    }

    private void exibirMenu() {
        System.out.println("SISTEMA BANCARIO POO");
        System.out.println("1. Cadastrar Cliente");
        System.out.println("2. Criar Conta");
        System.out.println("3. Realizar Deposito");
        System.out.println("4. Realizar Saque");
        System.out.println("5. Realizar Transferencia Tradicional");
        System.out.println("6. Cadastrar Chave PIX");
        System.out.println("7. Realizar PIX");
        System.out.println("8. Realizar PIX no Credito");
        System.out.println("9. Solicitar Cartao de Credito");
        System.out.println("10. Gerar Cartao Virtual");
        System.out.println("11. Consultar Cartoes");
        System.out.println("12. Consultar Fatura do Cartao de Credito");
        System.out.println("13. Solicitar Emprestimo");
        System.out.println("14. Consultar Saldo e Extrato");
        System.out.println("15. Area do Gerente");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private void cadastrarCliente() {
        System.out.println("CADASTRO DE CLIENTE");
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
            System.out.println("Erro: Ja existe um cliente cadastrado com este CPF.");
            return;
        }

        Cliente cliente = new Cliente(nome, cpf, email, telefone, senha);
        banco.adicionarCliente(cliente);
        System.out.println("Cliente " + nome + " cadastrado com SUCESSO!");
    }

    private void criarConta() {
        System.out.println("CRIACAO DE CONTA");
        System.out.print("Digite o CPF do titular cadastrado: ");
        String cpf = scanner.nextLine().trim();
        Cliente cliente = banco.buscarClientePorCpf(cpf);

        if (cliente == null) {
            System.out.println("Erro: Cliente nao encontrado para o CPF informado.");
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
            System.out.println("Erro: Tipo de conta invalido.");
            return;
        }

        banco.adicionarConta(novaConta);
        cliente.adicionarConta(novaConta);
        System.out.println("Conta criada com SUCESSO!");
        System.out.println("Titular: " + cliente.getNome());
        System.out.println("Numero da Conta Gerado: " + numeroGerado);
    }

    private void realizarDeposito() {
        System.out.println("DEPOSITO");
        System.out.print("Numero da conta: ");
        Conta conta = banco.buscarConta(scanner.nextLine().trim());
        if (conta == null) {
            System.out.println("Erro: Conta nao encontrada no sistema.");
            return;
        }

        System.out.print("Valor do deposito: R$ ");
        double valor = Double.parseDouble(scanner.nextLine().trim());

        try {
            Deposito op = new Deposito(conta, valor);
            op.executar();
            System.out.println("Deposito realizado com SUCESSO!");
            System.out.println("Novo Saldo da Conta (" + conta.getNumero() + "): R$ " + String.format("%.2f", conta.getSaldo()));
        } catch (Exception e) {
            System.out.println("Erro no deposito: " + e.getMessage());
        }
    }

    private void realizarSaque() {
        System.out.println("SAQUE");
        System.out.print("Numero da conta: ");
        Conta conta = banco.buscarConta(scanner.nextLine().trim());
        if (conta == null) {
            System.out.println("Erro: Conta nao encontrada no sistema.");
            return;
        }

        System.out.print("Valor do saque: R$ ");
        double valor = Double.parseDouble(scanner.nextLine().trim());

        try {
            Saque op = new Saque(conta, valor);
            op.executar();
            System.out.println("Saque efetuado com SUCESSO!");
            System.out.println("Saldo atualizado da Conta (" + conta.getNumero() + "): R$ " + String.format("%.2f", conta.getSaldo()));
        } catch (Exception e) {
            System.out.println("Erro no saque: " + e.getMessage());
        }
    }

    private void realizarTransferencia() {
        System.out.println("TRANSFERENCIA TRADICIONAL");
        System.out.print("Digite o numero da conta de ORIGEM: ");
        Conta origem = banco.buscarConta(scanner.nextLine().trim());
        if (origem == null) {
            System.out.println("Erro: Conta de origem nao existe.");
            return;
        }

        System.out.print("Digite o numero da conta de DESTINO: ");
        Conta destino = banco.buscarConta(scanner.nextLine().trim());
        if (destino == null) {
            System.out.println("Erro: Conta de destino nao existe.");
            return;
        }

        System.out.print("Valor da transferencia: R$ ");
        double valor = Double.parseDouble(scanner.nextLine().trim());

        try {
            Transferencia op = new Transferencia(origem, destino, valor);
            op.executar();
            System.out.println("Transferencia concluida com SUCESSO!");
            System.out.println("Saldo Origem (" + origem.getNumero() + "): R$ " + String.format("%.2f", origem.getSaldo()));
        } catch (Exception e) {
            System.out.println("Erro na transferencia: " + e.getMessage());
        }
    }

    private void cadastrarChavePix() {
        System.out.println("CADASTRO DE CHAVE PIX");
        System.out.print("Numero da conta vinculada: ");
        Conta conta = banco.buscarConta(scanner.nextLine().trim());
        if (conta == null) {
            System.out.println("Erro: Conta nao encontrada no sistema.");
            return;
        }

        System.out.print("Informe a chave PIX: ");
        String valorChave = scanner.nextLine().trim();

        if (banco.buscarChavePix(valorChave) != null) {
            System.out.println("Erro: Esta chave PIX ja esta cadastrada no sistema.");
            return;
        }

        ChavePix chave = new ChavePix(valorChave, TipoChavePix.EMAIL, conta);
        conta.getCliente().adicionarChavePix(chave);
        System.out.println("Chave PIX '" + valorChave + "' cadastrada com SUCESSO para " + conta.getCliente().getNome() + "!");
    }

    private void realizarPix() {
        System.out.println("PIX (DEBITO EM CONTA)");
        System.out.print("Digite o numero da CONTA DE ORIGEM: ");
        Conta origem = banco.buscarConta(scanner.nextLine().trim());
        if (origem == null) {
            System.out.println("Erro: Conta de origem nao encontrada.");
            return;
        }

        System.out.print("Digite a CHAVE PIX DO DESTINATARIO: ");
        String chave = scanner.nextLine().trim();
        ChavePix chavePixDestino = banco.buscarChavePix(chave);

        if (chavePixDestino == null) {
            System.out.println("Erro: A chave PIX informada NAO EXISTE!");
            return;
        }

        Conta destino = chavePixDestino.getConta();
        if (destino == null) {
            System.out.println("Erro: A conta vinculada a esta chave PIX nao existe.");
            return;
        }

        System.out.println("Destinatario: " + destino.getCliente().getNome() + " (Conta: " + destino.getNumero() + ")");
        System.out.print("Digite o VALOR do Pix: R$ ");
        double valor = Double.parseDouble(scanner.nextLine().trim());

        try {
            Pix op = new Pix(origem, destino, valor, chave);
            op.executar();
            System.out.println("PIX REALIZADO COM SUCESSO!");
            System.out.println("Saldo atualizado da Conta Origem: R$ " + String.format("%.2f", origem.getSaldo()));
        } catch (Exception e) {
            System.out.println("Erro na execucao do Pix: " + e.getMessage());
        }
    }

    private void realizarPixNoCredito() {
        System.out.println("PIX NO CREDITO");
        System.out.print("Digite o CPF do Titular do Cartao: ");
        Cliente cliente = banco.buscarClientePorCpf(scanner.nextLine().trim());

        if (cliente == null) {
            System.out.println("Erro: Cliente nao encontrado.");
            return;
        }

        if (cliente.getContas().isEmpty()) {
            System.out.println("Erro: O cliente nao possui conta cadastrada.");
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
            System.out.println("Erro: Cliente nao possui cartao de credito aprovado.");
            return;
        }

        System.out.print("Digite a CHAVE PIX DO DESTINATARIO: ");
        String chave = scanner.nextLine().trim();
        ChavePix chavePixDestino = banco.buscarChavePix(chave);

        if (chavePixDestino == null) {
            System.out.println("Erro: A chave PIX de destino NAO EXISTE no sistema.");
            return;
        }

        System.out.print("Valor do Pix no Credito: R$ ");
        double valor = Double.parseDouble(scanner.nextLine().trim());

        try {
            cc.realizarPixNoCredito(chave, valor);
            System.out.println("PIX no Credito REALIZADO com sucesso!");
            System.out.println("Lancado na fatura do cartao " + cc.getNumero());
            System.out.println("Limite Disponivel no Cartao: R$ " + String.format("%.2f", cc.getLimiteDisponivel()));
        } catch (Exception e) {
            System.out.println("Erro no Pix Credito: " + e.getMessage());
        }
    }

    private void solicitarCartao() {
        System.out.println("SOLICITAR CARTAO DE CREDITO");
        System.out.print("Digite o CPF do Titular: ");
        Cliente cliente = banco.buscarClientePorCpf(scanner.nextLine().trim());

        if (cliente == null) {
            System.out.println("Erro: Cliente nao cadastrado no banco.");
            return;
        }

        if (cliente.getContas().isEmpty()) {
            System.out.println("Erro: O cliente NAO possui nenhuma conta bancaria cadastrada!");
            return;
        }

        System.out.println("Contas vinculadas ao cliente:");
        for (Conta c : cliente.getContas()) {
            System.out.println("Conta Numero: " + c.getNumero());
        }

        System.out.print("Digite o numero da conta para vincular o cartao: ");
        Conta conta = banco.buscarConta(scanner.nextLine().trim());

        if (conta == null || !cliente.getContas().contains(conta)) {
            System.out.println("Erro: Conta invalida ou nao pertence a este cliente.");
            return;
        }

        System.out.print("Informe o Limite de Credito desejado: R$ ");
        double limite = Double.parseDouble(scanner.nextLine().trim());

        SolicitacaoCartao solicitacao = new SolicitacaoCartao(cliente, conta, limite);
        banco.adicionarSolicitacaoCartao(solicitacao);

        System.out.println("Solicitacao enviada com SUCESSO!");
        System.out.println("Status: AGUARDANDO APROVACAO DO GERENTE (ID do pedido: #" + solicitacao.getId() + ")");
    }

private void gerarCartaoVirtual() {
        System.out.println("SOLICITAR CARTAO VIRTUAL");
        System.out.print("Digite o CPF do Titular: ");
        Cliente cliente = banco.buscarClientePorCpf(scanner.nextLine().trim());

        if (cliente == null) {
            System.out.println("Erro: Cliente nao encontrado.");
            return;
        }

        if (cliente.getContas().isEmpty()) {
            System.out.println("Erro: Voce precisa possuir uma conta ativa para solicitar um Cartao Virtual.");
            return;
        }

        System.out.println("Contas vinculadas ao cliente:");
        for (Conta c : cliente.getContas()) {
            System.out.println("Conta Numero: " + c.getNumero());
        }

        System.out.print("Digite o numero da conta para vincular o cartao virtual: ");
        Conta conta = banco.buscarConta(scanner.nextLine().trim());

        if (conta == null || !cliente.getContas().contains(conta)) {
            System.out.println("Erro: Conta invalida ou nao pertence a este cliente.");
            return;
        }

        System.out.print("Informe o limite desejado para o Cartao Virtual: R$ ");
        double limite = Double.parseDouble(scanner.nextLine().trim());

        if (limite <= 0) {
            System.out.println("Erro: O limite deve ser maior que zero.");
            return;
        }

        
        SolicitacaoCartao solicitacao = new SolicitacaoCartao(cliente, conta, limite, true);
        banco.adicionarSolicitacaoCartao(solicitacao);

        System.out.println("Solicitacao de Cartao Virtual enviada com SUCESSO!");
        System.out.println("Status: AGUARDANDO APROVACAO DO GERENTE (ID do pedido: #" + solicitacao.getId() + ")");
    }



    private void consultarCartoes() {
        System.out.println("CONSULTAR CARTOES");
        System.out.print("Digite o CPF do Titular: ");
        Cliente cliente = banco.buscarClientePorCpf(scanner.nextLine().trim());

        if (cliente == null) {
            System.out.println("Erro: Cliente nao encontrado.");
            return;
        }

        if (cliente.getCartoes().isEmpty()) {
            System.out.println("Voce ainda nao possui cartoes cadastrados.");
        } else {
            System.out.println("Meus Cartoes:");
            for (Cartao c : cliente.getCartoes()) {
                System.out.println(c);
            }
        }
    }

    private void consultarFatura() {
        System.out.println("CONSULTA DE FATURA");
        System.out.print("CPF do Titular: ");
        Cliente cliente = banco.buscarClientePorCpf(scanner.nextLine().trim());

        if (cliente == null) {
            System.out.println("Erro: Cliente nao encontrado.");
            return;
        }

        boolean encontrouCartao = false;
        for (Cartao c : cliente.getCartoes()) {
            if (c instanceof CartaoCredito cc) {
                encontrouCartao = true;
                System.out.println("Cartao de Credito Final: " + cc.getNumero());
                System.out.println("Limite Total: R$ " + String.format("%.2f", cc.getLimite()));
                System.out.println("Limite Disponivel: R$ " + String.format("%.2f", cc.getLimiteDisponivel()));
                System.out.println("LANCAMENTOS NA FATURA:");
                if (cc.getFatura().getItens().isEmpty()) {
                    System.out.println("(Nenhum lancamento registrado nesta fatura)");
                } else {
                    for (String item : cc.getFatura().getItens()) {
                        System.out.println(item);
                    }
                }
                System.out.println("TOTAL A PAGAR NA FATURA: R$ " + String.format("%.2f", cc.getFatura().getValorAPagar()));
            }
        }

        if (!encontrouCartao) {
            System.out.println("Nenhum cartao de credito ativo/aprovado para este cliente.");
        }
    }

    private void solicitarEmprestimo() {
        System.out.println("SOLICITACAO DE EMPRESTIMO");
        System.out.print("Digite o numero da Conta: ");
        Conta conta = banco.buscarConta(scanner.nextLine().trim());

        if (conta == null) {
            System.out.println("Erro: Conta nao encontrada.");
            return;
        }

        System.out.print("Valor do emprestimo solicitado: R$ ");
        double valor = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Quantidade de parcelas: ");
        int parcelas = Integer.parseInt(scanner.nextLine().trim());

        Emprestimo emp = new Emprestimo(conta, valor, parcelas);
        banco.adicionarEmprestimo(emp);

        System.out.println("Solicitacao de Emprestimo #" + emp.getId() + " registrada com SUCESSO!");
        System.out.println("O valor so sera creditado na conta apos a APROVACAO DO GERENTE.");
    }

    private void consultarExtrato() {
        System.out.println("DADOS DA CONTA E EXTRATO COMPLETO");
        System.out.print("Digite o numero da Conta: ");
        Conta conta = banco.buscarConta(scanner.nextLine().trim());

        if (conta == null) {
            System.out.println("Erro: Conta nao encontrada no sistema.");
            return;
        }

        Cliente titular = conta.getCliente();
        System.out.println("DADOS DO TITULAR");
        System.out.println("Nome: " + titular.getNome());
        System.out.println("CPF: " + titular.getCpf());
        System.out.println("E-mail: " + titular.getEmail());
        System.out.println("Telefone: " + titular.getTelefone());
        System.out.println("DADOS DA CONTA");
        System.out.println("Numero da Conta: " + conta.getNumero());
        System.out.println("Tipo: " + conta.getClass().getSimpleName());
        System.out.println("Status: " + (conta.isBloqueada() ? "BLOQUEADA" : "ATIVA"));
        System.out.println("Saldo Atual: R$ " + String.format("%.2f", conta.getSaldo()));

        if (conta instanceof ContaCorrente cc) {
            System.out.println("Limite Especial: R$ " + String.format("%.2f", cc.getLimite()));
        }

        System.out.println("CHAVES PIX CADASTRADAS:");
        if (titular.getChavesPix().isEmpty()) {
            System.out.println("(Nenhuma chave Pix vinculada)");
        } else {
            for (ChavePix cp : titular.getChavesPix()) {
                if (cp.getConta().getNumero().equalsIgnoreCase(conta.getNumero())) {
                    System.out.println(cp.getValor() + " [" + cp.getTipo() + "]");
                }
            }
        }

        System.out.println("HISTORICO DE OPERACOES (EXTRATO):");
        if (conta.getOperacoes().isEmpty()) {
            System.out.println("(Nenhuma operacao realizada nesta conta ate o momento)");
        } else {
            for (Operacao op : conta.getOperacoes()) {
                System.out.println(op.getDescricao() + " | Valor: R$ " + String.format("%.2f", op.getValor()));
            }
        }
    }

    private void areaGerente() {
        System.out.print("Digite a senha do Gerente (padrao: admin123): ");
        String senha = scanner.nextLine().trim();

        if (!gerentePadrao.autenticar(senha)) {
            System.out.println("Senha incorreta! Acesso negado a Area do Gerente.");
            return;
        }

        int subOpcao = -1;
        while (subOpcao != 0) {
            System.out.println("PAINEL ADMINISTRATIVO DO GERENTE");
            System.out.println("Gerente: " + gerentePadrao.getNome());
            
            System.out.println("1. Aprovar/Recusar Emprestimos Pendentes (" + contarEmprestimosPendentes() + " pendentes)");
            System.out.println("2. Aprovar/Recusar Cartoes de Credito Pendentes (" + contarCartoesPendentes() + " pendentes)");
            System.out.println("3. Bloquear / Desbloquear Conta");
            System.out.println("4. Relatorio Geral de Contas do Banco");
            System.out.println("0. Voltar ao Menu Principal");
            
            System.out.print("Escolha uma opcao: ");

            try {
                subOpcao = Integer.parseInt(scanner.nextLine().trim());
                switch (subOpcao) {
                    case 1 -> gerenciarEmprestimos();
                    case 2 -> gerenciarCartoes();
                    case 3 -> alterarStatusConta();
                    case 4 -> exibirRelatorioBanco();
                    case 0 -> System.out.println("Saindo do painel do gerente...");
                    default -> System.out.println("Opcao invalida!");
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
        System.out.println("GERENCIAR EMPRESTIMOS PENDENTES");
        var pendentes = banco.getEmprestimos().stream()
                .filter(e -> e.getStatus() == Emprestimo.StatusEmprestimo.PENDENTE)
                .toList();

        if (pendentes.isEmpty()) {
            System.out.println("Nenhum emprestimo pendente de aprovacao.");
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
                    System.out.println("Emprestimo #" + e.getId() + " APROVADO! Valor creditado na conta.");
                } catch (Exception ex) {
                    System.out.println("Erro ao aprovar: " + ex.getMessage());
                }
            } else {
                e.recusar(gerentePadrao);
                System.out.println("Emprestimo #" + e.getId() + " RECUSADO.");
            }
        }
    }

   private void gerenciarCartoes() {
        System.out.println("GERENCIAR CARTOES PENDENTES");
        var pendentes = banco.getSolicitacoesCartao().stream()
                .filter(s -> s.getStatus() == SolicitacaoCartao.StatusSolicitacao.PENDENTE)
                .toList();

        if (pendentes.isEmpty()) {
            System.out.println("Nenhuma solicitacao de cartao pendente.");
            return;
        }

        for (SolicitacaoCartao s : pendentes) {
            String tipoCartao = s.isEhVirtual() ? "[CARTAO VIRTUAL]" : "[CARTAO CREDITO FISICO]";
            System.out.println("ID: #" + s.getId() + " | Tipo: " + tipoCartao +
                    " | Cliente: " + s.getCliente().getNome() +
                    " | Conta: " + s.getConta().getNumero() + 
                    " | Limite Desejado: R$ " + String.format("%.2f", s.getLimiteSolicitado()));
            
            System.out.print("Deseja APROVAR (A) ou RECUSAR (R)? [A/R]: ");
            String dec = scanner.nextLine().trim();

            if (dec.equalsIgnoreCase("A")) {
                s.aprovar();
                System.out.println("Solicitacao #" + s.getId() + " APROVADA! Cartao gerado e associado ao cliente.");
            } else {
                s.recusar();
                System.out.println("Solicitacao #" + s.getId() + " RECUSADA.");
            }
        }
    }
    private void alterarStatusConta() {
        System.out.println("BLOQUEAR / DESBLOQUEAR CONTA");
        System.out.print("Digite o numero da Conta: ");
        Conta conta = banco.buscarConta(scanner.nextLine().trim());

        if (conta == null) {
            System.out.println("Conta nao encontrada.");
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
        System.out.println("RELATORIO GERAL DO BANCO");
        System.out.println("Total de Clientes Cadastrados: " + banco.getClientes().size());
        System.out.println("Total de Contas Abertas: " + banco.getContas().size());

        double saldoTotal = 0.0;
        for (Conta c : banco.getContas()) {
            saldoTotal += c.getSaldo();
            System.out.println("Conta: " + c.getNumero() + " | Titular: " + c.getCliente().getNome() +
                    " | Tipo: " + c.getClass().getSimpleName() + " | Saldo: R$ " + String.format("%.2f", c.getSaldo()) +
                    " | Status: " + (c.isBloqueada() ? "BLOQUEADA" : "ATIVA"));
        }
        
        System.out.println("TOTAL CUSTODIADO NO BANCO: R$ " + String.format("%.2f", saldoTotal));
    }

    private ChavePix buscarContaPorPix(String chave) {
        return banco.buscarChavePix(chave);
    }
}
