package model;

// Aplicação do conceito de Classe Abstrata (não pode ser instanciada diretamente)
public abstract class Usuario {

    // Encapsulamento: atributos privados com acesso controlado via getters e setters
    private int id;
    private String nome;
    private String email;
    private String senha;

    // Construtor com todos os atributos (inclui ID)
    public Usuario(int id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // Construtor sem ID (utilizado quando o ID ainda será gerado pelo banco)
    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // Encapsulamento: métodos públicos para acessar/modificar atributos privados

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
