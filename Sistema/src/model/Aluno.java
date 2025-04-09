package model;

import java.time.LocalDate;

// Herança: Aluno herda da classe abstrata Usuario
public class Aluno extends Usuario {

    // Encapsulamento: atributos privados com acesso controlado
    private LocalDate dataVencimentoPagamento;
    private boolean statusPagamento;

    // Construtor com ID (usado quando o aluno já existe no banco)
    public Aluno(int id, String nome, String email, String senha) {
        // Super: invoca o construtor da classe pai Usuario
        super(id, nome, email, senha);
    }

    // Construtor sem ID (usado para novos cadastros)
    public Aluno(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    // Getter e Setter para dataVencimentoPagamento
    // Aplicação do Encapsulamento
    public LocalDate getDataVencimentoPagamento() {
        return dataVencimentoPagamento;
    }

    public void setDataVencimentoPagamento(LocalDate dataVencimentoPagamento) {
        this.dataVencimentoPagamento = dataVencimentoPagamento;
    }

    // Getter e Setter para statusPagamento
    // Encapsulamento aplicado para manipular o estado do pagamento
    public boolean isStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(boolean statusPagamento) {
        this.statusPagamento = statusPagamento;
    }
}
