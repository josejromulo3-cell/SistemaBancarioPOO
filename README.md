# 🏦 Sistema Bancário POO

Projeto desenvolvido em **Java** aplicando os conceitos essenciais da **Programação Orientada a Objetos (POO)**: Abstração, Encapsulamento, Herança e Polimorfismo, além de tratamento de exceções personalizadas e validações rígidas de regras de negócio.

---

## 🎯 Funcionalidades e Regras de Negócio Implementadas

### 👥 Gestão de Clientes e Contas
- **Cadastro de Clientes:** Prevenção automática de cadastros duplicados com o mesmo **CPF**.
- **Criação de Contas:** Vinculação de contas a clientes cadastrados com prevenção de **números de contas duplicados**.
- **Tipos de Contas Disponíveis no Menu:** Criar e operar **Conta Corrente** e **Conta Poupança** diretamente pelo menu interativo (com suporte arquitetural no código para Conta Salário, Empresarial e Universitária).

### 💸 Operações Bancárias Integradas
- **Depósito:** Crédito com atualização de saldo em tempo real.
- **Saque:** Validação de saldo disponível e tratamento para exceções de saldo insuficiente.
- **Transferência entre Contas:**
  - Validação de saldo da conta de origem.
  - **Bloqueio de transferência própria:** Impede que uma conta transfira valores para ela mesma.
  - Atualização síncrona dos saldos envolvidos.
- **Consulta de Extrato / Saldo:** Exibição do titular e saldo atual formatado em R$.

### 🛡️ Tratamento Robusto de Exceções
- Lançamento e captura de exceções personalizadas (`SaldoInsuficienteException`, `ValorInvalidoException`, `ContaBloqueadaException`), evitando travamento da aplicação e informando o usuário com mensagens amigáveis.

---

## 📂 Estrutura do Projeto

```text
SistemaBancario/
│
├── .gitignore                      # Garante a exclusão de arquivos compilados (.class)
├── README.md                       # Documentação do projeto
│
└── src/
    ├── main/
    │   ├── Main.java               # Classe principal (execução)
    │   └── SistemaBancario.java    # Menu interativo via terminal e rotas
    │
    ├── banco/
    │   └── Banco.java              # Gerenciador de clientes, contas e buscas/validações
    │
    ├── pessoa/
    │   └── Pessoa.java             # Classe Abstrata (Superclasse)
    │
    ├── cliente/
    │   └── Cliente.java            # Herda de Pessoa (Associação com Contas e Cartões)
    │
    ├── gerente/
    │   └── Gerente.java            # Herda de Pessoa
    │
    ├── conta/
    │   ├── Conta.java              # Classe Abstrata base
    │   ├── ContaCorrente.java      # Implementação com taxa de manutenção
    │   ├── ContaPoupanca.java      # Implementação com rendimento
    │   ├── ContaSalario.java       # Modelagem de conta salário
    │   ├── ContaEmpresarial.java   # Modelagem para PJ
    │   └── ContaUniversitaria.java # Modelagem universitária
    │
    ├── cartao/
    │   ├── Cartao.java             # Classe Abstrata
    │   ├── CartaoCredito.java
    │   ├── CartaoDebito.java
    │   ├── CartaoVirtual.java
    │   └── Fatura.java
    │
    ├── operacao/
    │   ├── Operacao.java           # Classe Abstrata para transações
    │   ├── Deposito.java           # Executa operação de depósito
    │   ├── Saque.java              # Executa operação de saque
    │   ├── Pix.java                # Transação Pix
    │   ├── Transferencia.java      # Valida e executa transferência entre contas
    │   └── PagamentoBoleto.java
    │
    ├── pix/
    │   ├── ChavePix.java
    │   └── TipoChavePix.java       # Enum (CPF, EMAIL, TELEFONE, ALEATORIA)
    │
    ├── emprestimo/
    │   └── Emprestimo.java
    │
    ├── interfaces/
    │   ├── Autenticavel.java
    │   ├── Transferivel.java
    │   ├── Pagavel.java
    │   └── Tributavel.java
    │
    ├── excecao/
    │   ├── SaldoInsuficienteException.java
    │   ├── ContaBloqueadaException.java
    │   ├── ValorInvalidoException.java
    │   ├── ClienteNaoEncontradoException.java
    │   └── SenhaIncorretaException.java
    │
    └── util/
        ├── GeradorNumeroConta.java
        ├── GeradorCartao.java
        ├── ValidadorCPF.java
        └── Formatador.java
