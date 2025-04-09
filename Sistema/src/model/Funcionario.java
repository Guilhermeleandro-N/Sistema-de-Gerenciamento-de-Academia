package model;

// Herança: Funcionario herda de Usuario
public class Funcionario extends Usuario {

    // Encapsulamento: atributo privado
    private String cargo;

    // Construtor com ID (usado para atualizar ou carregar do banco)
    public Funcionario(int id, String nome, String email, String senha) {
        // Super: chamada ao construtor da classe pai (Usuario)
        super(id, nome, email, senha);
    }

    // Construtor sem ID (usado para inserção de novos registros)
    public Funcionario(String nome, String email, String senha) {
        // Super: chamada ao construtor da classe pai (Usuario)
        super(nome, email, senha);
    }

    // Getter e Setter do cargo – Aplicação do Encapsulamento
    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    // Polimorfismo por sobrescrita (Override): redefinindo o método toString
    @Override
    public String toString() {
        return "Funcionário: " + getNome() + " | Email: " + getEmail();
    }
}
