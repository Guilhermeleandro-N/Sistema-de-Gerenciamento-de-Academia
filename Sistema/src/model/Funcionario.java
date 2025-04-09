package model;

public class Funcionario extends Usuario {
    private String cargo;

    public Funcionario(int id, String nome, String cpf, String telefone, String email, String senha) {
        super(id, nome, cpf, telefone, email, senha);
    }

    public Funcionario(String nome, String cpf, String telefone, String email, String senha) {
        super(nome, cpf, telefone, email, senha);
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "Funcionário: " + getNome() + " | Email: " + getEmail();
    }
}
