package dao;

import model.Aluno;
import model.Funcionario;
import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private final Connection conexao;

    public UsuarioDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // INSERIR USUÁRIO (Aluno ou Funcionário)
    public int inserirUsuario(Usuario usuario, String tipo) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, email, senha, tipo) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, tipo); // "aluno" ou "funcionario"

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGerado = rs.getInt(1);
                    usuario.setId(idGerado);
                    return idGerado;
                }
            }
        }

        return -1;
    }

    // BUSCAR USUÁRIO POR ID
    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirUsuario(rs);
                }
            }
        }

        return null;
    }

    // LISTAR TODOS OS USUÁRIOS
    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario u = construirUsuario(rs);
                if (u != null) lista.add(u);
            }
        }

        return lista;
    }

    // DELETAR USUÁRIO
    public boolean deletar(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }


    // MÉTODO AUXILIAR PARA CRIAR OBJETO USUARIO DO RESULTSET
    private Usuario construirUsuario(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nome = rs.getString("nome");
        String email = rs.getString("email");
        String senha = rs.getString("senha");
        String tipo = rs.getString("tipo");

        if ("aluno".equalsIgnoreCase(tipo)) {
            return new Aluno(id, nome, email, senha);
        } else if ("funcionario".equalsIgnoreCase(tipo)) {
            return new Funcionario(id, nome, email, senha);
        }

        return null;
    }
}
