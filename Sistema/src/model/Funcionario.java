package model;

public class Funcionario extends Usuario {
    private String cargo;

    public Funcionario(int id, String nome, String email, String senha) {
        super(id, nome, email, senha);
    }

    public Funcionario(String nome, String email, String senha) {
        super(nome, email, senha);
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
