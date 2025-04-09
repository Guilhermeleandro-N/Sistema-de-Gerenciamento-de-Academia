package dao;

import model.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Classe DAO responsável por interagir com a tabela "funcionarios" do banco de dados
// Aplicação de POO: Organização por pacotes, separação de responsabilidades, uso de classes e métodos
public class FuncionarioDAO implements Listavel{

    // Encapsulamento: a conexão é um atributo privado
    private final Connection conexao;

    // Construtor que recebe a conexão e inicializa o DAO
    public FuncionarioDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // Método para inserir um novo registro na tabela "funcionarios"
    public void inserirFuncionario(int idUsuario, String cargo) throws SQLException {
        String sql = "INSERT INTO funcionarios (id_usuario, cargo) VALUES (?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario); // Chave estrangeira (ID do usuário)
            stmt.setString(2, cargo);  // Cargo do funcionário
            stmt.executeUpdate();      // Executa a inserção
        }
    }

    // Método para listar todos os funcionários com informações completas (inclusive da tabela "usuarios")
    public List<Funcionario> listarCompletos() throws SQLException {
        List<Funcionario> funcionarios = new ArrayList<>();

        // Query que faz um JOIN entre "usuarios" e "funcionarios"
        String sql = """
                SELECT u.id, u.nome, u.email, u.senha,
                       f.cargo
                FROM usuarios u
                JOIN funcionarios f ON u.id = f.id_usuario
            """;

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Herança: Funcionario herda de Usuario
                Funcionario f = new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                );

                // Encapsulamento: uso de setter para definir o cargo
                f.setCargo(rs.getString("cargo"));

                // Adiciona o funcionário à lista
                funcionarios.add(f);
            }
        }

        return funcionarios;
    }
}
