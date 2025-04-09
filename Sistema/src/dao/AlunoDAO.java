package dao;

import model.Aluno;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// DAO responsável por operações relacionadas à tabela "alunos"
// Aplicação de POO: separação de responsabilidades, organização em pacotes, uso de herança e encapsulamento
public class AlunoDAO implements Listavel {

    // Encapsulamento: atributo privado que mantém a conexão com o banco
    private final Connection conexao;

    // Construtor que injeta a dependência da conexão
    public AlunoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // Método para inserir um novo aluno, baseado no ID de um usuário já existente
    public void inserirAluno(int idUsuario) throws SQLException {
        String sql = "INSERT INTO alunos (id_usuario, status_pagamento, data_vencimento_pagamento) VALUES (?, false, NULL)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario); // chave estrangeira
            stmt.executeUpdate();      // executa inserção
        }
    }

    // Método para listar todos os alunos com dados completos (herdados de "usuarios" e específicos de "alunos")
    public List<Aluno> listarCompletos() throws SQLException {
        List<Aluno> alunos = new ArrayList<>();

        String sql = """
            SELECT u.id, u.nome, u.email, u.senha,
                   a.data_vencimento_pagamento, a.status_pagamento
            FROM usuarios u
            JOIN alunos a ON u.id = a.id_usuario
        """;

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Herança: Aluno herda atributos de Usuario
                Aluno aluno = new Aluno(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                );

                // Conversão de java.sql.Date para java.time.LocalDate (boas práticas com Java moderno)
                Date dataSql = rs.getDate("data_vencimento_pagamento");
                if (dataSql != null) {
                    aluno.setDataVencimentoPagamento(dataSql.toLocalDate());
                }

                // Encapsulamento: acesso aos atributos via setters
                aluno.setStatusPagamento(rs.getBoolean("status_pagamento"));

                alunos.add(aluno);
            }
        }

        return alunos;
    }

    // Atualiza o vencimento e o status de pagamento de um aluno (por ID de usuário)
    public boolean atualizarPagamento(int idAluno, LocalDate novaData, boolean status) throws SQLException {
        String sql = """
            UPDATE alunos
            SET data_vencimento_pagamento = ?, status_pagamento = ?
            WHERE id_usuario = ?
        """;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(novaData)); // conversão de LocalDate para SQL Date
            stmt.setBoolean(2, status);
            stmt.setInt(3, idAluno);

            return stmt.executeUpdate() > 0; // retorna true se houve alteração
        }
    }

    // Remove um aluno com base no id_usuario (chave estrangeira)
    public boolean deletarPorIdUsuario(int idUsuario) throws SQLException {
        String sql = "DELETE FROM alunos WHERE id_usuario = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            return stmt.executeUpdate() > 0;
        }
    }
}
