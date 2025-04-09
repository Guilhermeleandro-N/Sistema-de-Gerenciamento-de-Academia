package dao;

import model.Pagamento;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAO implements Listavel{

    private final Connection conexao;

    public PagamentoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // Inserir novo pagamento
    public void inserirPagamento(Pagamento pagamento) {
        String sql = "INSERT INTO pagamentos (id_aluno, data_pagamento, data_vencimento, valor, status) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, pagamento.getIdAluno());
            stmt.setDate(2, Date.valueOf(pagamento.getDataPagamento()));
            stmt.setDate(3, Date.valueOf(pagamento.getDataVencimento()));
            stmt.setDouble(4, pagamento.getValor());
            stmt.setString(5, pagamento.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Listar todos os pagamentos
    public List<Pagamento> listarCompletos() {
        List<Pagamento> pagamentos = new ArrayList<>();
        String sql = "SELECT * FROM pagamentos";

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int idAluno = rs.getInt("id_aluno");
                Date dataPgtoSql = rs.getDate("data_pagamento");
                Date dataVencSql = rs.getDate("data_vencimento");
                double valor = rs.getDouble("valor");
                String status = rs.getString("status");

                LocalDate dataPagamento = dataPgtoSql != null ? dataPgtoSql.toLocalDate() : null;
                LocalDate dataVencimento = dataVencSql != null ? dataVencSql.toLocalDate() : null;

                Pagamento pagamento = new Pagamento(idAluno, dataPagamento, dataVencimento, valor, status);
                pagamento.setId(id);
                pagamentos.add(pagamento);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pagamentos;
    }


    // Listar pagamentos por aluno
    public List<Pagamento> listarPagamentosPorAluno(int idAluno) {
        List<Pagamento> pagamentos = new ArrayList<>();
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
                pagamentos.add(pagamento);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pagamentos;
    }
}
