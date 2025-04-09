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
                System.out.println("5. Editar Usuário");
                System.out.println("6. Excluir Usuário");
                System.out.println("7. Registrar Pagamento");
                System.out.println("8. Listar Pagamentos");
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
                            System.out.println(a.getId() + " | " + a.getNome() + " | Email: " + a.getEmail() +
                                    " | Pagamento: " + (a.isStatusPagamento() ? "Em dia" : "Atrasado"));
                        }
                    }

                    case 4 -> {
                        System.out.println("=== Lista de Funcionários ===");
                        List<Funcionario> funcionarios = funcionarioDAO.listarFuncionariosCompletos();
                        for (Funcionario f : funcionarios) {
                            System.out.println(f.getId() + " | " + f.getNome() + " | Cargo: " + f.getCargo());
                        }
                    }

                    case 5 -> {
                        System.out.println("=== Editar Usuário ===");
                        List<Usuario> usuarios = usuarioDAO.listarUsuarios();
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

                        Usuario u = new Usuario(idEditar, novoNome, novoEmail, novaSenha) {};
                        boolean atualizado = usuarioDAO.atualizarUsuario(u);

                        if (atualizado) {
                            System.out.println("Usuário atualizado com sucesso!");
                        } else {
                            System.out.println("Falha ao atualizar usuário.");
                        }
                    }

                    case 6 -> {
                        System.out.println("=== Excluir Usuário ===");
                        List<Usuario> usuarios = usuarioDAO.listarUsuarios();
                        for (Usuario u : usuarios) {
                            System.out.println(u.getId() + " | " + u.getNome() + " | Email: " + u.getEmail());
                        }

                        System.out.print("ID do usuário para excluir: ");
                        int idExcluir = scanner.nextInt();
                        scanner.nextLine();

                        boolean excluido = usuarioDAO.excluirUsuario(idExcluir);

                        if (excluido) {
                            System.out.println("Usuário excluído com sucesso!");
                        } else {
                            System.out.println("Falha ao excluir usuário.");
                        }
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

                        Pagamento pagamento = new Pagamento(idAluno, dataPagamento, dataVencimento, valor, status);
                        pagamentoDAO.inserirPagamento(pagamento);
                        System.out.println("Pagamento registrado com sucesso!");
                    }

                    case 8 -> {
                        System.out.println("=== Lista de Pagamentos ===");
                        List<Pagamento> pagamentos = pagamentoDAO.listarPagamentos();
                        for (Pagamento p : pagamentos) {
                            System.out.println("ID: " + p.getId() + " | Aluno ID: " + p.getIdAluno() +
                                    " | Valor: R$" + p.getValor() +
                                    " | Pagamento: " + p.getDataPagamento() +
                                    " | Vencimento: " + p.getDataVencimento() +
                                    " | Status: " + p.getStatus());
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
