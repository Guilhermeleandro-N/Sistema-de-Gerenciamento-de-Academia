package dao;

import model.Usuario;
import model.Aluno;
import model.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Classe responsável pela manipulação de dados da tabela "usuarios" no banco
// Aplicação de POO: Organização por pacotes (dao), uso de classes, métodos e interfaces
public class UsuarioDAO implements Listavel {

    // Encapsulamento: conexão é um atributo privado e final, injetado via construtor
    private final Connection conexao;

    // Construtor: recebe a conexão com o banco de dados
    public UsuarioDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // Método para inserir um novo usuário no banco
    public int inserirUsuario(Usuario usuario, String tipo) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, email, senha, tipo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Definindo os valores no SQL
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, tipo);
            stmt.executeUpdate();

            // Retorna o ID gerado automaticamente
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Chave primária gerada
            }
        }
        return -1; // Caso ocorra falha
    }

    // Método que retorna uma lista com todos os usuários cadastrados
    public List<Usuario> listarCompletos() throws SQLException {
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

                // Polimorfismo: instanciando objetos diferentes com base no tipo
                if (tipo.equals("aluno")) {
                    lista.add(new Aluno(id, nome, email, senha));
                } else if (tipo.equals("funcionario")) {
                    lista.add(new Funcionario(id, nome, email, senha));
                }
            }
        }
        return lista;
    }

    // Atualiza os dados de um usuário com base no ID
    public boolean atualizarUsuario(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nome = ?, email = ?, senha = ? WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setInt(4, usuario.getId());

            return stmt.executeUpdate() > 0; // Retorna true se atualizou
        }
    }

    // Exclui um usuário, e também seu registro em aluno ou funcionário
    public boolean excluirUsuario(int id) throws SQLException {
        String tipo = buscarTipoUsuario(id); // Busca se é aluno ou funcionário

        if (tipo == null) return false; // Não encontrou o tipo

        // Exclusão específica
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

        // Exclusão do usuário principal
        String delUsuario = "DELETE FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(delUsuario)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Método privado auxiliar que retorna o tipo (aluno ou funcionario) do usuário
    private String buscarTipoUsuario(int id) throws SQLException {
        String sql = "SELECT tipo FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("tipo");
            }
        }
        return null; // Se não encontrado
    }
}
