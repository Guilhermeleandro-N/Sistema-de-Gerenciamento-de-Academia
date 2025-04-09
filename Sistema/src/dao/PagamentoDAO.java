package dao;

import model.Pagamento;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAO {
    private final Connection conexao;

    public PagamentoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserirPagamento(Pagamento pagamento) throws SQLException {
        String sql = "INSERT INTO pagamentos (id_aluno, valor, data_pagamento, data_vencimento, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, pagamento.getIdAluno());
            stmt.setDouble(2, pagamento.getValor());
            stmt.setDate(3, Date.valueOf(pagamento.getDataPagamento()));
            stmt.setDate(4, Date.valueOf(pagamento.getDataVencimento()));
            stmt.setString(5, pagamento.getStatus());
            stmt.executeUpdate();
        }
    }

    public List<Pagamento> listarPagamentosPorAluno(int idAluno) throws SQLException {
        List<Pagamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM pagamentos WHERE id_aluno = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pagamento pagamento = new Pagamento(
                        rs.getInt("id_aluno"),
                        rs.getDate("data_pagamento").toLocalDate(),
                        rs.getDate("data_vencimento").toLocalDate(),
                        rs.getDouble("valor"),
                        rs.getString("status")
                );
                pagamento.setId(rs.getInt("id"));
                lista.add(pagamento);
            }
        }
        return lista;
    }

    // (Você pode incluir update e delete depois, se quiser)
}
