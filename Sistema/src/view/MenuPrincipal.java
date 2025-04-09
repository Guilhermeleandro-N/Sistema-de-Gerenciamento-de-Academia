package view;

import dao.AlunoDAO;
import dao.Conexao;
import dao.FuncionarioDAO;
import dao.PagamentoDAO;
import dao.UsuarioDAO;
import model.Aluno;
import model.Funcionario;
import model.Pagamento;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {

    public static void main(String[] args) {
        try (Connection conexao = Conexao.conectar()) {
            if (conexao == null) {
                System.out.println("Não foi possível conectar ao banco de dados.");
                return;
            }

            Scanner scanner = new Scanner(System.in);
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
                System.out.println("5. Ver Pagamentos de um Aluno");
                System.out.println("0. Sair");
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer

                switch (opcao) {
                    case 1 -> {
                        System.out.println("=== Cadastro de Aluno ===");
                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Senha: ");
                        String senha = scanner.nextLine();

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

                        Funcionario funcionario = new Funcionario(nome, email, senha);
                        int id = usuarioDAO.inserirUsuario(funcionario, "funcionario");
                        funcionarioDAO.inserirFuncionario(id, cargo);

                        System.out.println("Funcionário cadastrado com sucesso!");
                    }

                    case 3 -> {
                        System.out.println("=== Lista de Alunos ===");
                        List<Aluno> alunos = alunoDAO.listarAlunosCompletos();
                        for (Aluno a : alunos) {
                            System.out.println(a.getNome() + " | Email: " + a.getEmail() +
                                    " | Pagamento: " + (a.isStatusPagamento() ? "Em dia" : "Atrasado"));
                        }
                    }

                    case 4 -> {
                        System.out.println("=== Lista de Funcionários ===");
                        List<Funcionario> funcionarios = funcionarioDAO.listarFuncionariosCompletos();
                        for (Funcionario f : funcionarios) {
                            System.out.println(f.getNome() + " | Cargo: " + f.getCargo());
                        }
                    }

                    case 5 -> {
                        System.out.println("=== Pagamentos de um Aluno ===");
                        System.out.print("Digite o ID do aluno: ");
                        int idAluno = scanner.nextInt();
                        scanner.nextLine();

                        List<Pagamento> pagamentos = pagamentoDAO.listarPagamentosPorAluno(idAluno);
                        if (pagamentos.isEmpty()) {
                            System.out.println("Nenhum pagamento encontrado para este aluno.");
                        } else {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            for (Pagamento p : pagamentos) {
                                System.out.println("Valor: R$" + p.getValor() +
                                        " | Data Pagamento: " + p.getDataPagamento().format(formatter) +
                                        " | Vencimento: " + p.getDataVencimento().format(formatter) +
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
