# 🏦 Sistema Bancário POO — Java

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![POO](https://img.shields.io/badge/Paradigma-Orientação_a_Objetos-blue?style=for-the-badge)

Projeto acadêmico desenvolvido para a disciplina de **Programação Orientada a Objetos (POO)**. O sistema simula o funcionamento completo de uma instituição bancária via linha de comando, aplicando conceitos avançados de arquitetura de software, design de classes, validações de domínio e tratamento robusto de exceções.

---

## 🎓 Aplicação dos Pilares de POO

O projeto foi construído rigorosamente sobre os conceitos exigidos no paradigma orientado a objetos:

1. **Abstração:**
   - Representação de entidades do mundo real através de classes como `Pessoa`, `Cliente`, `Gerente`, `Conta`, `Operacao`, `Cartao`, `Emprestimo` e `ChavePix`.
2. **Encapsulamento:**
   - Modificadores de acesso (`private`, `protected`) protegendo o estado dos objetos, expondo o acesso apenas por métodos seletores/modificadores (`getters`/`setters`) e operações controladas (`sacar`, `depositar`, `transferir`).
3. **Herança:**
   - Reuso de código e hierarquia clara entre superclasses abstratas e subclasses concretas:
     - `Pessoa` ➔ `Cliente`, `Gerente`
     - `Conta` ➔ `ContaCorrente`, `ContaPoupanca`, `ContaSalario`, `ContaEmpresarial`, `ContaUniversitaria`
     - `Cartao` ➔ `CartaoCredito`, `CartaoDebito`, `CartaoVirtual`
     - `Operacao` ➔ `Deposito`, `Saque`, `Transferencia`, `Pix`, `PagamentoBoleto`
4. **Polimorfismo:**
   - Sobrescrita de métodos (`@Override`) e tratamento uniforme de objetos genéricos (ex: lista de `Conta` aceitando qualquer subclasse concreta; execução polimórfica de `Operacao.executar()`).
5. **Interfaces:**
   - Desacoplamento através de contratos comportamentais (`Autenticavel`, `Transferivel`, `Pagavel`, `Tributavel`).

---

## 📐 Diagrama de Classes e Hierarquia (UML em ASCII)

```text
                  +-------------------+
                  |  <<interface>>    |
                  |   Autenticavel    |
                  +-------------------+
                            ^
                            |
                  +-------------------+
                  |     Pessoa        |  (Abstrata)
                  +-------------------+
                  | - nome: String    |
                  | - cpf: String     |
                  +-------------------+
                            ^
              +-------------+-------------+
              |                           |
    +-------------------+       +-------------------+
    |      Cliente      |       |      Gerente      |
    +-------------------+       +-------------------+
    | - senha: String   |       | - matricula:Str.  |
    +-------------------+       +-------------------+
       |            |
       | 1..*       | 1..*
       v            v
    +-------+   +--------+
    | Conta |   | Cartao |
    +-------+   +--------+

----------------------------------------------------------------------

                  +-------------------+
                  |  <<interface>>    |
                  |   Transferivel    |
                  +-------------------+
                            ^
                            |
                  +-------------------+
                  |       Conta       |  (Abstrata)
                  +-------------------+
                  | # numero: String  |
                  | # saldo: double   |
                  | # cliente: Cliente|
                  +-------------------+
                            ^
      +----------+----------+----------+----------+
      |          |          |          |          |
+----------++----------++----------++----------++---------------+
|  Conta   ||  Conta   ||  Conta   ||  Conta   ||    Conta      |
|Corrente  || Poupanca || Salario  ||Empresari.||Universitaria  |
+----------++----------++----------++----------++---------------+

----------------------------------------------------------------------

                  +-------------------+
                  |     Operacao      |  (Abstrata)
                  +-------------------+
                  | # valor: double   |
                  | # tipo: String    |
                  +-------------------+
                  | + executar()*     |
                  +-------------------+
                            ^
      +----------+----------+----------+
      |          |          |          |
+----------++----------++----------++----------+
| Deposito ||  Saque   ||Transfer. ||   Pix    |
+----------++----------++----------++----------+
