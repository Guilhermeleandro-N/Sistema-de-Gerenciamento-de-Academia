package dao;

import model.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {
    private final Connection conexao;

    public FuncionarioDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserirFuncionario(int idUsuario, String cargo) throws SQLException {
        String sql = "INSERT INTO funcionarios (id_usuario, cargo) VALUES (?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setString(2, cargo);
            stmt.executeUpdate();
        }
    }

    public List<Funcionario> listarCompletos() throws SQLException {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = """
                SELECT u.id, u.nome, u.email, u.senha,
                       f.cargo
                FROM usuarios u
                JOIN funcionarios f ON u.id = f.id_usuario
            """;

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Funcionario f = new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                );
                f.setCargo(rs.getString("cargo"));
                funcionarios.add(f);
            }
        }

        return funcionarios;
    }
}
