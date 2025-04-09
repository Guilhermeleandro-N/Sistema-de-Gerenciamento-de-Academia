# Sistema de Gerenciamento de Academia

## Proposta de Projeto de POO
**Autores:**  
Maria Clara Landim Braz  
Guilherme Leandro Nogueira  

---

## 1. Tema do Projeto
Desenvolvimento de um sistema para academias, com funcionalidades de acompanhamento dos alunos e registro de seus resultados.

## 2. Objetivo da Aplicação
O sistema tem como objetivo gerenciar uma academia. Ele incluirá funcionalidades como:
- Cadastro de alunos e funcionários.
- Controle de pagamentos.
- Verificação da mensalidade.

## 3. Funcionalidades
- **Cadastro de alunos**
- **Cadastro de funcionários**
- **Gerenciamento de pagamentos dos alunos**
- **Validação da Matrícula**

## 4. Funcionalidade Geral
CRUD (Create, Read, Update, Delete):
- **Criação:** Cadastro de alunos e funcionários.
- **Leitura:** Listagem dos dados cadastrados.
- **Atualização:** Atualização de dados, progresso e pagamentos.
- **Exclusão:** Remoção de registros em casos de desistência ou dados incorretos.

## 5. Aplicação dos Conceitos de POO
### **Classes e Objetos**
- Criar classes para `Aluno`, `Funcionário`, `Pagamento`, `Academia` e `Progresso`.

### **Construtores**
- Uso de construtores para inicializar os objetos ao criar novos alunos e funcionários.

### **Encapsulamento**
- Aplicação de modificadores de acesso (`private`, `public`) para proteger dados sensíveis, como informações de pagamento.

### **Pacotes**
- Organização modular do sistema, separando classes em pacotes como:
  - `model``
  - `view`
  - 'DAO'

### **Classe Abstrata**
- Implementação da classe abstrata `Usuario` para representar atributos e métodos comuns de `Aluno` e `Funcionário`.

### **Interface**
- Criação da interface `AcessoAcademia` para definir o comportamento de controle de entrada dos alunos.

### **Herança**
- As classes `Aluno` e `Funcionário` herdam da classe `Usuario`.

### **Polimorfismo**
- Métodos sobrescritos para tratamento diferenciado de alunos e funcionários no sistema.
