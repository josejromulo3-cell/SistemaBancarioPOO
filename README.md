# Sistema Bancário Orientado a Objetos em Java
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![POO](https://img.shields.io/badge/Paradigma-Orientação_a_Objetos-blue?style=for-the-badge)

Projeto acadêmico da disciplina de **Programação Orientada a Objetos** — Universidade Federal da Paraíba (UFPB), Campus I.

Simulação via linha de comando do funcionamento de uma instituição financeira, contemplando clientes, gerentes, contas, cartões, operações financeiras (incluindo Pix) e empréstimos.

**Integrantes:** José Rômulo Oliveira da Silva e Maryana Marques de Medeiros

---

## 📖 Sobre o projeto

Este trabalho aplica na prática os quatro pilares da Programação Orientada a Objetos — **Abstração, Encapsulamento, Herança e Polimorfismo** — aliados a conceitos avançados como tratamento de exceções personalizadas, interfaces de contrato e uso do Java Collections Framework para gerenciar coleções dinâmicas de clientes, contas e operações.

O problema central resolvido foi projetar uma estrutura de classes capaz de representar fielmente regras de negócio bancárias reais — múltiplas entidades interligadas (contas, cartões, operações, empréstimos), com validações robustas em tempo de execução (saldo insuficiente, contas bloqueadas ou inexistentes), evitando código acoplado, repetitivo e propenso a inconsistências.

## ✨ Funcionalidades

### Gestão de Clientes e Pessoas
- Cadastro de dados pessoais com autenticação via senha segura (`Autenticavel`)
- Abertura automática de contas com geração dinâmica de número no formato `XXXXX-X`
- Suporte a múltiplos tipos de conta: **Corrente** (com limite especial), **Poupança**, **Salário**, **Empresarial** e **Universitária**

### Operações Financeiras
- Depósitos, saques e transferências entre contas, com registro em histórico (extrato)
- **Ecossistema Pix**: cadastro de chaves (CPF, e-mail, celular), transferência via débito em conta e Pix no crédito através da fatura do cartão (taxa de conveniência de 3%)

### Cartões e Faturas
- Solicitação de cartão de crédito vinculada obrigatoriamente a uma conta existente
- Lançamentos discriminados e controle de limite disponível

### Empréstimos
- Simulação e solicitação de empréstimos bancários

### Painel Administrativo do Gerente
- Aprovação ou recusa de solicitações de cartão de crédito
- Análise e liberação de empréstimos pendentes
- Bloqueio e desbloqueio operacional de contas
- Relatório consolidado do montante sob custódia do banco
- Consulta de dados e extrato detalhado do titular

## 🏗️ Modelagem

### Interfaces
| Interface | Contrato | Implementada por |
|---|---|---|
| `Autenticavel` | `autenticar(senha): boolean` | `Cliente`, `Gerente` |
| `Transferivel` | `transferir(destino, valor): void` | `Conta` |
| `Tributavel` | `calculaTributo(): double` | `ContaCorrente` |
| `Pagavel` | `getValorAPagar(): double`, `processarPagamento(): void` | `Fatura` |

### Hierarquias principais
- **Pessoa** *(abstrata)* → `Cliente`, `Gerente`
- **Conta** *(abstrata, implementa `Transferivel`)* → `ContaCorrente`, `ContaPoupanca`, `ContaEmpresarial`, `ContaSalario`, `ContaUniversitaria`
- **Cartao** *(abstrata)* → `CartaoCredito` (composição com `Fatura`), `CartaoDebito`, `CartaoVirtual`
- **Operacao** *(abstrata, método `executar()`)* → `Deposito`, `Saque`, `Transferencia`, `Pix`, `PagamentoBoleto`

### Outras entidades
- `ChavePix` (enum `TipoChavePix`: CPF, EMAIL, TELEFONE, ALEATORIA)
- `Emprestimo` (enum `StatusEmprestimo`), com `aprovar(gerente)` e `recusar(gerente)`
- `SolicitacaoCartao` (enum `StatusSolicitacao`: PENDENTE, APROVADO, RECUSADO)
- `Banco`: centraliza listas de clientes, contas, empréstimos e solicitações, com métodos de busca (`buscarClientePorCpf()`, `buscarConta()`, `buscarChavePix()`)
- `GeradorNumeroConta` / `GeradorCartao`: utilitários estáticos para geração de números de conta, cartão, CVV e validade

### Exceções personalizadas
- `ContaBloqueadaException`
- `SaldoInsuficienteException` (carrega o saldo atual)
- `ValorInvalidoException`

## 📁 Estrutura de pacotes

```
src/
├── banco/        # Gestão do repositório central do banco
├── cliente/      # Entidade Cliente e gerenciamento de chaves/contas
├── conta/        # Hierarquia de Contas Bancárias
├── gerente/      # Entidade Gerente e operações administrativas
├── pessoa/       # Superclasse abstrata Pessoa
├── pix/          # Modelo de Chaves e Tipos de Pix
├── operacao/     # Estrutura de Operações e histórico de Extrato
├── cartao/       # Cartões, Faturas e Solicitações
├── emprestimo/   # Fluxo e aprovação de Empréstimos
├── excecao/      # Exceções personalizadas do sistema
├── interfaces/   # Contratos do sistema (Autenticavel, etc.)
├── util/         # Utilitários (Gerador de número de conta)
└── main/         # Ponto de entrada (Main) e Menu Interativo
```

## 🔧 Decisões de projeto

- **Classes abstratas**: evitam a instanciação direta de objetos genéricos (`new Conta()`, `new Operacao()`), forçando a especialização e garantindo polimorfismo seguro
- **Encapsulamento estrito**: todos os atributos são `private`/`protected`, com acesso mediado por getters, setters e métodos de negócio com validações prévias
- **Invocação explícita de construtores (`super`)**: garante a inicialização correta dos atributos da superclasse
- **Acoplamento fraco via pacotes**: separação modular para alta coesão e facilidade de manutenção

## 🛠️ Tecnologias

- **Linguagem:** Java (JDK 17+)
- **Features utilizadas:** Switch expressions (`switch (tipo) ->`), pattern matching com `instanceof`, Streams API
- **Bibliotecas:** exclusivamente a API padrão do JDK (`java.util.List`, `ArrayList`, `Scanner`, `Random`)
- **Ferramentas auxiliares:** Git, GitHub, Mermaid.js (diagramas UML)

## ▶️ Como executar

```bash
# Compilar
javac -d bin $(find src -name "*.java")

# Executar
java -cp bin main.Main
```

## 📚 Referências


- MANELIMA. dio-Banco-Digital: Desafio: Criando um Banco Digital com Java e Orientação a Objetos. GitHub. Disponível em: https://github.com/Manelima/dio-Banco-Digital. Acesso em: 2026.

- SARAHTAMBALO. dio-desafio-banco-digital-poo-java. GitHub. Disponível em: https://github.com/sarahtambalo/dio-desafio-banco-digital-poo-java. Acesso em: 2026.

- ORACLE. The Java™ Tutorials: Object-Oriented Programming Concepts. Disponível em: https://docs.oracle.com/javase/tutorial/java/concepts/. Acesso em: 2026.

- ORACLE. *Java SE Documentation: Object-Oriented Programming Concepts*. Disponível em: [docs.oracle.com/javase/tutorial/java/concepts](https://docs.oracle.com/javase/tutorial/java/concepts/)

---

*Projeto desenvolvido para a disciplina de Programação Orientada a Objetos — UFPB.*
