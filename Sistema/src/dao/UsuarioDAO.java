package dao;

import model.Usuario;
import model.Aluno;
import model.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private final Connection conexao;

    public UsuarioDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public int inserirUsuario(Usuario usuario, String tipo) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, email, senha, tipo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, tipo);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

    public List<Usuario> listarUsuarios() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String senha = rs.getString("senha");

                if (tipo.equals("aluno")) {
                    lista.add(new Aluno(id, nome, email, senha));
                } else if (tipo.equals("funcionario")) {
                    lista.add(new Funcionario(id, nome, email, senha));
                }
            }
        }
        return lista;
    }

    public boolean atualizarUsuario(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nome = ?, email = ?, senha = ? WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setInt(4, usuario.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean excluirUsuario(int id) throws SQLException {
        String tipo = buscarTipoUsuario(id);

        if (tipo == null) return false;

        if (tipo.equals("aluno")) {
            String delAluno = "DELETE FROM alunos WHERE id_usuario = ?";
            try (PreparedStatement stmt = conexao.prepareStatement(delAluno)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } else if (tipo.equals("funcionario")) {
            String delFunc = "DELETE FROM funcionarios WHERE id_usuario = ?";
            try (PreparedStatement stmt = conexao.prepareStatement(delFunc)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        }

        String delUsuario = "DELETE FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(delUsuario)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    private String buscarTipoUsuario(int id) throws SQLException {
        String sql = "SELECT tipo FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("tipo");
            }
        }
        return null;
    }
}
