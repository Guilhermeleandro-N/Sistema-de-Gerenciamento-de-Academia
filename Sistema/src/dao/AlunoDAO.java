package dao;

import model.Aluno;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {
    private final Connection conexao;

    public AlunoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // Inserir aluno com base no ID do usuário já inserido
    public void inserirAluno(int idUsuario) throws SQLException {
        String sql = "INSERT INTO alunos (id_usuario, status_pagamento, data_vencimento_pagamento) VALUES (?, false, NULL)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();
        }
    }

    // Listar todos os alunos com dados completos
    public List<Aluno> listarAlunosCompletos() throws SQLException {
        List<Aluno> alunos = new ArrayList<>();
        String sql = """
            SELECT u.id, u.nome, u.email, u.cpf, u.senha,
                   a.data_vencimento_pagamento, a.status_pagamento
            FROM usuarios u
            JOIN alunos a ON u.id = a.id_usuario
        """;

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Aluno aluno = new Aluno(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                );

                Date dataSql = rs.getDate("data_vencimento_pagamento");
                if (dataSql != null) {
                    aluno.setDataVencimentoPagamento(dataSql.toLocalDate());
                }

                aluno.setStatusPagamento(rs.getBoolean("status_pagamento"));
                alunos.add(aluno);
            }
        }

        return alunos;
    }

    // Atualizar vencimento e status de pagamento do aluno
    public boolean atualizarPagamento(int idAluno, LocalDate novaData, boolean status) throws SQLException {
        String sql = """
            UPDATE alunos
            SET data_vencimento_pagamento = ?, status_pagamento = ?
            WHERE id_usuario = ?
        """;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(novaData));
            stmt.setBoolean(2, status);
            stmt.setInt(3, idAluno);
            return stmt.executeUpdate() > 0;
        }
    }

    // Excluir aluno pelo id_usuario
    public boolean deletarPorIdUsuario(int idUsuario) throws SQLException {
        String sql = "DELETE FROM alunos WHERE id_usuario = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            return stmt.executeUpdate() > 0;
        }
    }
}
