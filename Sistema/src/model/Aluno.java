package model;

import java.time.LocalDate;

public class Aluno extends Usuario {
    private LocalDate dataVencimentoPagamento;
    private boolean statusPagamento;

    public Aluno(int id, String nome, String cpf, String telefone, String email, String senha) {
        super(id, nome, cpf, telefone, email, senha);
    }

    public Aluno(String nome, String cpf, String telefone, String email, String senha) {
        super(nome, cpf, telefone, email, senha);
    }

    // Getter e Setter para dataVencimentoPagamento
    public LocalDate getDataVencimentoPagamento() {
        return dataVencimentoPagamento;
    }

    public void setDataVencimentoPagamento(LocalDate dataVencimentoPagamento) {
        this.dataVencimentoPagamento = dataVencimentoPagamento;
    }

    // Getter e Setter para statusPagamento
    public boolean isStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(boolean statusPagamento) {
        this.statusPagamento = statusPagamento;
    }
}
