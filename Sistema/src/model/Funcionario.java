package model;

public class Funcionario extends Usuario {
    private String cargo;

    public Funcionario(String nome, String cpf, String telefone, String email, String cargo) {
        super(nome, cpf, telefone, email);
        this.cargo = cargo;
    }

    public String getCargo() { return cargo; }

    public void setCargo(String cargo) { this.cargo = cargo; }
}
