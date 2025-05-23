package view;

import dao.*;
import model.Aluno;
import model.Funcionario;
import model.Pagamento;
import model.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

// Pacote: view (interface com o usuário)
public class MenuPrincipal {

    public static void main(String[] args) {
        // Tentativa de conexão com o banco de dados
        try (Connection conexao = Conexao.conectar()) {
            if (conexao == null) {
                System.out.println("Não foi possível conectar ao banco de dados.");
                return;
            }

            // Scanner para entrada do usuário
            Scanner scanner = new Scanner(System.in);

            // Instanciando os DAOs (Aplicação do conceito de Pacotes)
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexao);
            AlunoDAO alunoDAO = new AlunoDAO(conexao);
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO(conexao);
            PagamentoDAO pagamentoDAO = new PagamentoDAO(conexao);

            int opcao;
            do {
                System.out.println("\n=== MENU PRINCIPAL ===");
                System.out.println("1. Cadastrar Aluno");
                System.out.println("2. Cadastrar Funcionário");
                System.out.println("3. Listar Alunos");
                System.out.println("4. Listar Funcionários");
                System.out.println("5. Editar Usuário");
                System.out.println("6. Excluir Usuário");
                System.out.println("7. Registrar Pagamento");
                System.out.println("8. Listar Pagamentos");
                System.out.println("9. Listar Pagamentos de um Aluno");
                System.out.println("0. Sair");
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1 -> {
                        System.out.println("=== Cadastro de Aluno ===");
                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Senha: ");
                        String senha = scanner.nextLine();

                        // Construtor e Herança: Aluno é um tipo de Usuario
                        Aluno aluno = new Aluno(nome, email, senha);
                        int id = usuarioDAO.inserirUsuario(aluno, "aluno");
                        alunoDAO.inserirAluno(id);
                        System.out.println("Aluno cadastrado com sucesso!");
                    }
                    case 2 -> {
                        System.out.println("=== Cadastro de Funcionário ===");
                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Senha: ");
                        String senha = scanner.nextLine();
                        System.out.print("Cargo: ");
                        String cargo = scanner.nextLine();

                        // Construtor e Herança: Funcionario herda de Usuario
                        Funcionario funcionario = new Funcionario(nome, email, senha);
                        int id = usuarioDAO.inserirUsuario(funcionario, "funcionario");
                        funcionarioDAO.inserirFuncionario(id, cargo);
                        System.out.println("Funcionário cadastrado com sucesso!");
                    }
                    case 3 -> {
                        System.out.println("=== Lista de Alunos ===");
                        // Polimorfismo: método definido pela interface ListavelComStatus
                        List<Aluno> alunos = alunoDAO.listarCompletos();
                        for (Aluno a : alunos) {
                            System.out.println(a.getId() + " | " + a.getNome() + " | Email: " + a.getEmail() +
                                    " | Pagamento: " + (a.isStatusPagamento() ? "Em dia" : "Atrasado"));
                        }
                    }
                    case 4 -> {
                        System.out.println("=== Lista de Funcionários ===");
                        // Polimorfismo: uso do mesmo método para listar
                        List<Funcionario> funcionarios = funcionarioDAO.listarCompletos();
                        for (Funcionario f : funcionarios) {
                            System.out.println(f.getId() + " | " + f.getNome() + " | Cargo: " + f.getCargo());
                        }
                    }
                    case 5 -> {
                        System.out.println("=== Editar Usuário ===");
                        List<Usuario> usuarios = usuarioDAO.listarCompletos();
                        for (Usuario u : usuarios) {
                            System.out.println(u.getId() + " | " + u.getNome() + " | Email: " + u.getEmail());
                        }

                        System.out.print("ID do usuário para editar: ");
                        int idEditar = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Novo nome: ");
                        String novoNome = scanner.nextLine();
                        System.out.print("Novo email: ");
                        String novoEmail = scanner.nextLine();
                        System.out.print("Nova senha: ");
                        String novaSenha = scanner.nextLine();

                        // Construtor e Encapsulamento (uso de getters/setters)
                        Usuario u = new Usuario(idEditar, novoNome, novoEmail, novaSenha) {};
                        boolean atualizado = usuarioDAO.atualizarUsuario(u);

                        System.out.println(atualizado ? "Usuário atualizado com sucesso!" : "Falha ao atualizar usuário.");
                    }
                    case 6 -> {
                        System.out.println("=== Excluir Usuário ===");
                        List<Usuario> usuarios = usuarioDAO.listarCompletos();
                        for (Usuario u : usuarios) {
                            System.out.println(u.getId() + " | " + u.getNome() + " | Email: " + u.getEmail());
                        }

                        System.out.print("ID do usuário para excluir: ");
                        int idExcluir = scanner.nextInt();
                        scanner.nextLine();

                        boolean excluido = usuarioDAO.excluirUsuario(idExcluir);
                        System.out.println(excluido ? "Usuário excluído com sucesso!" : "Falha ao excluir usuário.");
                    }
                    case 7 -> {
                        System.out.println("=== Registrar Pagamento ===");
                        System.out.print("ID do aluno: ");
                        int idAluno = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Valor: ");
                        double valor = scanner.nextDouble();
                        scanner.nextLine();

                        System.out.print("Data de pagamento (YYYY-MM-DD): ");
                        LocalDate dataPagamento = LocalDate.parse(scanner.nextLine());

                        System.out.print("Data de vencimento (YYYY-MM-DD): ");
                        LocalDate dataVencimento = LocalDate.parse(scanner.nextLine());

                        System.out.print("Status (pago, em atraso, pendente): ");
                        String status = scanner.nextLine();

                        // Construtor e Encapsulamento
                        Pagamento pagamento = new Pagamento(idAluno, dataPagamento, dataVencimento, valor, status);
                        pagamentoDAO.inserirPagamento(pagamento);
                        System.out.println("Pagamento registrado com sucesso!");
                    }
                    case 8 -> {
                        System.out.println("=== Lista de Pagamentos ===");
                        List<Pagamento> pagamentos = pagamentoDAO.listarCompletos();
                        for (Pagamento p : pagamentos) {
                            System.out.println("ID: " + p.getId() + " | Aluno ID: " + p.getIdAluno() +
                                    " | Valor: R$" + p.getValor() +
                                    " | Pagamento: " + p.getDataPagamento() +
                                    " | Vencimento: " + p.getDataVencimento() +
                                    " | Status: " + p.getStatus());
                        }
                    }
                    case 9 -> {
                        System.out.println("=== Pagamentos de um Aluno ===");
                        List<Aluno> alunos = alunoDAO.listarCompletos();
                        for (Aluno a : alunos) {
                            System.out.println("ID: " + a.getId() + " | Nome: " + a.getNome());
                        }

                        System.out.print("Digite o ID do aluno: ");
                        int idAluno = scanner.nextInt();
                        scanner.nextLine();

                        List<Pagamento> pagamentos = pagamentoDAO.listarPagamentosPorAluno(idAluno);
                        if (pagamentos.isEmpty()) {
                            System.out.println("Nenhum pagamento encontrado para o aluno com ID: " + idAluno);
                        } else {
                            for (Pagamento p : pagamentos) {
                                System.out.println("ID: " + p.getId() +
                                        " | Valor: R$" + p.getValor() +
                                        " | Pagamento: " + (p.getDataPagamento() != null ? p.getDataPagamento() : "N/A") +
                                        " | Vencimento: " + (p.getDataVencimento() != null ? p.getDataVencimento() : "N/A") +
                                        " | Status: " + p.getStatus());
                            }
                        }
                    }
                    case 0 -> System.out.println("Encerrando o sistema...");
                    default -> System.out.println("Opção inválida.");
                }
            } while (opcao != 0);

        } catch (SQLException e) {
            System.err.println("Erro no banco de dados: " + e.getMessage());
        }
    }
}