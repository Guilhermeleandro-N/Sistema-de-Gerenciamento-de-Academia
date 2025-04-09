package view;

import dao.Conexao;

import java.sql.Connection;

public class TesteConexao {
    public static void main(String[] args) {
        Connection conn = Conexao.conectar();

        if (conn != null) {
            System.out.println("Conexão estabelecida com sucesso!");
            Conexao.desconectar(conn);
        } else {
            System.out.println("Falha na conexão.");
        }
    }
}
